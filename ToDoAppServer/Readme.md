#ToDo App Server
Spring Boot Based ToDo Application Backend Project

This application is implemented for using in Proof of Concept (POC) works. 
It includes basic crud operations for simple ToDo task list.

Service;
- Consist of three layers (controller, service, repository)
- Stores data in memory (for now).
- Provides a rest controller interface.
- Uses same entity in all layers for simplicity.
- Has sample dockerfile and kubernetes deployment file also

Key Points:
- Project uses 8090 port in development profile, 8080 port in production profile
- Cross-Origin Resource Sharing (CORS) from any domain with any method is allowed in development profile. 
  (In order to test with [frontend](../ToDoAppClient) project)
  See corsConfigurer method in [Application.java](./src/main/java/com/ay/todo/Application.java)

#Installment
You can run project with `mvn spring-boot:run` command. 
To create production jar file, use `mvn package` command.

In order to create Docker image after creating jar file:
```sh
$ docker build -t ayortanli/todoappserver:1.0.2 .
```
You can run your container with:
```sh
$ docker run --name todoappservercontainer -d -p 8080:8080 ayortanli/todoappserver:1.0.2
```
Push your image to repository as follows:
```sh
$ docker push ayortanli/todoappserver:1.0.2
```
Finally, deploy your application to k8s with following command:
```sh
$ kubectl create -f todoappserver-k8s.yaml
```
If you want to use available images without recreation, use following url in yaml files
(https://cloud.docker.com/repository/docker/ayortanli/todoappserver)
 
