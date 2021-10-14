import ru.sbrf.ufs.pipeline.Const

@Library(['ufs-jobs@master']) _

def latestCommitHash = ''

pipeline {
    agent {
        label 'ufs-release'
    }
    options {
        timeout(time: 15, unit: 'MINUTES')
        timestamps()
    }
    parameters {
    string(name: 'branch', defaultValue: 'release/01.001.00', description: 'Ветка для сборки образа')
        string(name: 'image_name', defaultValue: 'registry.sigma.sbrf.ru/pprb/ci02473994/ci03045533_sbbol_antifraud/antifraud', description: 'Имя docker образа')
        booleanParam(name: 'checkmarx', defaultValue: false)
    }
    environment {
        GIT_PROJECT = 'CIBPPRB'
        GIT_REPOSITORY = 'sbbol-antifraud'
        GIT_REPOSITORY_LINK = "${Const.BITBUCKET_SERVER_INSTANCE_URL}/scm/${GIT_PROJECT}/${GIT_REPOSITORY}.git"
        BUILD_JAVA_DOCKER_IMAGE = 'registry.sigma.sbrf.ru/ci00149046/ci00405008_sbbolufs/bellsoft/liberica-openjdk-alpine:15.0.1-9-with-certs'
        GROUP_ID = 'Nexus_PROD.CI03045533_sbbol-antifraud'
        ARTIFACT_ID = 'antifraud'
        VERSION_PATTERN = /01\.001\.\d{2}_\d{4}/
        // Паттерн, по которому выбираются версии для инкремента последней при сборке
        INITIAL_VERSION = '01.001.00_0000'
        // Дефолтная версия. Будет использована в случае, когда с указанным паттерном еще нет ни одной сборки
        NEXUSSBRF_RELEASE_REPOSITORY = 'https://sbrf-nexus.sigma.sbrf.ru/nexus/service/local/artifact/maven/content'
        // Ссылка для публикации артефактов
        PROJECT_URL = "https://sbtatlas.sigma.sbrf.ru/stashdbo/projects/${GIT_PROJECT}/repos/${GIT_REPOSITORY}/"
        // Ссылка на страницу git репозитория
        DOCS_PATH = "${GIT_REPOSITORY}/${env.BRANCH_NAME}" // путь для публикации документации
        // internal envs
        IMAGE_NAME = ''
        ARTIFACT_NAME_OS = ''
        ARTIFACT_NAME_UFS = ''
        ARTIFACT_NAME_SOWA = ''
        ARTIFACT_NAME_STATIC = ''
        ARTIFACT_NAME_DOCS = ''
        ARTIFACT_NAME_LOCK = ''
        VERSION = ''
        LAST_VERSION = ''
        PATH_VERSION = ''

        CREDENTIAL_ID = "TUZ_DCBMSC5"
        NEXUS_CREDS = credentials("TUZ_DCBMSC5")
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {
        /**
         * Клонирует указанный git branch
         */
        stage('Checkout git') {
            steps {
                script {
                    latestCommitHash = git.checkoutRef('bitbucket-dbo-key', GIT_PROJECT, GIT_REPOSITORY, branch)
                }
            }
        }

        /**
         * Вычисляет версию текущей сборки
         */
        stage('Evaluate version') {
            steps {
                script {
                    // Ищем список всех тэгов, удовлетворяющих нашему паттерну версий
                    List versionTags = git.tags().findAll { it.matches(VERSION_PATTERN) }.sort()
                    LAST_VERSION = versionTags.isEmpty() ? '' : versionTags.last()

                    // Инкрементируем последнюю версию и устанавливаем ее, как текущую версию сборки
                    def versions = (LAST_VERSION ?: INITIAL_VERSION).split("_")
                    def nextBuildNumber = (versions[1].toInteger() + 1).toString().padLeft(4, '0')
                    VERSION = "${versions[0]}_${nextBuildNumber}"
                    PATH_VERSION = "${(VERSION =~ /\d+\.\d+/)[0]}"
                }
            }
        }

        /**
         * Собираем проект
         */
        stage('Build') {
            failFast true
            parallel {
                /**
                 * Собираем gradle проект
                 */
                stage('Build Java') {
                    steps {
                        script {
                            docker.withRegistry(Const.OPENSHIFT_REGISTRY, CREDENTIAL_ID) {
                                sh 'docker run --rm ' +
                                        "-v ${WORKSPACE}:/build " +
                                        '-w /build ' +
                                        "-e PATH_VERSION=${PATH_VERSION} " +
                                        "${BUILD_JAVA_DOCKER_IMAGE} " +
                                        './gradlew ' +
                                        "-PnexusLogin=${NEXUS_CREDS_USR} " +
                                        "-PnexusPassword=${NEXUS_CREDS_PSW} " +
                                        "-Pversion=${VERSION} " +
                                        "-Dsonar.host.url=https://sbt-sonarqube.sigma.sbrf.ru/ " +
                                        "-Dsonar.login=${SONAR_TOKEN} " +
                                        "-Dsonar.projectKey=ru.sberbank.pprb.sbbol.antifraud " +
                                        "-Dsonar.branch.name=${params.branch} " +
                                        'build sonarqube --parallel'
                            }
                        }
                    }
                }
            }
        }

        /**
         * Устанавливает в описание jenkins job собираемую версию
         */
        stage('Set display') {
            steps {
                script {
                    currentBuild.displayName += " $VERSION"
                    rtp stableText: "<h1>Build number: $VERSION</h1>"
                }
            }
        }

        /**
         * Собирает и публикует docker образ в registry
         */
        stage('Build and Push docker image') {
            steps {
                script {
                    IMAGE_NAME = "${params.image_name}:${VERSION}"
                    docker.withRegistry(Const.OPENSHIFT_REGISTRY, CREDENTIAL_ID) {
                        docker.build(IMAGE_NAME, "--force-rm .").push()
                    }
                }
            }
        }

        /**
         * Собирает артефакт с шаблонами OpenShift
         */
        stage('Prepare Openshift manifest') {
            steps {
                script {
                    dir('openshift') {
                        def repoDigest = sh(script: "docker inspect ${IMAGE_NAME} --format='{{index .RepoDigests 0}}'", returnStdout: true).trim()
                        def imageHash = repoDigest.split('@').last()
                        log.info('Docker image hash: ' + imageHash)
                        sh "sed -i 's/\${imageNameWithDigest}/antifraud@${imageHash}/' configs/antifraud/deployment-antifraud.yml"
                        sh "sed -i 's/\${imageVersion}/${VERSION}/' configs/antifraud/deployment-antifraud.yml"
                        ARTIFACT_NAME_OS = "antifraud_os-${VERSION}.zip"
                        // copy liquibase
                        sh "cp -r ../antifraud-application/src/main/resources/db/changelog db"
                        sh "mkdir ${WORKSPACE}/distrib"
                        sh "zip -rq ${WORKSPACE}/distrib/${ARTIFACT_NAME_OS} *"
                    }
                }
            }
        }

        /**
         * Собирает артефакт с документацией
         */
        stage('Prepare docs') {
            steps {
                script {
                    ARTIFACT_NAME_DOCS = "antifraud_docs-${VERSION}.zip"
                    dir('docs/build') {
                        sh "zip -rq ${WORKSPACE}/distrib/$ARTIFACT_NAME_DOCS docs"
                    }
                }
            }
        }

        /**
         * Собирает артефакт с lock файлами
         */
        stage('Prepare locks') {
            steps {
                script {
                    ARTIFACT_NAME_LOCK = "antifraud_locks-${VERSION}.zip"
                    dir('gradle') {
                        sh "zip -rq ${WORKSPACE}/distrib/$ARTIFACT_NAME_LOCK dependency-locks"
                        dir('wrapper') {
                            sh "zip -rq ${WORKSPACE}/distrib/$ARTIFACT_NAME_LOCK gradle-wrapper.properties"
                        }
                    }
                }
            }
        }

        /**
         * Собирает release notes
         */
        stage('Create release notes') {
            steps {
                script {
                    def projectLog = sh(
                            returnStdout: true,
                            script: "git log --oneline --no-merges --pretty=tformat:'%s' " +
                                    "${LAST_VERSION ? LAST_VERSION + '..HEAD' : ''}"
                    )
                    dir('distrib') {
                        releaseNotes = createReleaseNotes(projectLog, latestCommitHash, PROJECT_URL)
                        sh "zip -q release_notes-${VERSION}.zip release-notes"
                    }
                }
            }
        }

        /**
         * Публикует собранные выше артефакты в nexus
         */
        stage('Publish') {
            steps {
                script {
                    dir('distrib') {
                        publishDev(
                                credentialId: CREDENTIAL_ID,
                                repository: "corp-releases",
                                groupId: "ru.sberbank.pprb.sbbol.antifraud",
                                artifactId: ARTIFACT_ID,
                                version: "D-${VERSION}",
                                extension: 'zip',
                                packaging: 'zip',
                                classifier: "distrib",
                                file: ARTIFACT_NAME_OS
                        )
                        log.info("Distrib url: http://nexus.sigma.sbrf.ru:8099/nexus/service/local/repositories/corp-releases/content/ru/sberbank/pprb/sbbol/antifraud/antifraud/D-${VERSION}/antifraud-D-${VERSION}-distrib.zip")

                        qgm.publishReleaseNotes(GROUP_ID, ARTIFACT_ID, VERSION, releaseNotes, CREDENTIAL_ID)
                        nexus.publishZip(GROUP_ID, ARTIFACT_ID, "distrib.openshift", ARTIFACT_NAME_OS, VERSION, CREDENTIAL_ID)
                        nexus.publishZip(GROUP_ID, ARTIFACT_ID, "distrib.docs", ARTIFACT_NAME_DOCS, VERSION, CREDENTIAL_ID)
                        nexus.publishZip(GROUP_ID, ARTIFACT_ID, "distrib.lock", ARTIFACT_NAME_LOCK, VERSION, CREDENTIAL_ID)

                        log.info("Sbrf-nexus distrib url: https://sbrf-nexus.sigma.sbrf.ru/nexus/content/repositories/Nexus_PROD/Nexus_PROD/CI03045533_sbbol-antifraud/antifraud/D-${VERSION}/antifraud-D-${VERSION}-distrib.openshift.zip")

                        archiveArtifacts artifacts: "*.zip"
                    }
                }
            }
        }

        /**
         * Публикует документацию на сервер с документацией
         */
        stage('Publish documentation') {
            steps {
                script {
                    docs.publish('documentation-publisher', 'docs/build/docs/', DOCS_PATH)
                }
            }
        }

        /**
         * Анализ кода checkmarx
         */
        stage('Checkmarx code analyze') {
            when { expression { params.checkmarx } }
            steps {
                script {
                    def devsecopsConfig = readYaml(file: 'jenkins/resources/devsecops-config.yml')
                    String repoUrl = "${Const.BITBUCKET_SERVER_INSTANCE_URL}/scm/${GIT_PROJECT.toLowerCase()}/${GIT_REPOSITORY}.git"
                    library('ru.sbrf.devsecops@master')
                    runOSS(devsecopsConfig, repoUrl, "${params.branch}/${GIT_REPOSITORY}", latestCommitHash)
                    runSastCx(devsecopsConfig, repoUrl, "${params.branch}/${GIT_REPOSITORY}", latestCommitHash)

                    /**
                     * В функции checkStatus четвертым параметром можно выставить максимальное время ожидания ответа (в секундах)
                     * Если не дождаться ответа, то будет добавлен флаг о непрохождении этой проверки
                     * Этот параметр индивидуален и зависит от размера вашего приложения(кода)
                     * По умолчанию стоит 5 минут
                     * В примере ниже выставляем ожидание 2 минуты
                     *   checkmarx.checkStatus(Const.SAST_QG_URL, latestCommitHash, VERSION, 120)
                     */
                    checkmarx.checkStatus(Const.SAST_QG_URL, latestCommitHash, VERSION)
                    def QGstatus = getOSSQGFlag(latestCommitHash)
                    log.info("OSS_RUN:${QGstatus.OSS_RUN} OSS_PASS:${QGstatus.OSS_PASS} OSS_HIGH_PASS:${QGstatus.OSS_HIGH_PASS} OSS_MEDIUM_PASS:${QGstatus.OSS_MEDIUM_PASS}")
                }
            }
        }

        /**
         * Публикует технические флаги о сборке
         */
        stage('Push technical flags') {
            steps {
                script {
                    dpm.publishFlags(VERSION, ARTIFACT_ID, GROUP_ID, ["bvt", "ci", "smart_regress_ift", "smart_regress_st", "smoke_ift", "smoke_st"], CREDENTIAL_ID)
                }
            }
        }
    }
    post {
        always {
            script {
                // Ставим git tag с версией сборки на текущий commit
                git.tag('bitbucket-dbo-key', VERSION)
            }
        }
        /**
         * Обязательно подчищаем за собой
         */
        cleanup {
            cleanWs()
            sh "docker rmi -f ${IMAGE_NAME}"
        }
    }
}

/**
 * Функция по публикации в разработческий Nexus. Видоизмененная копия
 * https://sbtatlas.sigma.sbrf.ru/stashdbo/projects/CIBUFS/repos/ufs-jobs/browse/vars/nexus.groovy
 *
 * @param credentialId jenkins credential name
 * @param repository Репозиторий nexus
 * @param groupId Имя группы артефакта
 * @param artifactId Имя артефакта
 * @param version Версия
 * @param extension Расширение файла (json/xml/etc)
 * @param classifier Классификатор (суффикс) артефакта
 * @param packaging Расширение архива (zip/rar/etc)
 * @param file Имя/путь файла
 */
def publishDev(Map params) {
    def response = ""
    def code = ""
    withCredentials([usernamePassword(credentialsId: params.credentialId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        String request = "curl -vk" +
                " -w 'http code:%{http_code}'" +
                " -u ${USERNAME}:${PASSWORD}" +
                " -F r=${params.repository}" +
                " -F g=${params.groupId}" +
                " -F a=${params.artifactId}" +
                " -F v=${params.version}" +
                " -F p=${params.packaging}" +
                " -F c=${params.classifier}" +
                " -F e=${params.extension}" +
                " -F file=@${params.file}" +
                " -F hasPom=false" +
                " https://nexus.sigma.sbrf.ru/nexus/service/local/artifact/maven/content"

        response = sh(returnStdout: true, script: request)
        log.info("Response: ${response}")
        code = sh(returnStdout: true, script: "echo ${response} | grep 'http code:' ")

    }
    if (params.extension == 'flag' && params.classifier.contains('ift')) return // Костыль! Не работает публикация некоторых флагов. Мешает сборке релизов.
    def arr = code.split(":")
    if (code.trim() == '' || arr.length == 0 || arr[arr.length-1].trim() != '201') {
        error ("Failed publish to Nexus")
    }
}