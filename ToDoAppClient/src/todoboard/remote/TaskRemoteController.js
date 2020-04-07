import RemoteObject from "./RemoteObject"

export default class TaskRemoteController {

    constructor(){
        this.remoteObject = new RemoteObject();
    }

    retrieveAllTasks(handleResult){
        let xhttp = this.remoteObject.createDefaultRequestObject("GET", "/todos", handleResult);
        xhttp.send();
    }

    deleteTask(taskId, handleResult) {
        let xhttp = this.remoteObject.createDefaultRequestObject("DELETE", "/todos/"+taskId, handleResult);
        xhttp.send();
    }

    createTask(taskTitle, taskDescription, handleResult) {
        let xhttp = this.remoteObject.createDefaultRequestObject("POST", "/todos", handleResult);
        xhttp.send(JSON.stringify({
                    "taskTitle":taskTitle,
                    "taskDescription":taskDescription
            }));
    }

    updateTask(taskId, taskTitle, taskDescription, handleResult) {
        let xhttp = this.remoteObject.createDefaultRequestObject("PUT", "/todos/"+taskId, handleResult);
        xhttp.send(JSON.stringify({
            "taskTitle":taskTitle,
            "taskDescription":taskDescription
        }));
    }

    updateTaskState(taskId, newState, handleResult) {
        let xhttp = this.remoteObject.createDefaultRequestObject("PUT", "/todos/state/"+taskId, handleResult);
        xhttp.send(newState);
    }

    archiveCompletedTasks(taskList, handleResult) {
        let xhttp = this.remoteObject.createDefaultRequestObject("PUT", "/todos/archive", handleResult);
        xhttp.send(JSON.stringify(taskList.map(task => task.taskId)));
    }
}