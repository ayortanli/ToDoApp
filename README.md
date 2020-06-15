# ToDoApp
ToDoApp is developed to use for Proof of Concept works. 
It consists of a backend and a frontend projects. 
In development profile, backend and frontend applications can directly call each other.
On the other hand; in production profile, environment is planned and implemented as a kubernetes cluster.

The system architecture in production profile is as follows:

![General Architecture](./images/GeneralArchitecture.png)

Details can be found in subproject pages.

- [ToDoAppClient Project](./ToDoAppClient)
- [ToDoAppServer Project](./ToDoAppServer)
- AuthService
  - Auth related services like login, logout, authentication status check are implemented in this service. (For simplicity, TodoAppServer is used in place of authentication service. See [security configuration](./ToDoAppServer/src/main/java/com/ay/todo/SecurityConfiguration.java) of TodoAppServer for details.)
- [Other configuration files](./TodoAppConfig)
  - [todoapppublicingress-k8s.yaml](./TodoAppConfig/todoapppublicingress-k8s.yaml) and [todoappprivateingress-k8s.yaml](./TodoAppConfig/todoappprivateingress-k8s.yaml) creates nginx ingress for system routing. 
  - [todoappdb-k8s.yaml](./TodoAppConfig/todoappdb-k8s.yaml) creates an endpoint service which is used as database proxy inside k8s cluster. (Database is located as a static resource outside of the cluster)
  - [todoappsecrets-k8s.yaml](./TodoAppConfig/todoappsecrets-k8s.yaml) creates a secret object which holds database username and password. (This object should be updated with real db username/password by using related kubectl commands)
  - [todoappredis-k8s.yaml](./TodoAppConfig/todoappredis-k8s.yaml) creates a redis database container and a related service which is used for session sharing of services throughout the cluster
  - [todoapp-db.sql](./TodoAppConfig/todoapp-db.sql) contains sql scripts to construct application initial database.
  