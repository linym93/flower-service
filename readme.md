(1) change directory to the root folder where pom.xml locates, use maven to build flower-service-0.0.1-SNAPSHOT.jar
    mvn clean install
   
(2) After the application jar is created, it can be run locally, deployed to docker container and  kubernate

    1. run it in local machine:  start the application in target directory
         java -jar ./flower-service-0.0.1-SNAPSHOT.jar
 
    2. run it in docker container: from root directory where Dockerfile locates, use docker command to create and run image
		   create image and tag with version#:            
		           docker build -t flowerservice:v1 .
		   check image is created:  
		           docker images
		   run the image:           
		            docker run -p 8080:8080 flowerservice:v1
		   or run in background:
		            docker run -d -p 8080:8080 flowerserice:v1
		   
		   list all docker containers:  
		            docker ps -al
		   stop&remove a docker container: 
		            docker rm -f CONTAINER_ID
		   remove docker image : 
		            docker rmi ImageID
		   
    3. use deployment.yaml to deploy the docker image to minikube (install minikute and virtualbox)
         start minikube:
             minikube start
         to use local docker image run, to make if default add the command to .bash_profile:
            eval $(minikube docker-env)
         Build the image with the Docker daemon of Minikube :
            docker build -t flowerservice:v1 .
         create deployment, pods and service using yaml file:
            kubectl create -f deployment.yaml
         start dashboard  to check, one deploand two pods will be created for the deployment:
            minikube dashboard
           
(3) access endpoints:

1. Get all the userDocs

curl -X GET http://localhost:8080/flower-service/userDocs -H 'content-type: application/json' 
  

2. get userDocs orderby title:
  url -X GET http://localhost:8080/flower-service/userDocs?orderby=title -H 'content-type: application/json' 

 Result:
 
 [
    {
        "id": 1,
        "userId": 1,
        "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8080/flower-service/userDocs/1"
            }
        ]
    },
    {
        "id": 2,
        "userId": 1,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8080/flower_service/userDocs/2"
            }
        ]
    },
    ...
    ] 
    
  command:
    curl -X GET 'http://localhost:8080/flower-service/userDocs?orderby=title' 
  Results:  list of userDocs sort by title
  
  command:
    curl -X GET 'http://localhost:8080/flower-service/userDocs?userId=1'
  Results:  list of userDocs with userId=1
  
  command:
    curl -X GET 'http://localhost:8080/flower-service/userDocs?userId=1&orderby=title' 
  Results:  list of userDocs with userId=1 sort by title
  
  
    
2. get a single userDoc by id

 command: 
 
 curl -X GET \
  http://localhost:8080/flower-service/userDocs/4 \
  -H 'content-type: application/json' \
>>>>>>> f9fed737c4a5c12859a1c5eafe0562c2ac3ac723
  
3. get userDocs with specify userId
  url -X GET http://localhost:8080/flower-service/userDocs?userID=1 -H 'content-type: application/json' 
  
4. get userDocs with specify userId and orderby title:
  url -X GET http://localhost:8080/flower-service/userDocs?userID=1&orderby=title -H 'content-type: application/json' 

5. get a single userDoc by id
 
 curl -X GET http://localhost:8080/flower-service/userDocs/4 -H 'content-type: application/json' 

6. get count of unique userId

curl -X GET  http://localhost:8080/flower-service/userDocs/userCount -H 'content-type: application/json'

7. update title, body of userDoc 4 using put (Note all 4 fields are needed)

curl -X PUT \
  http://localhost:8080/flower-service/userDocs/4 \
  -H 'accept: application/json' \
  -H 'content-type: application/json' \
  -d '{
    "id": 4,
    "userId": 1,
    "title": "1800Flowers",
    "body":"1800Flowers"
}'


8. update title, body of userDoc 4 using patch (only specify the fields need to be updated)

curl -X PATCH \
  http://localhost:8080/flower-service/userDocs/4 \
  -H 'accept: application/json' \
  -H 'content-type: application/json' \
  -d '{
    "title": "1800Flowers",
    "body":"1800Flowers"
}'


  
