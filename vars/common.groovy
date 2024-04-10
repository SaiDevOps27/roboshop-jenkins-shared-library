def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
    }
    if (app_lang == "maven") {
        sh 'mvn package'
    }
}

def testcases() {
     // npm test
    // maven test
     // go test
    sh 'echo OK'
}

def codequality() {
    sh 'sonar-scanner -Dsonar.host.url=http://172.31.39.21:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=${component}'
}