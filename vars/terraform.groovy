def call() {
    pipeline {
        agent any

        options {
            ansiColor('xterm')
        }


        parameters {
            string(name: 'ENV', defaultValue: '', description: 'Which Environment?')
        }

        stages {


            stage('Init') {
                steps {
                    sh 'terraform init -backend-config=env-dev/state.tfvars '
                }
            }

            stage('Apply') {
                steps {
                    //sh 'terraform apply -auto-approve -var-file=env-dev/main.tfvars'
                    sh 'echo'
                }
            }
        }
    }
}