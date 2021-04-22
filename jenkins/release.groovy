import groovy.json.JsonOutput
import ru.sbrf.ufs.pipeline.Const

@Library(['ufs-jobs@master']) _


pipeline {
    agent {
        label 'ufs-release'
    }
    options {
        timeout(time: 180, unit: 'MINUTES')
        timestamps()
    }
    parameters {
        choice(name: 'versionIncrement', choices: ['BUILD', 'RC', 'MINOR', 'MAJOR'],
                description: 'Стратегия увеличения нумерации сборки (формат {MAJOR}.{MINOR}.{RC}-build.{BUILD}). Не используется в случае указания forceVersion')
        string(name: 'forceVersion', defaultValue: null, description: 'Версия сборки (для простановки явно)')
        booleanParam(name: 'fullBuild', defaultValue: true, description: 'Полная сборка с вызовом CustomerBuilder (обязательно для релизной сборки)')
        string(name: 'dataspaceDistrib', defaultValue: '', description: 'Ссылка на дистрибутив dataspace (если не проставлен флаг fullBuild)')
        string(name: 'customerDistrib', defaultValue: '', description: 'Ссылка на дистрибутив фабрики (если не проставлен флаг fullBuild)')
        booleanParam(name: 'release', defaultValue: false, description: 'Выпуск релизной сборки')
    }
    environment {
        GIT_PROJECT = 'CIBPPRB'
        GIT_REPOSITORY = 'sbbol-antifraud'
        GIT_LINK = 'ssh://git@10.56.5.65:8878/cibpprb/sbbol-antifraud.git'
        NEXUS_GROUP_ID = 'Nexus_PROD.CI03045533_sbbol-antifraud'
        CUSTOMER_ARTIFACT_ID = 'antifraud'
        DATASPACE_ARTIFACT_ID = 'dataspace-apps-antifraud'
        ARTIFACT_NAME_OS = ''
        CUSTOMER_ARCHIVE_NAME = 'customer-distrib.zip'
        DATASPACE_ARCHIVE_NAME = 'dataspace-distrib.zip'
        VERSION = ''
        VERSION_PATTERN = /\d+\.\d+\.\d+(\-build\.\d+)?/
        DATASPACE_CONFIGS = './config/default.yml,./config/sigma/devgen2_eip.yml'
        NEXUSSBRF_RELEASE_REPOSITORY = 'https://sbrf-nexus.sigma.sbrf.ru/nexus/service/local/artifact/maven/content'
        DEV_REPOSITORY = 'https://nexus.sigma.sbrf.ru/nexus/service/local/artifact/maven/content'
        PROJECT_URL = "https://sbtatlas.sigma.sbrf.ru/stashdbo/projects/${GIT_PROJECT}/repos/${GIT_REPOSITORY}/"

        CUSTOMER_DISTRIB_URL = ''
        DATASPACE_DISTRIB_URL = ''
    }

    stages {

        stage('Input params validation') {
            steps {
                script {
                    if (params.forceVersion) {
                        if (!params.forceVersion.matches(VERSION_PATTERN)) {
                            error("Version should match pattern (digits).(digits).(digits)[-build.(digits)]")
                        }
                    }
                    if (!params.forceVersion && !params.versionIncrement) {
                        error("Version increment or forceVersion are required")
                    }
                    if (!params.fullBuild) {
                        if (!params.customerDistrib) {
                            error("customerDistrib is required (flag fullBuild is not set)")
                        }
                        if (!params.dataspaceDistrib) {
                            error("dataspaceDistrib is required (flag fullBuild is not set)")
                        }
                        if (params.release && params.forceVersion) {
                            error("forceVersion should be empty to make release from provided distributives")
                        }
                    }
                }
            }
        }

        stage('Trigger CustomerBuilder') {
            when {
                expression { params.fullBuild }
            }
            steps {
                script {
                    def deployerParams = "";
                    if (params.release) {
                        if (params.forceVersion) {
                            deployerParams += "-Dversion.forceVersion=${params.forceVersion}"
                        } else if (params.versionIncrement) {
                            deployerParams += "-Dversion.incrementType=${params.versionIncrement}"
                        }
                        deployerParams += " -Dgit.commitComment=\"deployer release\""
                    }
                    def parameters = [
                            "isRelease" : params.release,
                            "jobMode" : "Deployer",
                            "gitModel" : GIT_LINK,
                            "branch" : GIT_BRANCH,
                            "pathToConfigFile" : DATASPACE_CONFIGS,
                            "isIncludeCoreWithSearch" : true,
                            "isIncludeOnlyCore" : false,
                            "isIncludeOnlySearch" : false,
                            "isIncludeGigabas" : true,
                            "isIncludeStateMachine" : false,
                            "isIncludeDuplication" : false,
                            "buildClient" : true,
                            "needInstallToOS" : false,
                            "needQG" : true, // обязательное требование
                            "clientDeployerOptions" : deployerParams
                    ]
                    def paramString = new StringBuilder()
                    for (param in parameters) {
                        if (paramString.length() != 0) {
                            paramString.append('&')
                        }
                        paramString.append(param.key)
                                .append('=')
                                .append(URLEncoder.encode(param.value as String, "UTF-8"))
                    }
                    def triggerBuildResponse = httpRequest(
                            httpMode: 'POST',
                            authentication: "sbt-jenkins-sigma",
                            ignoreSslErrors: true,
                            quiet: true,
                            consoleLogResponseBody: false,
                            url: "https://sbt-jenkins.sigma.sbrf.ru/job/SBBOL/job/DataSpace/job/CustomerBuilder/buildWithParameters?${paramString}"
                    )
                    def queueLink = triggerBuildResponse.headers["Location"][0]
                    log.info("CustomerBuild submitted. Queue link: ${queueLink}")

                    def timeoutSeconds = 1
                    String buildLink = null
                    while (!buildLink) {
                        def queueResponse = httpRequest(
                                authentication: "sbt-jenkins-sigma",
                                ignoreSslErrors: true,
                                quiet: true,
                                consoleLogResponseBody: false,
                                url: "${queueLink}api/json")
                        def queue = readJSON(text: queueResponse.content)
                        buildLink = queue?.executable?.url
                        if (!buildLink) {
                            log.info("Waiting for build to start...")
                            sleep(timeoutSeconds)
                            timeoutSeconds++
                        }
                    }
                    log.info("CustomerBuilder started: ${buildLink}")

                    def finished = false
                    def result = null
                    while (!finished) {
                        def buildResponse = httpRequest(
                                authentication: "sbt-jenkins-sigma",
                                ignoreSslErrors: true,
                                quiet: true,
                                consoleLogResponseBody: false,
                                url: "${buildLink}api/json")
                        def buildInfo = readJSON(text: buildResponse.content)
                        finished = !buildInfo?.building
                        if (!finished) {
                            log.info("Waiting for build to complete...")
                            log.info("Build link: ${buildLink}")
                            sleep(60)
                        } else {
                            result = buildInfo
                        }
                    }
                    log.info("CustomerBuilder finished. Result ${result?.result}")

                    if ("SUCCESS" == result.result) {
                        def description = result.description

                        def regex = /.*<br> jobResult = ([^\r^\n]*).*/
                        def jobResultText = (description =~ regex)[0][1]
                        log.info("Job result: ${jobResultText}")
                        def jobResult = readJSON(text: jobResultText)

                        log.info("DataSpace distrib link: ${jobResult.rootKey.dataSpaceDistribution}")
                        log.info("Customer distrib link: ${jobResult.rootKey.customerDistribution}")
                        CUSTOMER_DISTRIB_URL = jobResult.rootKey.customerDistribution
                        DATASPACE_DISTRIB_URL = jobResult.rootKey.dataSpaceDistribution
                    } else {
                        error("Unsuccessfull build. Check logs of CustomerBuilder ${buildLink}")
                    }
                }
            }
        }

        stage('Detect distrib links and build version') {
            steps {
                script {
                    if (!params.fullBuild) {
                        CUSTOMER_DISTRIB_URL = params.customerDistrib
                        DATASPACE_DISTRIB_URL = params.dataspaceDistrib
                    }
                    if (params.forceVersion) {
                        VERSION = params.forceVersion
                    } else {
                        def regex = /.*v=([^&]*).*/
                        def result = (DATASPACE_DISTRIB_URL =~ regex)[0][1]
                        VERSION = result
                    }
                    log.info('Build version: ' + VERSION)
                    if (!VERSION) {
                        error("Can not determine build version")
                    }
                }
            }
        }

        stage('Download distribs') {
            steps {
                script {
                    log.info("Downloading customer distrib ${CUSTOMER_DISTRIB_URL}")
                    httpRequest authentication: "sbbol-nexus",
                            outputFile: CUSTOMER_ARCHIVE_NAME,
                            responseHandle: 'NONE',
                            url: "${CUSTOMER_DISTRIB_URL}"
                    log.info("Downloading dataspace distrib ${DATASPACE_DISTRIB_URL}")
                    httpRequest authentication: "sbbol-nexus",
                            outputFile: DATASPACE_ARCHIVE_NAME,
                            responseHandle: 'NONE',
                            url: "${DATASPACE_DISTRIB_URL}"
                }
            }
        }

        stage('Prepare install_eip archive') {
            steps {
                script {
                    dir('distrib') {
                        sh "unzip ../${CUSTOMER_ARCHIVE_NAME}"
                        sh "unzip ../${DATASPACE_ARCHIVE_NAME} -d ./dataspace"

                        // extract version
                        def dataspaceVersion = sh(
                                script: "cat dataspace/configs/dataspace-core-template.yaml" +
                                        " | sed -n 's/^.*version: \\(.*\\).*\$/\\1/p'" +
                                        " | head -1",
                                returnStdout: true).trim()
                        sh "echo \"Dataspace version: ${dataspaceVersion}\""

                        // remove version from resources names
                        sh "sed -i 's/\${MODULE_NAME}-${dataspaceVersion}/\${MODULE_NAME}/' dataspace/configs/dataspace-core-template.yaml"
                        sh "sed -i 's/\${MODULE_NAME}-${dataspaceVersion}/\${MODULE_NAME}/' dataspace/configs/dataspace-gigabas-template.yaml"

                        // package archive
                        ARTIFACT_NAME_OS = "antifraud-${VERSION}.zip"
                        sh "zip -rq ${WORKSPACE}/distrib/${ARTIFACT_NAME_OS} *"
                    }
                }
            }
        }

        stage('Publish') {
            steps {
                script {
                    dir('distrib') {
                        log.info("Publishing artifact to ${DEV_REPOSITORY}")
                        publishDev(
                                credentialId: "sbbol-nexus",
                                repository: "corp-releases",
                                groupId: "ru.sberbank.pprb.sbbol.antifraud",
                                artifactId: CUSTOMER_ARTIFACT_ID,
                                version: "D-${VERSION}",
                                extension: 'zip',
                                packaging: 'zip',
                                classifier: "distrib",
                                file: ARTIFACT_NAME_OS
                        )
                        log.info("Successfully published to https://nexus.sigma.sbrf.ru/nexus/content/repositories/corp-releases/ru/sberbank/pprb/sbbol/antifraud/antifraud/D-${VERSION}/")
                        log.info("Distrib url: http://nexus.sigma.sbrf.ru:8099/nexus/service/local/repositories/corp-releases/content/ru/sberbank/pprb/sbbol/antifraud/antifraud/D-${VERSION}/antifraud-D-${VERSION}-distrib.zip")
                        if (params.release) {
                            log.info("Uploading CustomerBuilder distribs to sbrf-nexus")

                            nexus.publishZip(GROUP_ID, DATASPACE_ARTIFACT_ID, "distrib", "../${DATASPACE_ARCHIVE_NAME}", VERSION)
                            log.info("Successfully published to ${getSbrfNexusLink(DATASPACE_ARTIFACT_ID, "D-${VERSION}")}")
                            nexus.publishZip(GROUP_ID, CUSTOMER_ARTIFACT_ID, "distrib", "../${CUSTOMER_ARCHIVE_NAME}", VERSION)
                            log.info("Successfully published to ${getSbrfNexusLink(CUSTOMER_ARTIFACT_ID, "D-${VERSION}")}")

                            log.info("Publishing artifact to ${NEXUSSBRF_RELEASE_REPOSITORY}")
                            nexus.publishZip(GROUP_ID, CUSTOMER_ARTIFACT_ID, "distrib", ARTIFACT_NAME_OS, "${VERSION}-eip")
                            log.info("Successfully published to ${getSbrfNexusLink(CUSTOMER_ARTIFACT_ID, "D-${VERSION}-eip")}")
                        }
                        archiveArtifacts artifacts: "*.zip"
                    }
                }
            }
        }

        stage('Push technical flags') {
            when {
                expression { params.release }
            }
            steps {
                script {
                    boolean qgPassed = true
                    for (String artifactId in [DATASPACE_ARTIFACT_ID, CUSTOMER_ARTIFACT_ID]) {
                        def response = qgm.getFlagMap(
                                repositoryId: 'Nexus_PROD',
                                groupId: "Nexus_PROD/CI03045533_sbbol-antifraud",
                                artifactId: DATASPACE_ARTIFACT_ID,
                                version: VERSION
                        )
                        log.info("QGM result for ${artifactId}: ${response}")
                        def success = true;
                        def flags = ["ci", "sast", "oss", "meta"]
                        for (def flag : flags) {
                            if (response[flag] != "ok") {
                                success = false
                                break
                            }
                        }
                        if (!success) {
                            qgPassed = false
                            log.warn("QG unsuccessful for artifact ${artifactId}. Flags won't be published for current artifact")
                            break
                        }
                    }
                    log.info("QG result: ${qgPassed}")
                    if (qgPassed) {
                        dpm.publishFlags("${VERSION}-eip", CUSTOMER_ARTIFACT_ID, GROUP_ID, ["ci", "sast", "oss", "meta"])
                    }
                }
            }
        }

        stage('Push ReleaseNotes') {
            when {
                expression { params.release }
            }
            steps {
                script {
                    def latestCommitHash = git.checkoutRef('bitbucket-dbo-key', GIT_PROJECT, GIT_REPOSITORY, GIT_BRANCH)
                    List versionTags = git.tags().findAll { it.matches(VERSION_PATTERN) }.sort()
                    def lastVersion = versionTags.isEmpty() ? '' : versionTags.last()
                    log.info("Last version: ${lastVersion}")
                    def projectLog = sh(
                            returnStdout: true,
                            script: "git log --oneline --no-merges --pretty=tformat:'%s' " +
                                    "${lastVersion ? lastVersion + '..HEAD' : ''}"
                    )
                    dir('distrib') {
                        def sbrfNexusCustomerLink = getSbrfNexusLink(CUSTOMER_ARTIFACT_ID, VERSION)
                        def sbrfNexusDataspaceLink = getSbrfNexusLink(DATASPACE_ARTIFACT_ID, VERSION)
                        createReleaseNotesWithDescription(projectLog, latestCommitHash, PROJECT_URL, sbrfNexusCustomerLink, sbrfNexusDataspaceLink)
                        nexus.publishReleaseNotes(GROUP_ID, CUSTOMER_ARTIFACT_ID, "${VERSION}-eip")
                        archiveArtifacts artifacts: "release-notes"
                    }
                }
            }
        }

        stage('Tag current version') {
            when {
                expression { params.release }
            }
            steps {
                script {
                    git.tag('bitbucket-dbo-key', VERSION)
                }
            }
        }
    }
    post {
        cleanup {
            cleanWs()
        }
    }
}

