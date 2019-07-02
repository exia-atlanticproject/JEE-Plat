pipeline {
  agent any
  }
  stages {
    stage('Build') {
      steps {
        dir(path: 'src') {
          Execute Docker info
        }

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