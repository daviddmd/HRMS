# HRMS - Human Resources Management System

Human Resources Management System developed with Spring Boot with the Microservices pattern.

It is composed by three distinct microservices, [department-service](department-service), concerning tasks related to
the Company's (physical) locations and departments, [job-service](job-service) concerning tasks related to the Company's
Jobs and the filled contracts associated with them, and finally the [employee-service](employee-service), concerning the
Company's employees and their respective attributes.

The communication between the microservices are handled synchronously with Feign and asynchronously with RabbitMQ.

Accompanying the three microservices are the [Discovery Service](discovery-service) (Eureka), that must be started in
order for these three to be able to communicate with each other (the parameters may be adjusted in the discovery
service's [application.yml](discovery-service/src/main/resources/application.yml) file, and these changes must be
reflected in each microservice's configuration), as well as an [API Gateway Service](gateway-service) (Spring Cloud
Gateway) to map each microservice routes to easily accessible endpoints.

The order of execution should start with the Discovery Service followed by the other services, in no particular order,
since each regularly polls the Discovery (Eureka) service for new information.

If the necessary RabbitMQ exchanges and Queues (described in
the [Employee Service](employee-service/src/main/java/com/example/employeeservice/Configuration/RabbitMQConfig.java) and
[Department Service](department-service/src/main/java/com/example/departmentservice/Configuration/RabbitMQConfig.java))
aren't set up before the execution of the program, they will be created in the first instantiation of the producers,
which may result in errors of services that depend on them on their startup.

Authorization and Authentication is done through a Keycloak server. The configuration may be set at the API
Gateway [configuration file](gateway-service/src/main/resources/application.yml).

A suitable JAR may be built for each service with `gradle build`, as well as the respective OpenAPI information with
`gradle generateOpenApiDocs`. The OpenAPI docs endpoint may need to be adjusted at `build.gradle` depending on the
`application.yml` configuration (regarding the application server port).

