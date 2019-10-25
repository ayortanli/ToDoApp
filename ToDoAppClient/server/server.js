//used only in docker image
const express = require("express");

const app = express();

app.use(express.static('.'));

const server = app.listen(process.env.PORT || 8080, function(){
    let port = server.address().port;
    console.log("Server started at http://localhost:%s", port);
    console.log("process.env: " + JSON.stringify(process.env));
});