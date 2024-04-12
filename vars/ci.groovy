def call() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts = ""
    }
    if (env.TAG_NAME ==~ ".*") {
        env.GTAG = "true"
    } else {
        env.GTAG = "false"
    }
    node('workstation') {

        try {

            stage('Check out Code') {
                cleanWs()
                git branch: 'main', url: 'https://github.com/SaiDevOps27/${component}.git'
            }

            sh 'env'

            if (env.BRANCH_NAME != "main") {
                stage('Build/Compile') {
                    common.compile()
                }
            }

            println GTAG
            println BRANCH_NAME

            if (env.GTAG != "true" && env.BRANCH_NAME != "main") {
                stage('Test Cases') {
                    common.testcases()
                }
            }

            if (BRANCH_NAME ==~ "PR-.*" ) {
                stage('Code Quality') {
                    common.codequality()
                }
            }

            if (env.GTAG == "true") {
                stage('Package') {
                    common.prepareArtifacts()
                }
                stage('Artifact Upload') {
                    common.testcases()
                }
            }
        }
        catch(e) {
            mail body: "<h1>${component} - PipeLine Failed \n ${BUILD_URL}<h1>", from: 'saimaheshgundu@gmail.com', subject: "${component} - pipeline failed", to: 'saimaheshgundu@gmail.com', mimeType: 'text/html'
        }
    }
}


