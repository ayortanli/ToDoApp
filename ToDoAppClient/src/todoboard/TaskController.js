export default class TaskController {

    retrieveAllTasks(handleResult){
        let xhttp = this.createDefaultRequestObject("GET", "/tasks.json", handleResult);
        xhttp.send();
    }

    deleteTask(taskId, handleResult) {
        let xhttp = this.createDefaultRequestObject("GET", "./deleteResult.json", handleResult);
        xhttp.send();
    }

    createTask(taskTitle, taskDescription, handleResult) {
        let xhttp = this.createDefaultRequestObject("GET", "./createTaskResult.json", handleResult);
        xhttp.send({
            "taskTitle":taskTitle,
            "taskDescription":taskDescription
        });
    }

    updateTask(taskId, taskTitle, taskDescription, handleResult) {
        let xhttp = this.createDefaultRequestObject("GET", "./updateTaskResult.json", handleResult);
        xhttp.send({
            "taskTitle":taskTitle,
            "taskDescription":taskDescription
        });
    }

    updateTaskState(taskId, newState, handleResult) {
        let xhttp = this.createDefaultRequestObject("GET", "./updateStateResult.json", handleResult);
        xhttp.send(newState);
    }

    archiveCompletedTasks(taskList, handleResult) {
        let xhttp = this.createDefaultRequestObject("GET", "./archiveTasksResult.json", handleResult);
        xhttp.send(taskList);
    }

    createDefaultRequestObject(method, url ,handleResult){
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = ()=>this.handleRemoteCall(xhttp, handleResult);
        xhttp.open(method, url, true);
        xhttp.setRequestHeader("Content-type", "application/json");
        return xhttp;
    }

    handleRemoteCall(xhttp, handleResult){
        if (xhttp.readyState == 4) {
            if(xhttp.status == 200) {
                if(xhttp.responseText !== '')
                    handleResult(JSON.parse(xhttp.response));
                else
                    handleResult();
            } else {
                alert("Error Code: HTTP(" + xhttp.status + ")\nError Message: " + xhttp.responseText);
            }
        }
    }


}