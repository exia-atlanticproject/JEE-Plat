pipeline {
  agent {
    label 'master'
  }
  stages {
    stage('Build') {
      steps {
        dir(path: 'src') {
          sh 'docker login -u jdieuze -p $DOCKER_PASSWORD'
          sh 'docker build --no-cache -t jee-platform .'
          sh 'docker tag jdieuze/jee-platform:latest'
          sh 'docker push jdieuze/jee-platform:latest'
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
