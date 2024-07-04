def buildAll = false
def affectedModules = []
def affectedList
def goal = "package"

pipeline {
    agent any

    tools {
        maven 'maven-apache'
        dockerTool 'docker'
    }

    environment {
        goal = "package"
        dockerRepo = "nemezis"
        profile = "test"
        BRANCH_NAME = "${GIT_BRANCH.split('/').size() > 1 ? GIT_BRANCH.split('/')[1..-1].join('/') : GIT_BRANCH}"
        def dockerHome = tool 'docker'
        PATH = "${dockerHome}/bin:${env.PATH}"
        DockerCredentials = 'DockerCredentials'
    }

    stages {
        stage("Get Diff") {
            steps {
                script {
                    def changes = []

                    if (env.CHANGE_ID) {
                        echo "Pull Request Trigger"
                        if (isUnix()) {
                            changes = sh(returnStdout: true, script: "git --no-pager diff origin/${CHANGE_TARGET} --name-only").trim().split()
                        } else {
                            changes = bat(returnStdout: true, script: "git --no-pager diff origin/${CHANGE_TARGET} --name-only").trim().split()
                        }
                        goal = "compile"
                    } else {
                        echo "Push Trigger"
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

                    changes.each { c ->
                        if (c.contains("common") || c == "pom.xml") {
                            affectedModules = []
                            buildAll = true
                            return
                        } else {
                            if (c.indexOf("/") > 1) {
                                affectedModules.add(c.substring(0, c.indexOf("/")))
                                println "Affected Modules: ${c.substring(0, c.indexOf("/"))}"
                            }
                        }
                    }
                    println "Affected Modules: ${affectedModules}"
                }
            }
        }

        stage('Test') {
            steps {
                withMaven {
                    sh "mvn clean package"
                }
            }
        }

        stage('Build Changed Modules') {
            when {
                expression {
                    return affectedModules.size() > 0
                }
            }
            steps {
                script {
                    def uniqueModules = affectedModules.unique()
                    for (module in uniqueModules) {
                        stage("Build ${module}") {
                            withMaven {
                                if (isUnix()) {
                                    dir("${module}") {
                                        sh "mvn clean ${goal} -P${profile}"
                                    }
                                } else {
                                    dir("${module}") {
                                        bat "mvn clean ${goal} -P${profile}"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        stage('Build and Push Docker Image') {
            when {
                expression {
                    return env.BRANCH_NAME.startsWith('feature') && affectedModules.size() > 0
                }
            }
            steps {
                script {
                    def uniqueModules = affectedModules.unique()
                    for (module in uniqueModules) {
                        dir(module) {
                            stage("Build Docker Image for ${module}") {
                                script {
                                    def imageName = "${dockerRepo}/${module}:${env.BUILD_ID}"
                                    sh "docker build -t ${imageName} ."

                                    withCredentials([usernamePassword(credentialsId: DockerCredentials, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                        sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
//                                         sh "docker push ${imageName}"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
