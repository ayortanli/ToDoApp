# ToDoApp
ToDoApp is developed to use for Proof of Concept works. 
It consists of a backend and a frontend projects. 
In development profile, backend and frontend applications can directly call each other.
On the other hand; in production profile, environment is implemented is an kubernetes cluster.

The system architecture is as follows:

![General Architecture](./images/GeneralArchitecture.png)

Details can be found in subproject pages.

- [ToDoAppClient Project](./ToDoAppClient)
- [ToDoAppServer Project](./ToDoAppServer)
- [Other configuration files](./TodoAppConfig)
  - [todoappingress-k8s.yaml](./TodoAppConfig/todoappingress-k8s.yaml) creates nginx ingress for
  system routing. 