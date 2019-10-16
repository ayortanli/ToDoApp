export default class TaskController {

    retrieveAllTasks(handleResult){
        let xhttp = this.createDefaultRequestObject("GET", "/todos", handleResult);
        xhttp.send();
    }

    deleteTask(taskId, handleResult) {
        let xhttp = this.createDefaultRequestObject("DELETE", "/todos/"+taskId, handleResult);
        xhttp.send();
    }

    createTask(taskTitle, taskDescription, handleResult) {
        let xhttp = this.createDefaultRequestObject("POST", "/todos", handleResult);
        xhttp.send(JSON.stringify({
                    "taskTitle":taskTitle,
                    "taskDescription":taskDescription
            }));
    }

    updateTask(taskId, taskTitle, taskDescription, handleResult) {
        let xhttp = this.createDefaultRequestObject("PUT", "/todos/"+taskId, handleResult);
        xhttp.send(JSON.stringify({
            "taskTitle":taskTitle,
            "taskDescription":taskDescription
        }));
    }

    updateTaskState(taskId, newState, handleResult) {
        let xhttp = this.createDefaultRequestObject("PUT", "/todos/state/"+taskId, handleResult);
        xhttp.send(newState);
    }

    archiveCompletedTasks(taskList, handleResult) {
        let xhttp = this.createDefaultRequestObject("PUT", "/todos/archive", handleResult);
        xhttp.send(JSON.stringify(taskList.map(task => task.taskId)));
    }

    createDefaultRequestObject(method, url ,handleResult){
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = ()=>this.handleRemoteCall(xhttp, handleResult);
        xhttp.open(method, process.env.SERVER_URL + url, true);
        xhttp.setRequestHeader("Content-Type", "application/json");
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