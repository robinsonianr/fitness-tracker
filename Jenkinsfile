pipeline {
    agent any

    triggers {
        pollSCM('*/S * * * *')
    }
    stages {
        stage('Compile') {
            steps {
                gradlew('clean', 'classes')
            }
        }
         stage('Test') {
            steps {
                gradlew('test')
            }
        }
        stage('Build Docker Image') {
            steps {
               gradlew('dockerTagDockerHub')
            }
        }
        stage('Run Docker Image') {
            steps {
                gradlew('dockerRun')
            }
        }
    }
}


def gradlew(String... args) {
    sh "./gradlew ${args.join(' ')} -s"
}
