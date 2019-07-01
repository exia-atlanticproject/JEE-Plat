pipeline {
  agent {
    docker {
      image 'maven:3.6.1-jdk-8'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn --version'
      }
    }
    stage('Test') {
      steps {
        echo 'mvn test'
      }
    }
    stage('Deploy') {
      steps {
        echo 'Deploying....'
      }
    }
  }
}