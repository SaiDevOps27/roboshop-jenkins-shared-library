def call() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts=""
    }
    pipeline {
        agent any

        stages {

            stage('Compile/Build') {
                steps {
                    script {
                        common.compile()
                    }
                }
            }
            stage('Test Cases') {
                steps {
                    script {
                        common.testcases()
                    }
                }
            }

            stage('Code Quality') {
                steps {
                    script {
                        common.codequality()
                    }
                }
            }
        }

        post {
            failure {
                mail body: "<h1>${component} - PipeLine Failed \n ${BUILD_URL}<h1>", from: 'saimaheshgundu@gmail.com', subject: "${component} - pipeline failed", to: 'saimaheshgundu@gmail.com', mimeType: 'text/html'
            }
        }

    }
}


