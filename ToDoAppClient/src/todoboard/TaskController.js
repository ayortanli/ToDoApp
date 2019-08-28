export default class TaskController {

    retrieveAllTasks(handleResult){
        let xhttp = this.createDefaultRequestObject("GET", "/tasks.json", handleResult);
        xhttp.send("");
    }

    deleteTask(taskId, handleResult) {
        let xhttp = this.createDefaultRequestObject("GET", "./deleteResult.json", handleResult);
        xhttp.send(taskId);
    }

    updateTaskState(taskId, newState, handleResult) {
        let xhttp = this.createDefaultRequestObject("GET", "./updateStateResult.json", handleResult);
        xhttp.send({
            "taskId":taskId,
            "newState":newState
        });
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
            "taskId": taskId,
            "taskTitle":taskTitle,
            "taskDescription":taskDescription
        });
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
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let result = JSON.parse(xhttp.response);
            if(result.errorCode===0)
                handleResult(result.resultObject);
            else
                alert("Error Code: "+ result.errorCode+ "\nError Message: "+result.errorMessage);
        }
    }


}