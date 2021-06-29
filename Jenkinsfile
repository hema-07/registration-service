pipeline {
    agent any
    stages {
        stage('Compile') {
            steps {
                withMaven(maven: 'Maven_3.6.0') {
                    sh 'mvn clean compile'
                }
            }
        }
        stage('Build') {
            steps {
                withMaven(maven: 'Maven_3.6.0') {
                    sh 'mvn clean package'
                }
            }
        }
        stage('Test') {
            steps {
                withMaven(maven: 'Maven_3.6.0') {
                    sh 'mvn test'
                }
            }
        }
        stage('Verify') {
            steps {
                withMaven(maven: 'Maven_3.6.0') {
                    sh 'mvn verify'
                }
            }
        }
    }
}