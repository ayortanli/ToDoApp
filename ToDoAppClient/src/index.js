import React from 'react';
import ReactDOM from 'react-dom';
import TodoBoard from './todoboard/TodoBoard';
import "./style.css";

const element = document.createElement('div');
document.body.appendChild(element);

ReactDOM.render(
    <div className="container pt-2">
        <TodoBoard />
    </div>
    ,
    element
);
