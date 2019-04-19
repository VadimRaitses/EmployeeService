# EmployeeService
Spring Based API with employee entities for micro services architecture with rabbitmq
and event-ervice wich subscribe and receive all messages



### Run/Install: 

Initially main idea of project is to provide fast setup for producer-consumer spring based solutions with api's and exchanged entities,
and ung this solution in different profile modes.
In current setup all full loaded project will produce 4 docker containers connected in a network.
Employee Service for producing messages and storing entities in Mongo.
Event Service for receiving messages from queue and also storing them in Mongo
Docker with MongoDb.
Docker with RabbitMq.

services  itself can run locally without any docker setups.
it requires get two repositories, EmployeeService with EventService 
and run thru:
* ./gradle buildLocalContainers - for launching mongo and rabbitmq dockers 
* ./gradlew clean build run - for EmployeeService and  EventService running jars separately
but using "run" default profile will be profile: = -Dspring.profiles.active=dev, when mongo and rabbitmq will be a part of local network.
while regular profile will connect to mongo:27777 and rabbitmq:15672


#### Option 1
* Verify you have mongodb on you local machine if not, you can use docker instead.
*  ./gradlew buildLocalContainers 
* Build your EmployeeService with with dev profile   **./gradle clean build run**
* Build your EventService    with with dev profile   **./gradle clean build run**
run itself is part of application plugin and using   -Dspring.profiles.active=dev


#### Option 2 
*  ./gradlew startDocker 




#### Requests:
EmployeeService exposed on 8080 port and 
EventService exposed on 8081 port by default.


**create department :**

curl -X POST \
  http://localhost:8080/department/ \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{"name":"department"}'


**create employee :**

employee should be created with existing department id

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


**get Employee** 

curl -X GET \
  http://localhost:8080/employee/{id} \
  -H 'cache-control: no-cache' 


** delete employee **
curl -X DELETE \
  http://localhost:8080/employee/{id} \
  -H 'cache-control: no-cache'
  

**get Events** 

curl -X GET \
  http://localhost:8081/event/{employeeid} \
  -H 'cache-control: no-cache'

