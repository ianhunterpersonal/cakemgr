pipeline {
    // run on jenkins nodes tha has java 8 label
    agent any // { label 'java8' }
    // global env variables
    environment {
        EMAIL_RECIPIENTS = 'ian.hunter.personal@gmail.com'
        MAVEN_VER='3.6.1'
    }
    
    triggers { cron('H */4 * * 1-5') }
    
    stages {
        stage('Build') { 
            steps {
                echo 'Building..' + env.BRANCH_NAME
                sh 'mvn -B clean install' 
            }
        }
        stage('Deploy') { 
            steps {
                echo 'Deploying to docker...' + env.BRANCH_NAME
                sh 'mvn -B -Dmaven.test.skip=true spring-boot:build-image -Dspring-boot.build-image.imageName=totnesjava/cakemgr' 
            }
        }
     }
}