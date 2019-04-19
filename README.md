# EmployeeService
Spring Based API with employee entities for micro services architecture with rabbitmq
and event-service which subscribe for message broker.



### Run/Install: 

Initially main idea of project is to provide fast setup for producer-consumer spring based solutions with api's exchanged entities,
and run this solution in different profile modes.

In current setup all full loaded project will produce 4 docker containers connected in a their hub network network.

1. Employee Service for producing messages and storing entities in Mongo.
2. Event Service for receiving messages from queue and also storing them in Mongo
3. Docker with MongoDb.
4. Docker with RabbitMq.

Services  itself can run locally without any docker setups.
it requires two of  repositories, EmployeeService with EventService 
and run thru:
* ./gradle buildLocalContainers - for launching mongo and rabbitmq dockers 
* ./gradlew clean build run - for EmployeeService and  EventService running jars separately
but using "run" default profile will be profile: = -Dspring.profiles.active=dev, when mongo and rabbitmq will be a part of local network.
while regular profile will connect to mongo:27777 and rabbitmq:15672


#### Option 1

* Verify you have mongodb on you local machine if not, you can use docker instead.
* ./gradlew buildLocalContainers 
* Build your EmployeeService with with dev profile   **./gradle clean build run**
* Build your EventService    with with dev profile   **./gradle clean build run**
run itself is part of application plugin and using   -Dspring.profiles.active=dev


#### Option 2 

*  ./gradlew startDocker 


#### Invironment:
Big part of launching environment are different gradle tasks, for creating building and launching dockers and connect them into network.
event.sh will clone EventService and produce docker image for this service.
Available tasks:

**startDocker** - will launch all solution.

**buildLocalContainers** - will build mongo and rabbitmq without internal network

**buildDockers** - will remove and build only mongo, rabbitmq containers and internal network.

**getEmployeeService** - will build employee container and connect to internal microservice network

**getEventService** -  will build event container and connect to internal microservice network

**removedockers** -will remove all solution containers and images built by this repo.


#### Requests:
EmployeeService exposed on 8080 port and 
EventService exposed on 8081 port by default.


**create department :**

curl -X POST \
  http://localhost:8080/department/ \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{"name":"department"}'

in successful case will respond created entity with id. and status 200

  
**create employee :**

employee should be created with already existing department id,
also email property of employee are unique.

curl -X POST \
  http://localhost:8080/employee/ \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"email":"vadim.vadim@vadim.vadim",
	"fullName":"vadim vadim",
	"birthday":"1985-09-10",
	"department":{
	"id" : "54506471-36de-40f7-96cf-7b461f86f859",
	"name" : "department"
	}
}'

in successful case will respond created entity with id. and status 200
400 in case of provided bad department id 400
404 in case of department not found 
400 in case of  exisiting employee with same email,


**update employee :**

curl -X PUT \
  http://localhost:8080/employee/{id} \
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



**get Employee** 

curl -X GET \
  http://localhost:8080/employee/{id} \
  -H 'cache-control: no-cache' 


** delete employee **
curl -X DELETE \
  http://localhost:8080/employee/{id} \
  -H 'cache-control: no-cache'
  
in successful case will respond with status 204  

**get Events** 

curl -X GET \
  http://localhost:8081/event/{employeeid} \
  -H 'cache-control: no-cache'

array of 
