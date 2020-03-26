
export default class RemoteObject {

    createDefaultRequestObject(method, url ,handleResult){
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = ()=>this.handleRemoteCall(xhttp, handleResult);
        xhttp.open(method, SERVER_URL + url, true);
        xhttp.withCredentials = true;
        xhttp.setRequestHeader("Content-Type", "application/json");
        if(method==="POST" || method ==="PUT" || method ==="DELETE")
            xhttp.setRequestHeader("X-XSRF-TOKEN",this.getCsrfToken());
        return xhttp;
    }

    handleRemoteCall(xhttp, handleResult){
        if (xhttp.readyState == 4) {
            if(xhttp.status == 200) {
                if(xhttp.responseText !== '')
                    handleResult(JSON.parse(xhttp.response));
                else
                    handleResult();
            } else if (xhttp.status == 401) {
                alert("Authentication failed!!!");
            } else if (xhttp.status == 403) {
                alert("You don't have permission for this operation!");
            }else {
                alert("Error Code: HTTP(" + xhttp.status + ")\nError Message: " + xhttp.responseText);
            }
        }
    }

    getCsrfToken(){
        let token = document.cookie;
        token = token.slice(token.indexOf("XSRF-TOKEN=")+11);
        if(token.indexOf(";")>-1)
            token = token.slice(0, token.indexOf(";"));
        return token;
    }
}