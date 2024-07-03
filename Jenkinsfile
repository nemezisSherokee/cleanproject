pipeline {
    agent {
        docker {
            image 'maven:3.6.3-openjdk-17-slim'
            args '-v /root/.m2:/root/.m2'
        }
    }

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=/root/.m2/repository'
        DOCKER_HUB_REPO = 'nemezis/testcleanproject'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/nemezisSherokee/cleanproject.git/', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    def app = docker.build("${DOCKER_HUB_REPO}:${env.BUILD_ID}")
                }
            }
        }
    }
}
