import ru.sbrf.ufs.pipeline.Const
import ru.sbrf.ufs.pipeline.docker.DockerRunBuilder

/**
 * Пайплайн пулл реквест чека
 */

@Library(['ufs-jobs@master']) _

pipeline {

    agent {
        label 'ufs-pr-check'
    }
    options {
        timeout(time: 15, unit: 'MINUTES')
        timestamps()
    }
    parameters {
        string(name: 'pullRequestId', description: 'ID пулл-реквеста')
    }
    environment {
        CREDENTIALS_ID = 'TUZ_DCBMSC5'
        GIT_PROJECT = 'CIBPPRB'
        GIT_REPOSITORY = 'sbbol-antifraud'
        PR_CHECK_LABEL = 'pr_check'
        pullRequest = null
        SONAR_PROJECT = 'ru.sberbank.pprb.sbbol.antifraud'
        SONAR_TOKEN = credentials('sonar-token')
        NEXUS_CREDS = credentials('TUZ_DCBMSC5')
        PACT_CREDS = credentials('pact_ci_meta')
        ALLURE_PROJECT_ID = '37'
        latestCommitHash = ''
    }
    stages {
        stage('Init') {
            steps {
                script {
                    pullRequest = bitbucket.getPullRequest(CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId.toInteger())
                    bitbucket.setJobPullRequestLink(pullRequest)
                    bitbucket.setJenkinsLabelInfo(CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL)
                    bitbucket.updateBitbucketHistoryBuild(CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, stage_name, "running")
                }
            }
        }

        stage('Prepare project') {
            steps {
                script {
                    latestCommitHash = git.checkoutRef 'bitbucket-dbo-key', GIT_PROJECT, GIT_REPOSITORY, "${pullRequest.fromRef.displayId}:${pullRequest.fromRef.displayId} ${pullRequest.toRef.displayId}:${pullRequest.toRef.displayId} "
                    sh "git merge ${pullRequest.toRef.displayId}"
                }
            }
        }
        stage('Compile and Check') {
            steps {
                script {
                    allureEe.run([
                            projectId   : ALLURE_PROJECT_ID,
                            allureResult: ["dataspace-antifraud/antifraud-application/allure-results"],
                            silent      : true
                    ]) { launch ->
                        new DockerRunBuilder(this)
                                .registry(Const.OPENSHIFT_REGISTRY, CREDENTIALS_ID)
                                .volume("${WORKSPACE}", "/build")
                                .volume("${WORKSPACE}/../.m2", "/root/.m2")
                                .extra("-w /build")
                                .env("M2_HOME", "/root/.m2")
                                .env("MVNW_REPOURL", "http://sbtatlas.sigma.sbrf.ru/nexus/content/groups/public/")
                                .env("MVNW_VERBOSE", "true")
                                .env("REPO_USER", "${NEXUS_CREDS_USR}")
                                .env("REPO_PASSWORD", "${NEXUS_CREDS_PSW}")
                                .cpu(1)
                                .memory("2g")
                                .image('registry.sigma.sbrf.ru/ci00149046/ci00405008_sbbolufs/openjdk:11-with-certs')
                                .cmd("./mvnw clean verify sonar:sonar " +
                                        "-P sonar " +
                                        "-e " +
                                        "-Dsonar.host.url=https://sbt-sonarqube.sigma.sbrf.ru/ " +
                                        "-Dsonar.login=${SONAR_TOKEN} " +
                                        "-Dsonar.projectKey=${SONAR_PROJECT} " +
                                        "-Dsonar.pullrequest.key=${params.pullRequestId} " +
                                        "-Dsonar.pullrequest.branch=${pullRequest.fromRef.displayId} " +
                                        "-Dsonar.pullrequest.base=${pullRequest.toRef.displayId} " +
                                        "-Dpact.url=${Const.PACT_BROKER_URL} " +
                                        "-Dpactbroker.auth.username=${PACT_CREDS_USR} " +
                                        "-Dpactbroker.auth.password=${PACT_CREDS_PSW} " +
                                        "-Dpact.consumer.version=${latestCommitHash} " +
                                        "-DbranchName=${env.BRANCH_NAME} " +
                                        "-Dpact.tag=${params.pullRequestId},${env.BRANCH_NAME} " +
                                        '-Dbuild.type=prCheck ' +
                                        '-Dtest.results.enabled=true ' +
                                        "-Dtest-layer=unit,api,web " +
                                        '-Dtest.results.location=../.qa/executedTests.log ' +
                                        "-Dallure.link=${Const.ALLURE_ENTRYPOINT_URL}jobrun/${launch.jobRunId} " +
                                        '-s /build/jenkins/settings.xml'
                                )
                                .run()
                    }
                }
            }
        }
    }
    post {
        success {
            script {
                bitbucket.setJenkinsLabelStatus(CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, true)
                bitbucket.updateBitbucketHistoryBuild(CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, "success", "successful")
            }
        }
        failure {
            script {
                bitbucket.updateBitbucketHistoryBuild(CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, "failure", "failed")
            }
        }
        cleanup {
            script {
                cleanWs()
            }
        }
    }
}
