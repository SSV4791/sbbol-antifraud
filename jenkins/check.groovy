import ru.sbrf.ufs.pipeline.Const

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
        BITBUCKET_CREDENTIALS_ID = 'sbbol-bitbucket'
        NEXUS_CREDENTIALS_ID = 'SBBOL-build'
        GIT_PROJECT = 'CIBPPRB'
        GIT_REPOSITORY = 'sbbol-antifraud'
        PR_CHECK_LABEL = 'pr_check'
        pullRequest = null
        SONAR_PROJECT = 'ru.sberbank.pprb.sbbol.antifraud:sbbol-antifraud'
        SONAR_TOKEN = credentials('sonar-token')
    }
    stages {
        stage('Init') {
            steps {
                script {
                    pullRequest = bitbucket.getPullRequest(BITBUCKET_CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId.toInteger())
                    bitbucket.setJobPullRequestLink(pullRequest)
                    bitbucket.setJenkinsLabelInfo(BITBUCKET_CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL)
                    bitbucket.updateBitbucketHistoryBuild(BITBUCKET_CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, stage_name, "running")
                }
            }
        }

        stage('Prepare project') {
            steps {
                script {
                    git.checkoutRef 'bitbucket-dbo-key', GIT_PROJECT, GIT_REPOSITORY, "${pullRequest.fromRef.displayId}:${pullRequest.fromRef.displayId} ${pullRequest.toRef.displayId}:${pullRequest.toRef.displayId} "
                    sh "git merge ${pullRequest.toRef.displayId}"
                }
            }
        }
        stage('Compile and Check') {
            steps {
                script {
                    withCredentials([usernamePassword(
                            credentialsId: NEXUS_CREDENTIALS_ID,
                            usernameVariable: 'USERNAME',
                            passwordVariable: 'PASSWORD'
                    )]) {
                        docker.withRegistry(Const.OPENSHIFT_REGISTRY, NEXUS_CREDENTIALS_ID) {
                            sh 'docker run --rm ' +
                                    '-v "$(pwd)":/build ' +
                                    '-v "$(pwd)"/../.m2:/root/.m2 ' +
                                    '-w /build ' +
                                    '-e "M2_HOME=/root/.m2" ' +
                                    '-e "MVNW_REPOURL=http://sbtatlas.sigma.sbrf.ru/nexus/content/groups/public/" ' +
                                    '-e "MVNW_VERBOSE=true" ' +
                                    "-e \"REPO_USER=${USERNAME}\" " +
                                    "-e \"REPO_PASSWORD=${PASSWORD}\" " +
                                    'registry.sigma.sbrf.ru/ci00149046/ci00405008_sbbolufs/openjdk:11-with-certs ' +
                                    "./mvnw clean verify sonar:sonar -P sonar -e -Dsonar.host.url=https://sbt-sonarqube.sigma.sbrf.ru/ -Dsonar.login=${SONAR_TOKEN} -Dsonar.projectKey=${SONAR_PROJECT} -Dsonar.pullrequest.key=${params.pullRequestId} -Dsonar.pullrequest.branch=${pullRequest.fromRef.displayId} -Dsonar.pullrequest.base=${pullRequest.toRef.displayId} -s /build/jenkins/settings.xml"
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            script {
                bitbucket.setJenkinsLabelStatus(BITBUCKET_CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, true)
                bitbucket.updateBitbucketHistoryBuild(BITBUCKET_CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, "success", "successful")
            }
        }
        failure {
            script {
                bitbucket.updateBitbucketHistoryBuild(BITBUCKET_CREDENTIALS_ID, GIT_PROJECT, GIT_REPOSITORY, params.pullRequestId, PR_CHECK_LABEL, "failure", "failed")
            }
        }
        cleanup {
            script {
                cleanWs()
            }
        }
    }
}
