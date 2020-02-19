#ToDo App Client
ToDo Application Frontend Project

This application is implemented for using in Proof of Concept (POC) works. 
It includes basic crud operations for simple ToDo task list.

Development environment is based on;
- nodejs for development runtime
- webpack for module bundling
- react for ui development
    
Key Points:
- TodoAppClient is deployed as nodejs server. 
Deployment port is retrieved from environment variable.
If not defined (i.e. in development profile) 8080 is used. See [server.js](./server/server.js) for details.
In production profile, 80 is used as server port. (Defined in [Dockerfile](./Dockerfile))
- As backend server url; process.env.SERVER_URL environment variable is used. If not defined 
default development backend url is activated. (Which is given in [webpack config file](./webpack.config.js))


#Installment
You can run project with `npm start` command. 
To create production distribution package under dist folder, use `npm run build` command.

In order to create Docker image after creating distribution package:
```sh
$ docker build -t ayortanli/todoappclient:1.0.0 .
```
You can run your container with:
```sh
$ docker run -d --name todoappclientcontainer -p 80:80 ayortanli/todoappclient:1.0.0
```
Push your image to repository as follows:
```sh
$ docker push ayortanli/todoappclient:1.0.0
```
Finally, deploy your application to k8s with following command:
```sh
$ kubectl create -f todoappclient-k8s.yaml
```
If you want to use available images without recreation, use following url in yaml files
(https://cloud.docker.com/repository/docker/ayortanli/todoappclient)
 
