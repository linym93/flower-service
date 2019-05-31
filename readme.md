(1) change directory to the root folder where pom.xml locates, use maven to build flower-service-0.0.1-SNAPSHOT.jar
    mvn clean install
   
(2) After the application jar is created, it can be run locally, deployed to docker container and  kubernate
    1. run it in local machine:  start the application in target directory
         java -jar ./flower-service-0.0.1-SNAPSHOT.jar
 
    2. run it in docker container: from root directory where Dockerfile locates, use docker command to create and run image
		   create image:            docker build -t flowerservice .
		   check image is created:  docker images
		   run the image:           docker run -p 8080:8080 flowerservice
		   
	3. use deployment.yaml to deploy the docker image to minikube
	
(3) access endpoints:

1. Get all the userDocs

command: 
curl -X GET \
  http://localhost:8080/flower-service/userDocs \
  -H 'content-type: application/json' 
  
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
    
2. get a single userDoc by id
 command: 
 curl -X GET \
  http://localhost:8080/flower-service/userDocs/4 \
  -H 'content-type: application/json' \
  
  result:
  {
    "id": 4,
    "userId": 1,
    "title": "eum et est occaecati",
    "body": "ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit",
    "_links": {
        "self": {
            "href": "http://localhost:8080/flower-service/userDocs/4"
        }
    }
}

3. get count of unique userId
command:
curl -X GET \
  http://localhost:8080/flower-service/userDocs/userCount \
  -H 'content-type: application/json' \

result:
10

4. update title, body of userDoc 4 using put (Note all 4 fields are needed)
command:
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

Result:
{
    "id": 4,
    "userId": 1,
    "title": "1800Flowers",
    "body": "1800Flowers",
    "_links": {
        "self": {
            "href": "http://localhost:8080/flower-service/userDocs/4"
        }
    }
}

(5) update title, body of userDoc 4 using patch (only specify the fields need to be updated)
curl -X PATCH \
  http://localhost:8080/flower-service/userDocs/4 \
  -H 'accept: application/json' \
  -H 'content-type: application/json' \
  -d '{
    "title": "1800Flowers",
    "body":"1800Flowers"
}'

Results:
{
    "id": 4,
    "userId": 1,
    "title": "1800Flowers",
    "body": "1800Flowers",
    "_links": {
        "self": {
            "href": "http://localhost:8080/flower-service/userDocs/4"
        }
    }
}


  