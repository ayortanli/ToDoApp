#ToDo App Server
Spring Boot Based ToDo Application Backend Project

This application is implemented for using in Proof of Concept (POC) works in scalable environment. 
It includes basic crud operations for simple ToDo task list.

Service;
- Consist of three layers (controller, service, repository)
- Manage data in hsqldb over Spring Data.
- Provides a rest controller interface.
- Uses same entity in all layers for simplicity.
- Has sample dockerfile and kubernetes deployment file also

Key Points:
- Project uses 8090 port in development profile, 8080 port in production profile
- Cross-Origin Resource Sharing (CORS) from any domain with any method is allowed in development profile. 
  (In order to test with [frontend](../ToDoAppClient) project)
  See corsConfigurer method in [Application.java](./src/main/java/com/ay/todo/Application.java)
- Database configuration is given as environment variables inside DockerFile. (By this way, it can also be set in k8s config file) To run application,
  do not forget to run [sql scripts](../TodoAppConfig/todoapp-db.sql) in your database schema.

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
Following, configuration files should also be applied for database access:

- Secret objects for database connection [todoappsecrets-k8s.yaml](../TodoAppConfig/todoappsecrets-k8s.yaml):
```sh
$ kubectl create -f todoappsecrets-k8s.yaml
```
After creating secret object, use related kubectl command to change secrets with real values. Or, as a much more 
easy option, edit and reapply the config file.

- Database endpoint service creation (Used as database proxy by all services inside k8s cluster)
[todoappdb-k8s.yaml](../TodoAppConfig/todoappdb-k8s.yaml)
```sh
$ kubectl create -f todoappdb-k8s.yaml
```
If you want to use available images without recreation, use following url in yaml files
(https://cloud.docker.com/repository/docker/ayortanli/todoappserver)
 