static String getSbrfNexusLink(String artifactId, String version) {
    return "https://sbrf-nexus.sigma.sbrf.ru/nexus/content/repositories/Nexus_PROD/Nexus_PROD/CI03045533_sbbol-antifraud/${artifactId}/${version}/${artifactId}-${version}-distrib.zip"
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
                " ${DEV_REPOSITORY}"

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

/**
 * Функция по формированию ReleaseNotes. Видоизмененная копия
 * https://sbtatlas.sigma.sbrf.ru/stashdbo/projects/CIBUFS/repos/ufs-jobs/browse/vars/createReleaseNotes.groovy
 * </br>
 * Отличается дополнительным блоком <pre>desc</pre> в ReleaseNotes с ссылками на артефакты,
 * полученные через CustomerBuilder.
 *
 * @param releaseNotes изменения, которые необходимо добавить в release-notes
 * @param latestCommit хеш последнего коммита
 * @param projectUrl ссылка на проект в BitBucket
 * @param customerDistribUrl ссылка на артефакт фабрики в sbrf-nexus (из CustomerBuilder)
 * @param dataspaceDistribUrl ссылка на артефакт dataspace в sbrf-nexus (из CustomerBuilder)
 */
def createReleaseNotesWithDescription(String releaseNotes, String latestCommit, String projectUrl,
                                      String customerDistribUrl, String dataspaceDistribUrl) {
    def data = [
            releaseNotes: [

            ],
            codeNotes: [
                    'commit': latestCommit,
                    'repository': projectUrl
            ],
            desc: [
                    'customerDistribUrl': customerDistribUrl,
                    'dataspaceDistribUrl': dataspaceDistribUrl
            ]
    ]

    def jiraIssues = [] as Set
    releaseNotes.trim().split('\n').each { releaseNote ->
        def jiraIssue = (releaseNote =~ /[A-Za-z0-9]+-+[0-9]+/)
        if (jiraIssue.find()) {
            jiraIssues << jiraIssue.group(0)
        }
    }

    jiraIssues.each { issue ->
        data.releaseNotes << [issue: issue]
    }

    def json = JsonOutput.toJson(data)
    log.info("ReleaseNotes: ${json}")
    writeFile file: 'release-notes', text: json
}
