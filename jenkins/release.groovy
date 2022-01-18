import ru.sbrf.ufs.pipeline.Const
import ru.sbrf.ufs.pipeline.docker.DockerRunBuilder

@Library(['ufs-jobs@master']) _

def credential = 'TUZ_DCBMSC5'
def projectLog = ''

pipeline {
    agent {
        label 'ufs-release'
    }
    options {
        timeout(time: 15, unit: 'MINUTES')
        timestamps()
    }
    parameters {
        string(name: 'branch', defaultValue: 'release/02.000.00', description: 'Ветка для сборки образа')
        string(name: 'branchParams', defaultValue: 'master', description: 'Ветка для params.yml')
        string(name: 'istio_tag', defaultValue: '01.000.03', description: 'Тег для шаблонов istio')
        string(name: 'commitOrTag', description: 'Хэш коммита или тэг от которого формируется release-notes')
        booleanParam(name: 'checkmarx', defaultValue: false)
        booleanParam(name: 'reverseAndPublish', defaultValue: false)
        choice(name: 'type', choices: ['release', 'dev'], description: "Тип сборки")
    }
    environment {
        GIT_PROJECT = 'CIBPPRB'
        GIT_REPOSITORY = 'sbbol-antifraud'
        GIT_REPOSITORY_LINK = "${Const.BITBUCKET_SERVER_INSTANCE_URL}/scm/${GIT_PROJECT}/${GIT_REPOSITORY}.git"
        BUILD_JAVA_DOCKER_IMAGE = 'registry.sigma.sbrf.ru/ci00149046/ci00405008_sbbolufs/bellsoft/liberica-openjdk-alpine:15.0.1-9-with-certs'
        GROUP_ID = 'Nexus_PROD.CI03045533_sbbol-antifraud'
        ARTIFACT_ID = 'antifraud'
        VERSION_PATTERN = /02\.000\.\d{2}_\d{4}/
        // Паттерн, по которому выбираются версии для инкремента последней при сборке
        INITIAL_VERSION = '02.000.00_0000'
        // Дефолтная версия. Будет использована в случае, когда с указанным паттерном еще нет ни одной сборки
        NEXUSSBRF_RELEASE_REPOSITORY = 'https://sbrf-nexus.sigma.sbrf.ru/nexus/service/local/artifact/maven/content'
        // Ссылка для публикации артефактов
        PROJECT_URL = "https://sbtatlas.sigma.sbrf.ru/stashdbo/projects/${GIT_PROJECT}/repos/${GIT_REPOSITORY}/"
        // Ссылка на страницу git репозитория
        DOCS_PATH = "${ARTIFACT_ID}/${env.BRANCH_NAME}" // путь для публикации документации
        NEXUS_CREDS = credentials("${credential}")
        GRADLE_CACHE_DIR = "${WORKSPACE}/.gradle"
        // код(GUID) компонента из ARIS, для каждой БП/БПС свой
        // internal envs
        DOCKER_REGISTRY = 'registry.sigma.sbrf.ru'
        DOCKER_IMAGE_REPOSITORY_PROD = 'pprb/ci02473994/ci03045533_sbbol_antifraud'
        DOCKER_IMAGE_REPOSITORY_DEV = 'pprb-dev/ci02473994/ci03045533_sbbol_antifraud_dev'
        DOCKER_IMAGE_REPOSITORY = ''
        DOCKER_IMAGE_NAME = ''
        DOCKER_IMAGE_HASH = ''
        ARTIFACT_NAME_LOCK = ''
        VERSION = ''
        LAST_VERSION = ''
        ARTIFACT_NAME = ''
        SONAR_TOKEN = credentials('sonar-token')
        LATEST_COMMIT_HASH = ''
        ALLURE_PROJECT_ID = '37'
        PACT_CREDS = credentials('pact_ci_meta')
    }

    stages {
        /**
         * Клонирует указанный git branch
         */
        stage('Checkout git') {
            steps {
                script {
                    LATEST_COMMIT_HASH = git.checkoutRef('bitbucket-dbo-key', GIT_PROJECT, GIT_REPOSITORY, branch)
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
                }
            }
        }

        /**
         * Push'им git tag с версией и устанавливаем в описание jenkins job собираемую версию
         */
        stage('Set version') {
            steps {
                script {
                    // Ставим git tag с версией сборки на текущий commit
                    git.tag('bitbucket-dbo-key', VERSION)
                    currentBuild.displayName += " D-$VERSION"
                    rtp stableText: "<h1>Build number: D-$VERSION</h1>"
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
                stage('Build Java and Pact tests check') {
                    steps {
                        script {
                            allureEe.run([
                                    projectId   : ALLURE_PROJECT_ID,
                                    allureResult: ["build/allure-results"],
                                    silent      : true
                            ]) { launch ->
                                new DockerRunBuilder(this)
                                        .registry(Const.OPENSHIFT_REGISTRY, credential)
                                        .volume("${WORKSPACE}", "/build")
                                        .extra("-w /build")
                                        .cpu(2)
                                        .memory("2g")
                                        .image(BUILD_JAVA_DOCKER_IMAGE)
                                        .cmd('./gradlew ' +
                                                "-PnexusLogin=${NEXUS_CREDS_USR} " +
                                                "-PnexusPassword=${NEXUS_CREDS_PSW} " +
                                                "-Pversion=${VERSION} " +
                                                "-Dtest-layer=cdcProvider,unit,api,web,cdcConsumer " +
                                                "-Dpactbroker.url=${Const.PACT_BROKER_URL} " +
                                                "-Dpactbroker.auth.username=${PACT_CREDS_USR} " +
                                                "-Dpactbroker.auth.password='${PACT_CREDS_PSW}' " +
                                                "-Dpact.pacticipant.version=${VERSION} " +
                                                "-Dpact.pacticipant.tag=${params.branch} " +
                                                "-Dbuild.link=${env.BUILD_URL} " +
                                                "-Dbuild.type=release " +
                                                "-Dallure.jobrunId=${launch.jobRunId} " +
                                                "-Dsonar.host.url=https://sbt-sonarqube.sigma.sbrf.ru/ " +
                                                "-Dsonar.login=${SONAR_TOKEN} " +
                                                "-Dsonar.projectKey=ru.sberbank.pprb.sbbol.antifraud " +
                                                "-Dsonar.branch.name=${params.branch} " +
                                                'build portalUpload sonarqube --parallel'
                                        )
                                        .run()
                            }
                        }
                    }
                }
            }
        }

        stage('Build and Push docker image') {
            steps {
                script {
                    if (params.type == 'release') {
                        DOCKER_IMAGE_REPOSITORY = DOCKER_IMAGE_REPOSITORY_PROD
                    } else {
                        DOCKER_IMAGE_REPOSITORY = DOCKER_IMAGE_REPOSITORY_DEV
                    }
                    DOCKER_IMAGE_NAME = "${DOCKER_REGISTRY}/${DOCKER_IMAGE_REPOSITORY}/${ARTIFACT_ID}:${VERSION}"
                    docker.withRegistry(Const.OPENSHIFT_REGISTRY, credential) {
                        docker.build(DOCKER_IMAGE_NAME, "--force-rm .").push()
                    }
                }
            }
        }

        /**
         * Подготавливаем шаблоны OpenShift для публикации
         */
        stage('Prepare Openshift manifest') {
            steps {
                script {
                    dir('openshift') {

                        istio.getOSTemplates('bitbucket-dbo-key', 'istio', 'openshift', params.istio_tag, './istio')

                        git.raw(credential, 'cibufs', 'sbbol-params', params.branchParams, "${ARTIFACT_ID}/${params.branch}/params.yml")
                        def repoDigest = sh(script: "docker inspect ${DOCKER_IMAGE_NAME} --format='{{index .RepoDigests 0}}'", returnStdout: true).trim()
                        DOCKER_IMAGE_HASH = repoDigest.split('@').last()
                        log.info('Docker image hash: ' + DOCKER_IMAGE_HASH)
                    }
                }
            }
        }

        /**
         * Публикуем артефакт в нексус
         */
        stage('Publish artifacts') {
            steps {
                script {
                    new DockerRunBuilder(this)
                            .registry(Const.OPENSHIFT_REGISTRY, credential)
                            .env("GRADLE_USER_HOME", '/build/.gradle')
                            .volume("${WORKSPACE}", "/build")
                            .extra("-w /build")
                            .cpu(1)
                            .memory("1g")
                            .image(BUILD_JAVA_DOCKER_IMAGE)
                            .cmd('./gradlew ' +
                                    "-PnexusLogin=${NEXUS_CREDS_USR} " +
                                    "-PnexusPassword='${NEXUS_CREDS_PSW}' " +
                                    "-Pversion=D-${VERSION} " +
                                    "-PdockerImage='${DOCKER_IMAGE_REPOSITORY}/${ARTIFACT_ID}@${DOCKER_IMAGE_HASH}' " +
                                    "${params.type} " +
                                    "${params.reverseAndPublish ? 'reverseAndPublish' : ''}"
                            )
                            .run()
                }
            }
        }

        /**
         * Собирает артефакт с lock файлами
         */
        stage('Prepare locks') {
            when { expression { params.type == 'release' } }
            steps {
                script {
                    ARTIFACT_NAME_LOCK = "${ARTIFACT_ID}_locks-${VERSION}.zip"
                    sh 'mkdir -p distrib'
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
                    //преобразование введенного значения в полный хэш коммита fromCommit
                    def fromCommit = ''
                    def comOrTag = commitOrTag.trim()
                    if (comOrTag.isEmpty()) {
                        fromCommit = git.tag2hash(LAST_VERSION); // конвертируем тэг последней версии в hash коммита
                        log.info("Значение в поле commitOrTag не введено, по умолчанию commitOrTag = ${LAST_VERSION}" +
                                " - последняя версия сборки");
                    } else {
                        fromCommit = git.convert2hash(comOrTag, VERSION_PATTERN)
                    }
                    projectLog = sh(
                            returnStdout: true,
                            script: "git log --oneline --no-merges --pretty=tformat:'%s' " +
                                    "${fromCommit ? fromCommit + '..HEAD' : ''}"
                    )
                    releaseNotes = createReleaseNotes(projectLog, LATEST_COMMIT_HASH, PROJECT_URL, fromCommit)
                }
            }
        }

        /**
         * Публикует собранные выше артефакты в nexus
         */
        stage('Publish') {
            when { expression { params.type == 'release' } }
            steps {
                script {
                    dir('distrib') {
                        qgm.publishReleaseNotes(GROUP_ID, ARTIFACT_ID, "D-$VERSION", releaseNotes, credential)
                        log.info("Sbrf-nexus distrib url: https://sbrf-nexus.sigma.sbrf.ru/nexus/content/repositories/Nexus_PROD/Nexus_PROD/CI03045533_sbbol-antifraud/${ARTIFACT_ID}/D-${VERSION}/${ARTIFACT_ID}-D-${VERSION}-distrib.configs.zip")
                        nexus.publishZip(GROUP_ID, ARTIFACT_ID, "distrib.lock", ARTIFACT_NAME_LOCK, "D-$VERSION", credential)
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
         * @see https://confluence.sberbank.ru/display/OTIB/SAST
         */
        stage('Checkmarx code analyze') {
            when { expression { params.checkmarx } }
            steps {
                script {
                    def devsecopsConfig = readYaml(file: 'jenkins/resources/devsecops-config.yml')
                    String repoUrl = "${Const.BITBUCKET_SERVER_INSTANCE_URL}/scm/${GIT_PROJECT.toLowerCase()}/${GIT_REPOSITORY}.git"
                    library('ru.sbrf.devsecops@master')
                    runOSS(devsecopsConfig, repoUrl, "${params.branch}/${GIT_REPOSITORY}", LATEST_COMMIT_HASH)
                    runSastCx(devsecopsConfig, repoUrl, "${params.branch}/${GIT_REPOSITORY}", LATEST_COMMIT_HASH)
                }
            }
        }

        /**
         * Публикует технические флаги о сборке
         */
        stage('Push technical flags') {
            steps {
                script {
                    dpm.publishFlags("D-$VERSION", ARTIFACT_ID, GROUP_ID, ["bvt", "ci", "smart_regress_ift", "smart_regress_st", "smoke_ift", "smoke_st"], credential)
                }
            }
        }

        /**
         * Проставляет в таски jira fix build
         */
        stage('Set fix build') {
            steps {
                script {
                    def jiraIssues = [] as Set
                    projectLog.trim().split('\n').each { issue ->
                        def jiraIssue = (issue =~ /[A-Za-z0-9]+-+[0-9]+/)
                        if (jiraIssue.find()) {
                            jiraIssues << jiraIssue.group(0)
                        }
                    }

                    jiraIssues.each { jiraIssue ->
                        try {
                            jira.setFixBuild(jiraIssue, VERSION, credential)
                        } catch (e) {
                            log.error("Failed to update fixBuild for ${jiraIssue}: ${e}")
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: "distrib/*.zip", allowEmptyArchive: true
            archiveArtifacts artifacts: "build/*.zip", allowEmptyArchive: true
        }
        /**
         * Обязательно подчищаем за собой
         */
        cleanup {
            cleanWs()
            sh "docker rmi -f ${DOCKER_IMAGE_NAME} || true"
        }
    }
}
