import React from "react";
import TaskSection from "./TaskSection";

import data from "./TestTaskData";

export default class TodoBoard extends React.Component {

    constructor(props) {
        super(props);
        this.extractAndSetTaskGroup(data);
    }

    extractAndSetTaskGroup(data){
        let todoItems = [];
        let inProgressItems = [];
        let doneItems = [];
        for(let i = 0; i < data.length; ++i){
            if(data[i].taskState === 'TODO')
                todoItems.push(data[i]);
            else if(data[i].taskState === 'IN_PROGRESS')
                inProgressItems.push(data[i]);
            else if(data[i].taskState === 'DONE')
                doneItems.push(data[i]);
        }
        this.state = {
            todoList: todoItems,
            inProgressList: inProgressItems,
            doneList: doneItems
        };
    }

    render(){
        return (
            <div className="jumbotron bg-dark text-center p-2">
                <span className="align-top">
                    <h1 className="text-light">Task List</h1>
                </span>
                <div className="container">
                    <div style={{minHeight: '85vh'}} className="row">
                        <div className="col-sm">
                            <TaskSection taskList={this.state.todoList} sectionName="To Do" className="bg-info"/>
                        </div>
                        <div className="col-sm">
                            <TaskSection taskList={this.state.inProgressList} sectionName="In Progress" className="bg-warning"/>
                        </div>
                        <div className="col-sm">
                            <TaskSection taskList={this.state.doneList} sectionName="Done" className="bg-primary"/>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}