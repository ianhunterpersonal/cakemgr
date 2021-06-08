pipeline {
    // run on jenkins nodes tha has java 8 label
    agent any // { label 'java8' }
    // global env variables
    environment {
        EMAIL_RECIPIENTS = 'ian.hunter.personal@gmail.com'
        MAVEN_VER='3.6.1'
    }
    stages {

        stage('Build') { 
            steps {
                sh 'mvn -B clean package' 
            }
        } 

     }
}