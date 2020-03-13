import RemoteObject from "./RemoteObject"

export default class UserController {

    constructor(){
        this.remoteObject = new RemoteObject();
    }

    getUser(handleResult){
        let xhttp = this.remoteObject.createDefaultRequestObject("GET", "/user", handleResult);
        xhttp.send();
    }

    logout(){
        location.href = SERVER_URL+"/logout";
    }
}