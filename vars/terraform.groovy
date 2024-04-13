def call() {
    pipeline {
        agent any

        parameters {
            string(name: 'ENV', defaultValue: '', description: 'Which Environment?')
        }

        options {
            ansiColor('xterm')
        }


        stages {
            stage('Init') {
                steps {
                    sh 'terraform init -backend-config=env-${ENV}/state.tfvars '
                }
            }
            stage('Apply') {
                steps {
                    sh 'terraform apply -auto-approve -var-file=env-${ENV}/main.tfvars'
                    //sh 'echo'
                }
            }
        }
        post {
            always {
                cleanWs()
            }
        }
    }
}