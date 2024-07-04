pipeline {
    agent any

        tools {
            maven 'Maven 3.2.5'
        }

//     agent {
//         dockerfile {
//             filename 'agent/Dockerfile'
//             args '-v /Users/lary/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock'
//         }
//     }
//
//     environment {
//         //MAVEN_OPTS = '-Dmaven.repo.local=/root/.m2/repository'
//         //DOCKER_HUB_REPO = 'nemezis/testcleanproject'
//         //DOCKERHUB_AUTH = credentials('DockerHubCredentials')
// //         MYSQL_AUTH= credentials('MYSQL_AUTH')
// //         IMAGE_NAME= 'paymybuddy'
// //         IMAGE_TAG= 'latest'
// //         HOSTNAME_DEPLOY_STAGING = "34.230.2.252"
// //         HOSTNAME_DEPLOY_PROD = "54.145.64.29"
// //06 63 63 02 13
//     }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/nemezisSherokee/cleanproject.git/', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                    withMaven {
                      sh "mvn clean package"
                    } // withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports

                // sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                                    withMaven {
                                      sh "mvn clean test"
                                    } // withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports

            }
        }

//         stage('Docker Build') {
//             steps {
//                 script {
//                     def app = docker.build("${DOCKER_HUB_REPO}:${env.BUILD_ID}")
//                 }
//             }
//         }
    }
}
