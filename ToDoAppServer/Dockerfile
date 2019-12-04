FROM openjdk:8-jre-alpine
WORKDIR /usr/local/todoappserver
ADD ./target/ToDoAppServer-1.1.0.jar ./ToDoAppServer.jar
EXPOSE 8080

#Default Env vars
ENV DB_URL=jdbc:hsqldb:hsql://host.docker.internal:9002/todoapp-dev-db
ENV DB_USERNAME=SA
ENV DB_PASSWORD=123

CMD "java" "-jar" "ToDoAppServer.jar" "--spring.profiles.active=prod" "--spring.datasource.url=$DB_URL" "--spring.datasource.username=$DB_USERNAME" "--spring.datasource.password=$DB_PASSWORD"

##docker image commands
#docker build -t ayortanli/todoappserver:1.1.0 .
#docker run --name todoappservercontainer -d -p 8080:8080 ayortanli/todoappserver:1.1.0
#docker push ayortanli/todoappserver:1.1.0
