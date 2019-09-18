def project = 'kubo-financiero-portal'
def appName = 'testingTechTalk'
def branchMap = [develop: 'qa', master: 'prod']
def imageTag = "gcr.io/${project}/${appName}:${branchMap[env.BRANCH_NAME]?:env.BRANCH_NAME}.${env.BUILD_NUMBER}"

pipeline {
  agent {
    kubernetes {
      label "expose-documents"
      defaultContainer 'jnlp'
      yaml """
apiVersion: v1
kind: Pod
metadata:
labels:
  component: ci
spec:
  # Use service account that can deploy to all namespaces
  serviceAccountName: cd-jenkins
  containers:
  - name: java8
    image: openjdk:8-jdk
    command:
    - cat
    tty: true
  - name: gcloud
    image: gcr.io/kubo-financiero-portal/gcloud-docker-node:2
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-socket-volume
    securityContext:
      privileged: true
    command:
    - cat
    tty: true
  - name: kubectl
    image: gcr.io/cloud-builders/kubectl
    command:
    - cat
    tty: true
  volumes:
  - name: docker-socket-volume
    hostPath:
      path: /var/run/docker.sock
      type: File
"""
}
  }
  stages {
    stage('Test & Sonar anaylsis') {
      steps {
        withSonarQubeEnv('sonarqube-k8s') {
          container('java8') {
            sh """
              ./gradlew clean build
              ./gradlew sonarqube -Dsonar.host.url=https://sonar.kubofinanciero.app -Dsonar.login=9f0b78d758468c043ef50a30db9e759f1be9d260 -Dsonar.projectKey=${appName} -Dsonar.exclusions=**/TestingTechTalk*
            """
          }
        }
        script {
          sh "sleep 5"
          def qg = waitForQualityGate()
          if (qg.status != 'OK') {
            error "Pipeline aborted due to quality gate failure: ${qg.status}"
          }
        }
      }
    }

    stage('Build and push image with Container Builder') {
      when {
        anyOf {
          branch 'develop'
          branch 'master'
        }
      }
      steps {
        container('gcloud') {
          sh """
            docker build -t ${imageTag} .
            gcloud docker -- push ${imageTag}
          """
        }
      }
    }

    stage('Deploy QA') {
      when {
        anyOf { branch 'develop' }
      }
      steps {
        container('kubectl') {
          sh("sed -i.bak 's#gcr.io/kubo-financiero-portal/${appName}#${imageTag}#' ./k8s/qa.yml")
          sh("kubectl apply -f k8s/qa.yml")
        }
      }
    }

    stage('Deploy PRODUCTION') {
      when {
        anyOf { branch 'master' }
      }
      steps {
        container('kubectl') {
          sh("sed -i.bak 's#gcr.io/kubo-financiero-portal/${appName}#${imageTag}#' ./k8s/prod.yml")
          sh("kubectl apply -f k8s/prod.yml")
        }
      }
    }
  }

  post {
    failure {
      script {
        currentBuild.result = 'FAILURE'
      }
    }

    always {
      step([$class: 'Mailer',
        notifyEveryUnstableBuild: true,
        recipients: "tecnologia@kubofinanciero.com",
        sendToIndividuals: true])
    }
  }

}