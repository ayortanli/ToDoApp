import React from "react";
import Task from "./Task";
import { Jumbotron, CardHeader, Button } from 'reactstrap';

export default class TaskSection extends React.Component {

    renderTasks(){
        let tasks = [];
        for (let i = 0; i < this.props.taskList.length; i++) {
            tasks.push(<Task key={`task ${i}`}
                             onDelete={() => this.onDelete(this.props.taskList[i].taskId)}
                             onUpdate={() => this.onUpdate(this.props.taskList[i])}
                             onNextState={()=> this.onNextState(this.props.taskList[i])}
                             onPrevState={()=> this.onPrevState(this.props.taskList[i])}
                             data={this.props.taskList[i]}/>);
        }
        return tasks;
    }

    renderBoardHeader(){
        if(this.props.onArchive!==undefined){
            return  <CardHeader className="h4 row">
                        <div className="col-2"></div>
                        <div className="col-8">
                            {this.props.sectionName}
                        </div>
                        <div className="col-2 px-0">
                            <Button color="secondary" onClick={()=>this.props.onArchive()}
                                    className="float-right">Archive</Button>
                        </div>
                    </CardHeader>;
        } else if(this.props.onInsert!==undefined){
            return  <CardHeader className="h4 row">
                        <div className="col-2"></div>
                        <div className="col-8">
                            {this.props.sectionName}
                        </div>
                        <div className="col-2 px-0">
                            <Button color="secondary"  onClick={()=>this.props.onInsert()}
                                    className="float-right">Add</Button>
                        </div>
                    </CardHeader>;
        } else {
            return  <CardHeader className="h4">
                        {this.props.sectionName}
                    </CardHeader>;
        }
    }

    onDelete(taskId){
        this.props.onDelete(taskId);
    }

    onNextState(task){
        this.props.onNextState(task);
    }

    onPrevState(task){
        this.props.onPrevState(task);
    }

    onUpdate (task) {
        this.props.onUpdate(task);
    }

    allowDrop(event){
        event.preventDefault();
    }

    onDrop(event) {
        event.preventDefault();
        let taskId = parseInt(event.dataTransfer.getData("taskId"));
        this.props.onUpdateState(taskId, this.props.sectionName);
    }

    render(){
        return (
            <Jumbotron onDrop={(event)=>this.onDrop(event)} onDragOver={(event)=>this.allowDrop(event)} style={{minHeight: '82vh'}} className={`text-center px-2 pb-1 pt-0 ${ this.props.className }`}>
                {this.renderBoardHeader()}
                <div>
                    {this.renderTasks()}
                </div>
            </Jumbotron>
        );
    }
}