import ru.sbrf.ufs.pipeline.Const
import ru.sbrf.ufs.pipeline.docker.DockerRunBuilder

@Library(['ufs-jobs@master']) _

def pullRequest = null
def latestCommitHash = ""
def ufsCredential = 'TUZ_DCBMSC5'

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
        GIT_PROJECT = 'CIBPPRB'
        GIT_REPOSITORY = 'sbbol-antifraud'
        PR_CHECK_LABEL = 'build'
        BUILD_JAVA_DOCKER_IMAGE = 'registry.sigma.sbrf.ru/ci00149046/ci00405008_sbbolufs/bellsoft/liberica-openjdk-alpine:15.0.1-9-with-certs'
        NEXUS_CREDS = credentials("${ufsCredential}")
        SONAR_TOKEN = credentials('sonar-token')
        ALLURE_PROJECT_ID = '37'
    }

    stages {
        stage('Preparing job') {
            steps {
                script {
                    pullRequest = bitbucket.getPullRequest(ufsCredential, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId.toInteger())
                    setJobPullRequestLink(pullRequest)
                    bitbucket.setJenkinsLabelInfo(ufsCredential, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL)
                    bitbucket.updateBitbucketHistoryBuild(ufsCredential, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, stage_name, "running")
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

        stage('Build Java') {
            steps {
                script {
                    allureEe.run([
                            projectId   : ALLURE_PROJECT_ID,
                            allureResult: ["build/allure-results"],
                            silent      : true
                    ]) { launch ->
                        new DockerRunBuilder(this)
                                .registry(Const.OPENSHIFT_REGISTRY, ufsCredential)
                                .volume("${WORKSPACE}", "/build")
                                .extra("-w /build")
                                .cpu(2)
                                .memory("2g")
                                .image(BUILD_JAVA_DOCKER_IMAGE)
                                .cmd('./gradlew ' +
                                        "-PnexusLogin=${NEXUS_CREDS_USR} " +
                                        "-PnexusPassword=${NEXUS_CREDS_PSW} " +
                                        "-Dtest-layer=unit,api,web,cdcConsumer,configuration " +
                                        "-Dbuild.link=${env.BUILD_URL} " +
                                        "-Dbuild.type=prCheck " +
                                        "-Dallure.jobrunId=${launch.jobRunId} " +
                                        "-Dsonar.host.url=https://sbt-sonarqube.sigma.sbrf.ru/ " +
                                        "-Dsonar.login=${SONAR_TOKEN} " +
                                        "-Dsonar.projectKey=ru.sberbank.pprb.sbbol.antifraud " +
                                        "-Dsonar.projectVersion=${pullRequest.fromRef.displayId} " +
                                        "-Dsonar.pullrequest.key=${params.pullRequestId} " +
                                        "-Dsonar.pullrequest.branch=${pullRequest.fromRef.displayId} " +
                                        "-Dsonar.pullrequest.base=${pullRequest.toRef.displayId} " +
                                        "portalUpload sonarqube"
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
                bitbucket.setJenkinsLabelStatus(ufsCredential, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, true)
                bitbucket.updateBitbucketHistoryBuild(ufsCredential, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, "success", "successful")
            }
        }
        failure {
            script {
                bitbucket.updateBitbucketHistoryBuild(ufsCredential, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, "failure", "failed")
            }
        }
        cleanup {
            script {
                cleanWs()
            }
        }
    }
}
