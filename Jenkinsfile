def buildAll = false
def affectedModules = []
def affectedList
def goal = "package"

pipeline {
    agent any

        tools {
            maven 'maven-apache'
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
    //this stage will get all the files that were modified
    		stage("get diff") {
    			steps {
    				script {
    					def changes = []

    					if(env.CHANGE_ID) { //check if triggered via Pull Request
    						echo "Pull Request Trigger"
    						//get changes via git diff so we can know which module should be built
    						if (isUnix()) {
    							changes = sh(returnStdout: true, script: "git --no-pager diff origin/${CHANGE_TARGET} --name-only").trim().split()
    						}
    						else {
    							changes = bat(returnStdout: true, script: "git --no-pager diff origin/${CHANGE_TARGET} --name-only").trim().split()
    						}
    						//use compile goal instead of package if the trigger came from Pull Request. we dont want to package our module for every pull request
    						goal = "compile"
    					} else if(currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause').size() > 0) { //check if triggered via User 'Build Now'
    						echo "User Trigger"
    						buildAll = true
    					} else { //defaults to Push Trigger
    						echo "Push Trigger"
    						//get changes via changelogs so we can know which module should be built
    						def changeLogSets = currentBuild.changeSets
    						for (int i = 0; i < changeLogSets.size(); i++) {
    							def entries = changeLogSets[i].items
    							for (int j = 0; j < entries.length; j++) {
    								def entry = entries[j]
    								def files = new ArrayList(entry.affectedFiles)
    								for (int k = 0; k < files.size(); k++) {
    									def file = files[k]
    									changes.add(file.path)
    								}
    							}
    						}
    					}
    					//iterate through changes
    					changes.each {c ->
    						if(c.contains("common") || c == "pom.xml") { //if changes includes parent pom.xml and common module, we should build all modules
    							affectedModules = []
    							buildAll = true
    							return
    						}else {
    							if(c.indexOf("/") > 1) { //filter all affected module. indexOf("/") means the file is inside a subfolder (module)
    								affectedModules.add(c.substring(0,c.indexOf("/")))
    							}

    						}
    					}

    				}
    			}
    		}

        stage('Checkout') {
            steps {
                git url: 'https://github.com/nemezisSherokee/cleanproject.git/', branch: 'main'
            }
        }

        stage('BuildALL') {
            steps {
                    withMaven {
                      sh "mvn clean package -Ptest"
                        dir('orderprocessing') {
                              sh "pwd"
                              sh 'mvn clean package -P test'
                            }
                    } // withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports

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
