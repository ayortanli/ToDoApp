import React from "react";
import Task from "./Task";
export default class TaskSection extends React.Component {

    renderTasks(){
        let tasks = [];
        for (let i = 0; i < this.props.taskList.length; i++) {
            tasks.push(<Task key={`task ${i}`} data={this.props.taskList[i]}/>);
        }
        return tasks;
    }

    render(){
        return (
            <div style={{minHeight: '82vh'}} className={`jumbotron text-center px-2 pb-1 pt-0 ${ this.props.className }`}>
                <div className="card-header h4">
                    {this.props.sectionName}
                </div>
                <span>
                    {this.renderTasks()}
                </span>
            </div>
        );
    }
}