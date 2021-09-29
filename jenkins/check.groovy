import ru.sbrf.ufs.pipeline.Const
import ru.sbrf.ufs.pipeline.docker.DockerRunBuilder

/**
 * Пайплайн пулл реквест чека
 */

@Library(['ufs-jobs@master']) _

def pullRequest = null
def latestCommitHash = ""

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
        PR_CHECK_LABEL = 'pr_check'
        CREDENTIALS_ID = 'TUZ_DCBMSC5'
        NEXUS_CREDS = credentials('TUZ_DCBMSC5')
        SONAR_TOKEN = credentials('sonar-token')
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
                    new DockerRunBuilder(this)
                            .registry(Const.OPENSHIFT_REGISTRY, CREDENTIALS_ID)
                            .env("PATH_VERSION", "00.000")
                            .volume("${WORKSPACE}", "/build")
                            .extra("-w /build")
                            .cpu(2)
                            .memory("2g")
                            .image('registry.sigma.sbrf.ru/ci00149046/ci00405008_sbbolufs/bellsoft/liberica-openjdk-alpine:15.0.1-9-with-certs')
                            .cmd('./gradlew ' +
                                    "-PnexusLogin=${NEXUS_CREDS_USR} " +
                                    "-PnexusPassword=${NEXUS_CREDS_PSW} " +
                                    "-Dsonar.host.url=https://sbt-sonarqube.sigma.sbrf.ru/ " +
                                    "-Dsonar.login=${SONAR_TOKEN} " +
                                    "-Dsonar.projectKey=ru.sberbank.pprb.sbbol.antifraud " +
                                    "-Dsonar.pullrequest.key=${params.pullRequestId} " +
                                    "-Dsonar.pullrequest.branch=${pullRequest.fromRef.displayId} " +
                                    "-Dsonar.pullrequest.base=${pullRequest.toRef.displayId} " +
                                    "clean build sonarqube --parallel"
                            )
                            .run()
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