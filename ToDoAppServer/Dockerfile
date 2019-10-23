FROM openjdk:8-jre-alpine
WORKDIR /usr/local/todoappserver
ADD ./target/ToDoAppServer-1.0.jar ./ToDoAppServer.jar
EXPOSE 8080
CMD "java" "-jar" "ToDoAppServer.jar" "--spring.profiles.active=prod"

##docker image commands
#docker build -t ayortanli/todoappserver:1.0.1 .
#docker run --name todoappservercontainer -d -p 8080:8080 ayortanli/todoappserver:1.0.1
#docker push ayortanli/todoappserver:1.0.1
