pipeline {
    agent any

    stages {

        stage ('Unit Test') {
                    steps {
                        script {
                            sh 'mvnw test'
                        }
                    }
                }

        stage ('Build') {
            steps {
                script {
                    sh 'mvnw clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerapp = docker.build("alexholanda/algamoney-api:${env.BUILD_ID}", '-f ./Dockerfile ./')
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                        dockerapp.push('latest')
                        dockerapp.push("${env.BUILD_ID}")
                    }
                }
            }
        }

        stage('Deploy Kubernetes') {
            environment {
                tag_version = "${env.BUILD_ID}"
            }

            steps {
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    sh 'sed -i "s/{{TAG}}/$tag_version/g" ./k8s/deployment.yaml'
                    sh 'kubectl apply -f ./k8s/deployment.yaml'
                }
            }
        }

    }
}