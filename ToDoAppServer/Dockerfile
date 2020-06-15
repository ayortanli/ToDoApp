FROM openjdk:8-jre-alpine
WORKDIR /usr/local/todoappservertodo
ADD ./target/ToDoAppServer-1.1.1.jar ./ToDoAppServer.jar
EXPOSE 8080

#Default Env vars
ENV DB_URL=jdbc:hsqldb:hsql://host.docker.internal:9002/todoapp-dev-db
ENV DB_USERNAME=SA
ENV DB_PASSWORD=123
ENV REDIS_URL=host.docker.internal
ENV REDIS_PORT=6379

CMD "java" "-jar" "ToDoAppServer.jar" "--spring.profiles.active=prod" "--spring.datasource.url=$DB_URL" "--spring.datasource.username=$DB_USERNAME" "--spring.datasource.password=$DB_PASSWORD" "--spring.redis.host=$REDIS_URL" "--spring.redis.port=$REDIS_PORT"

##docker image commands
#docker build -t ayortanli/todoappserver:1.1.1 .
#docker run --name todoappservercontainer -d -p 8080:8080 ayortanli/todoappserver:1.1.1
#docker push ayortanli/todoappserver:1.1.1
