# Homework

It is a take-home coding challenge.

## Getting Started

There are 3 ways to run this Spring Boot application.

* [Run in a local IDE](#run-in-a-local-ide)
* [Run as an executable Jar file](#run-as-an-executable-jar-file)
* [Run in a Docker container](#run-in-a-docker-container)

### General Info

* A `Swagger` page is configured to visually test the endpoint after the application is up and running. See [Running the test](#running-the-test) for more details.
* The endpoint will calculate the total fees given a combination of department, category, sub category and type. Note that `department` is mandatory, while `category`,
  `sub category` and `type` are optional. If it is not provided, the endpoint will calculate the fees for `All` the entries on that level.
* Junit tests with `Mockito` are available to verify the correctness of the solution. 

### Prerequisites

* Install JDK 8.
* Install Apache Maven (i.e. 3.6.3).
* Install the local IDE (i.e. IntelliJ). ***Note that Lombok is used to reduce boilerplate codes, and you need to install the lombok plugin in your IDE.***
* Install Docker (i.e. Desktop version or Docker Toolbox for Windows 10 Home)

### Run in a local IDE

* Clone the project from [GitHub](https://github.com/terrydship/clariti-homework) to your local IDE (i.e. IntelliJ)
* Download and resolve all the maven dependencies.
* Run `ClaritiHomeworkApplication.java` to start the application, alternatively run the following command in the terminal.
```
mvn spring-boot:run
```
* Run `ClaritiHomeworkServiceTest.java` and `ClaritiHomeworkControllerTest.java` for the test cases.

### Run as an executable Jar file

* Run the following command in the IDE terminal or a command window, assuming you are under the project root folder.
```
mvn clean package
java -jar target/homework-0.0.1.jar
```

### Run in a Docker container

* **Important:** Depending on if you are using `Docker Desktop` version or `Docker Toolbox`, the IP address to access the 
application endpoints could be different, you may verify with the following command
```
docker-machine ip
```
* Run the following command to generate the .jar file for the application.
```
mvn clean package
```
* Build the docker image with the following command, where `terrydship/clariti-homework` is a custom tag name.
```
docker build -t terrydship/clariti-homework .
```
* Run the docker container from the image with the following command, where `terrydship/clariti-homework` is the tag name I previously created.
```
docker run -p 8080:8080 terrydship/clariti-homework
```
* Some other useful command lines when running the application in docker
```
docker container ls
docker stop {CONTAINER ID}

docker image ls
docker image rm {IMAGE ID} -f
```

### Running the test

Once the application is up and running, you may do the following:

* Use the following URL to access the `Swagger` page to visually test the solutions.
i.e. `http://localhost:8080/homework/api/swagger-ui.html`. Expand the `Controller` section
on the page to test the endpoints.
```
http://{host}:{port}/homework/api/swagger-ui.html
```

Note that a `RuntimeException` will be thrown when
```
1. department is not provided
2. Invalid combination of department, category, sub category and type is provided. i.e. Category does not exist in the given department
3. Invalid department/category/sub category/type is provided. i.e. Input value does not come from the defined enum
```
