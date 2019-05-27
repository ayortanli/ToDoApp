import React from 'react';
import ReactDOM from 'react-dom';
import printMe from './print.js';


function component() {
    const element = document.createElement('div');
    const button = document.createElement("button");
    button.innerHTML = 'click Me and check the console';
    button.onclick = printMe;
    element.appendChild(button);

    const reactElement = document.createElement('div');
    element.appendChild(reactElement);
    ReactDOM.render(
        <h1>Hello, world!</h1>,
        reactElement
    );
    return element;
}

document.body.appendChild(component());
