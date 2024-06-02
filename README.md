# NAGP

## Prerequisites
- Docker Engine (Docker Desktop)
- Kubernetes (Minikube)

## Steps to Build and Deploy the Spring Boot Application with MongoDB on Kubernetes, Implementing Rolling Strategy and Verifying Horizontal Pod Auto scaling

### 1. Create a JAR file of the Spring Boot Project
		1. Navigate to the project directory:
			cd Nagp/springboot-mongodb
   
		2. Create a JAR file of the project:
			./mvnw clean package

### 2. Build Docker Image

		1. Create a Docker image of the project:
			docker build -t manas_nagp_sbmgdc .
		
		2. Tag the created image:
			docker tag manas_nagp_sbmgdc manasabrol/manas_nagp_sbmgdc

		3. Push the image to Docker Hub:
			docker push manasabrol/manas_nagp_sbmgdc

### 3. Run Kubernetes Environment

		1. Start Minikube:
			minikube start

### 4. Deploy MongoDB on Kubernetes
		
		1. Switch to the database deployment directory:
			cd Nagp/Deployment/Database Deployment

		2. Apply the following Kubernetes configurations:
			kubectl apply -f mongo-configmap.yaml
			kubectl apply -f mongo-secret.yaml
			kubectl apply -f mongo-service.yaml
			kubectl apply -f mongo-statefulset.yaml
		
		3. Verify the MongoDB deployment:
			kubectl get pods

		4. Create a database in the Mongo shell:
			kubectl exec -it mongo-0 -- /bin/bash
			mongosh
			use admin
			show dbs
			use productdb
			
			db.createUser({
			user: "myuser",
			pwd:  "password",
			roles: [
				{ role: "readWrite", db: "productdb" }
			]
			});

### 5. Deploy the Spring Boot Application on Kubernetes
	
		1. Switch to the API deployment directory:
			cd Nagp/Deployment/API Deployment

		2. Apply the following Kubernetes configurations:
			kubectl apply -f api-configmap.yaml
			kubectl apply -f api-deployment.yaml
			kubectl apply -f api-service.yaml

		3. Verify the application pod:
			kubectl get pods

		4. Access the application:
			minikube service api-service --url

		5. Open the URL in a browser.
		
### 6. Perform a Rolling Update
	
		1. Switch to the project directory:
			cd Nagp/springboot-mongodb

		2. Create a new Docker image:
			docker build -t manas_nagp_sbmgdc:v2 .

		3. Tag the new image:
			docker tag manas_nagp_sbmgdc:v2 manasabrol/manas_nagp_sbmgdc:v2

		4. Push the new image to Docker Hub:
			docker push manasabrol/manas_nagp_sbmgdc:v2
			
		5. Update the image in api-deployment.yaml:
			Set the new image to manasabrol/manas_nagp_sbmgdc:v2
			
		6. Apply the updated deployment:
			cd Nagp/Deployment/API Deployment
			kubectl apply -f api-deployment.yaml
			kubectl get deployment

		7. Get the status of the deployment:
			kubectl rollout status deployment/api-deployment
		
		8. Describe the deployment for detailed status:
			kubectl rollout status deployment/api-deployment

		9. Get the list of pods:
			kubectl get pods -l app=api
		
		10. Rollback to a previous version:
			kubectl rollout undo deployment/api-deployment

### 7. Verify Data Persistence by Deleting Mongo Pod

		1. Delete the MongoDB pod:
			kubectl delete pod mongo-0

		2. Check the running pod:
			kubectl get pod

		3. Verify data persistence in the web application.
		
### 8. Implement Horizontal Pod Autoscaler (HPA)

		1. Switch to the HPA deployment directory:
			cd Nagp/Deployment/HPA Deployment

		2. Apply the HPA configuration:
			kubectl apply -f api-hpa.yaml

		3. Switch to the k8s-metrics directory:
			cd Nagp/Deployment/k8s-metrics
			kubectl apply .

		4. Check the HPA status:
			kubectl get hpa

		5. Check the replicas:
			kubectl get pods


Notes:
manasabrol is the Docker Hub username.
manas_nagp_sbmgdc is the image name.
