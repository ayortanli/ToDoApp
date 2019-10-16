#Basic ToDo App Client

This application is implemented for using in Proof of Concept (POC) works. 
It includes basic crud operations for simple ToDo task list. For now, server communication is ignored and simple mock json files are used as server response. These files are under testData folder and should be copied under 
dist for mocking server responses.

- Development environment is based on 
    - nodejs for development runtime
    - webpack for module bundling
    - react for ui development
    
- Key Points:
    - As server url; process.env.SERVER_URL environment variable is used. If not defined 
    default development server url is activated. (Which is given in webpack config file)