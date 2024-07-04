def buildAll = false
def affectedModules = []
def affectedList
def goal = "package"

pipeline {
    agent any

    tools {
        maven 'maven-apache'
    }

    environment {
        // Define the goal (e.g., install, compile, package)
        goal = "package"
        profile = "test"
    }

    stages {
        // Stage to get all the files that were modified
        stage("get diff") {
            steps {
                script {
                    def changes = []

                    if (env.CHANGE_ID) { // Check if triggered via Pull Request
                        echo "Pull Request Trigger"
                        // Get changes via git diff to know which module should be built
                        if (isUnix()) {
                            changes = sh(returnStdout: true, script: "git --no-pager diff origin/${CHANGE_TARGET} --name-only").trim().split()
                        } else {
                            changes = bat(returnStdout: true, script: "git --no-pager diff origin/${CHANGE_TARGET} --name-only").trim().split()
                        }
                        // Use compile goal instead of package if the trigger came from Pull Request
                        goal = "compile"
                    } else { // Defaults to Push Trigger
                        echo "Push Trigger"
                        // Get changes via changelogs to know which module should be built
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
                    // Iterate through changes
                    changes.each { c ->
                        if (c.contains("common") || c == "pom.xml") { // If changes include parent pom.xml and common module, build all modules
                            affectedModules = []
                            buildAll = true
                            return
                        } else {
                            if (c.indexOf("/") > 1) { // Filter all affected modules
                                affectedModules.add(c.substring(0, c.indexOf("/")))
                                println "Affected Modules: ${c.substring(0, c.indexOf("/"))}"
                            }
                        }
                    }
                    println "Affected Modules: ${affectedModules}"
                }
            }
        }

//         stage('Checkout') {
//             steps {
//                 git url: 'https://github.com/nemezisSherokee/cleanproject.git/', branch: 'feature/test-git-hub-jenkins-webhook'
//             }
//         }

        stage('Test') {
            steps {
                withMaven {
                    sh "mvn clean test"
                } // withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports
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
                                        sh "pwd"
                                        sh "mvn clean ${goal} -P${profile}"
                                    }
                                } else {
                                    dir("${module}") {
                                        bat "pwd"
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
                    return env.BRANCH_NAME.startsWith('feature') &&  affectedModules.size() > 0
                }
            }
            steps {
                script {
                    def uniqueModules = affectedModules.unique()
                    for (module in uniqueModules) {
                        dir(module) {
                            stage("Build Docker Image for ${module}") {
                                def imageName = "${module}:${env.BUILD_ID}-SNAPSHOT"
                                def app = docker.build(imageName, ".")
                                docker.withRegistry('https://index.docker.io/v1/', 'DockerCredentials') {
                                    app.push()
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}
