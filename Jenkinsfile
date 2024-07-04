pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'docker build . -t node-todo-app'
            }
        }
        stage('Run') {
            steps {
                sh 'docker run -d -p 8000:8000 --name node-todo-app node-todo-app'
            }
        }
    }
}
