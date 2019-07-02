pipeline {
  agent {
    label 'master'
  }
  }
  stages {
    stage('Build') {
      steps {
        dir(path: 'src') {
          sh 'docker info'
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
