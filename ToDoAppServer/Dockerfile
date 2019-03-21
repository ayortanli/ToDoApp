FROM openjdk:8-jre-alpine
WORKDIR /usr/local/todoappserver
ADD ./target/ToDoAppServer-1.0.jar ./ToDoAppServer.jar
EXPOSE 8080
CMD "java" "-jar" "ToDoAppServer.jar"

##docker image commands
#docker build -t todoappserver:1.0
#docker run --name todoappservercontainer -d -p 8080:8080 todoappserver:1.0
