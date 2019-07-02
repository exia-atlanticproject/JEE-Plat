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
          sh 'docker tag jee-platform:latest jdieuze/jee-platform:latest'
          sh 'docker push jdieuze/jee-platform:latest'
          
          sh 'docker build --build-arg target=data-controller --no-cache -t data-controller .'
          sh 'docker tag jee-platform:latest jdieuze/data-controller:latest'
          sh 'docker push jdieuze/data-controller:latest'
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
