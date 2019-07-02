pipeline {
  agent {
    dockerfile {
      filename 'Dockerfile'
      label 'test'
    }
  }
  }
  stages {
    stage('Build') {
      steps {
        dir(path: 'src') {
          sh 'mvn compiler:compile'
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