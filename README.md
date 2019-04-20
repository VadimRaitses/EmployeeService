# EmployeeService
Spring Based API with abstract employee entities for **Dockerized** micro services architecture with **RabbitMQ**, **MongoDb**, Auth/Authorization layer, **Swagger**
and [EventService](https://github.com/VadimRaitses/EventService.git) which subscribe for message broker. 


## Requirements


* [Gradle less than 5 (available in /gradle)](https://gradle.org/)
* [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
* [Docker knowledge](https://www.docker.com/)
* [RabbitMq knowledge](https://www.rabbitmq.com/)
* [MongoDb knowledge](https://www.mongodb.com/)

## Run/Install: 


Initially main idea of project is to provide fast setup for producer-consumer spring based solutions with api's exchanged entities,
and run this solution in different profile modes.

In current setup all full loaded project will produce 4 docker containers connected in a their hub network network.

    1. Employee Service for producing messages and storing entities in Mongo.
    2. Event Service for receiving messages from queue and also storing them in Mongo
    3. Docker with MongoDb. no required authenticateion for mongo.
    4. Docker with RabbitMq.

Services  itself can run locally without any docker setups.
it requires two of  repositories, EmployeeService with [EventService](https://github.com/VadimRaitses/EventService.git)
and run thru:
* ./gradle buildLocalContainers - for launching mongo and rabbitmq dockers 
* ./gradlew clean build run - for EmployeeService and  [EventService](https://github.com/VadimRaitses/EventService.git) running jars separately
but using "run" default profile will be profile: = -Dspring.profiles.active=dev, when mongo and rabbitmq will be a part of local network.
while regular profile will connect to mongo:27777 and rabbitmq:15672


#### Option 1:


   * Verify you have mongodb on you local machine if not, you can use docker instead.
      
    ./gradlew buildLocalContainers 
       
   * Build your EmployeeService with with dev profile   
           
    ./gradle clean build run
    
   * Build your [EventService](https://github.com/VadimRaitses/EventService.git)   with with dev profile  
   
    ./gradle clean build run
        
   * run itself is part of application plugin and using   
        
    -Dspring.profiles.active=dev


#### Option 2:

     This option have consequtive chain because of integration part.
    ./gradlew buildDockers clean build buildServices
    

## Environment:


Big part of launching environment are different gradle tasks, for creating building and launching dockers and connect them into network.
event.sh will clone [EventService](https://github.com/VadimRaitses/EventService.git)  and produce docker image for this service.
Available tasks:
    **startDocker** - will launch all solution. but will fail on integration test part.
    
   **buildLocalContainers** - will build mongo and rabbitmq without internal network

   **buildDockers** - will remove and build only mongo, rabbitmq containers and internal network.
   
   **buildServices** - build and run container of rest services (Event, Entity)

   **getEmployeeService** - will build employee container and connect to internal microservice network

   **getEventService** -  will build event container and connect to internal microservice network

   **removedockers** -will remove all solution containers and images built by this repo.




## SWAGGER

    http://localhost:8080/swagger-ui.html
    

### Requests:


EmployeeService exposed on 8080 port and 

[EventService](https://github.com/VadimRaitses/EventService.git) exposed on 8081 port by default.

This step will produce a user and authenticate him, so response will appear with jwt token and add a user to your curent repository

    curl -X POST 
    http://localhost:8080/auth/ \
    -H 'cache-control: no-cache' \
    -H 'content-type: application/json' \
    -d '{"email":"test","password":"test"}'

In response will appear authorization header with jwt token, which should be copied to other requst headers for authorization "Authorization":"Bearer aabbccdd_ee"


**create department :**

    curl -X POST \
    http://localhost:8080/department/ \
    -H 'authorization: authorization: Bearer aabbccdd_ee' \
    -H 'Content-Type: application/json' \
    -H 'cache-control: no-cache' \
    -d '{"name":"Chicago Bulls"}'

in successful case will respond created entity with id. and status 200

  
**create employee :**

employee should be created with already existing department id,
also email property of employee are unique.

    curl -X POST \
      http://localhost:8080/employee/ \
      -H 'authorization: authorization: Bearer aabbccdd_ee' \
      -H 'Content-Type: application/json' \
      -H 'cache-control: no-cache' \
      -d '{
        "email":"michael.jordan@nba.goat",
        "fullName":"Michael Jordan",
        "birthday":"1963-17-02",
        "department":{
        "id" : "54506471-36de-40f7-96cf-7b461f86f859",
        "name" : "Chicago Bulls"
        }
    }'

in successful case will respond created entity with id. and status 200

400 in case of provided bad department id

404 in case of department not found 

400 in case of  exisiting employee with same email,


**update employee :**

    curl -X PUT \
      http://localhost:8080/employee/{id} \
      -H 'authorization: authorization: Bearer aabbccdd_ee' \
      -H 'Content-Type: application/json' \
      -H 'cache-control: no-cache' \
      -d '{
        "email":"angelPEtro@gmaild.comdddmm",
        "fullName":"vadim Angulart",
        "birthday":"1985-09-10",
        "department":{
        "id" : "abd55a3d-371c-433e-8b28-7a0375d174ef",
        "name" : "department"
        }
    }'

in successful case will respond with status 204  



**GET Employee** 

    curl -X GET \
      http://localhost:8080/employee/{id} \
      -H 'cache-control: no-cache' 


**DELETE employee**

    curl -X DELETE \
      http://localhost:8080/employee/{id} \
      -H 'cache-control: no-cache'
  
in successful case will respond with status 204  

**GET Events** 

    curl -X GET \
      http://localhost:8081/event/{employeeid} \
      -H 'cache-control: no-cache'

array of events
