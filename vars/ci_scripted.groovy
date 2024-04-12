def call() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts = ""
    }
    node('workstation') {

        try {
            stage('Check out Code') {
                sh 'ls -l'
                cleanWs()
                sh 'ls -l'
                git branch: 'main', url: 'https://github.com/SaiDevOps27/cart.git'
                sh 'ls -l'
            }

            stage('Build/Compile') {
                    sh 'env'
                    common.compile()
                }

            stage('Test Cases') {
                    common.testcases()
                }

            stage('Code Quality') {
                    common.codequality()
                }

        } catch(e) {
            mail body: "<h1>${component} - PipeLine Failed \n ${BUILD_URL}<h1>", from: 'saimaheshgundu@gmail.com', subject: "${component} - pipeline failed", to: 'saimaheshgundu@gmail.com', mimeType: 'text/html'
        }
    }
}
