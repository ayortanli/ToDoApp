import React from "react";
import TaskSection from "./TaskSection";
import TaskController from "./TaskController"
import { Jumbotron, Container } from "reactstrap";
import TaskInsert from "./TaskInsert";

export default class TodoBoard extends React.Component {

    constructor(props) {
        super(props);
        this.taskController = new TaskController();
        this.state = {
            todoList: [],
            inProgressList: [],
            doneList: [],
            isInserting: false
        };
    }

    onInsertClicked(){
        this.setState({
            isInserting: true
        });
    }

    onInsertCanceled(){
        this.updatedTask = null;
        this.setState({
            isInserting: false
        })
    }

    onInsertCommitted(task){
        let todoItems = this.state.todoList;
        todoItems.splice(todoItems.length,0,task);
        this.setState({
            todoList: todoItems
        });
    }

    onUpdateCommitted(task, updatedTask){
        this.updateTaskOnBoard(task, updatedTask, false);
    }

    onDelete(taskId){
        let result = confirm("Are your sure?");
        if(result)
            this.taskController.deleteTask(taskId, (resultData)=>this.onDeleteSuccess(taskId, resultData));
    }

    onArchive(){
        let result = confirm("Are your sure you want to archive all completed tasks?");
        if(result)
            this.taskController.archiveCompletedTasks(this.state.doneList, (resultData)=>this.onArchiveSuccess(resultData));
    }

    onNextState(task){
        this.onUpdateState(task, 'DONE');
    }

    onPrevState(task){
        this.onUpdateState(task, 'TODO');
    }

    onUpdateClicked(task) {
        this.updatedTask = task;
        this.setState({
            isInserting: true
        });
    }

    onUpdateState(task, finalState){
        let taskState = 'IN_PROGRESS';
        if(task.taskState===taskState){
            taskState = finalState;
        }
        this.taskController.updateTaskState(task.taskId, taskState, (resultData)=>this.onUpdateSuccess(task, resultData));
    }

    onDeleteSuccess(taskId, resultData){
        let todoList = this.state.todoList;
        todoList.splice(this.getItemIndexInList(taskId,todoList),1);
        this.setState({
                todoList:todoList
            });
    }

    onUpdateSuccess(task, updatedTask){
        this.updateTaskOnBoard(task,updatedTask, true);
    }

    updateTaskOnBoard(task, updatedTask, isMoveToTop){
        let todoItems = this.state.todoList;
        let inProgressItems = this.state.inProgressList;
        let doneItems = this.state.doneList;
        let index = -1;
        if(task.taskState==='TODO'){
            index = this.getItemIndexInList(task.taskId,todoItems)
            todoItems.splice(index,1);
            this.setState({
                todoList: todoItems
            });
        } else if(task.taskState==='IN_PROGRESS'){
            index = this.getItemIndexInList(task.taskId,inProgressItems)
            inProgressItems.splice(index,1);
            this.setState({
                inProgressList: inProgressItems
            });
        } else if(task.taskState==='DONE'){
            index = this.getItemIndexInList(task.taskId,doneItems)
            doneItems.splice(index,1);
            this.setState({
                doneList: doneItems
            });
        }
        if(isMoveToTop)
            index = 0;
        if(updatedTask.taskState==='TODO'){
            todoItems.splice(index,0,updatedTask);
            this.setState({
                todoList: todoItems
            });
        } else if(updatedTask.taskState==='IN_PROGRESS'){
            inProgressItems.splice(index,0,updatedTask);
            this.setState({
                inProgressList: inProgressItems
            });
        } else if(updatedTask.taskState==='DONE'){
            doneItems.splice(index,0,updatedTask);
            this.setState({
                doneList: doneItems
            });
        }
    }

    onArchiveSuccess(resultData){
        this.setState({
            todoList:this.state.todoList,
            inProgressList: this.state.inProgressList,
            doneList: []
        });
    }

    getItemIndexInList(taskId, list){
        for (let i = 0; i < list.length; i++) {
            if(list[i].taskId === taskId) {
                return i;
            }
        }
        return -1;
    }

    onRetrieveTasksSuccess(resultData){
        let todoItems = [];
        let inProgressItems = [];
        let doneItems = [];
        for(let i = 0; i < resultData.length; ++i){
            if(resultData[i].taskState === 'TODO')
                todoItems.push(resultData[i]);
            else if(resultData[i].taskState === 'IN_PROGRESS')
                inProgressItems.push(resultData[i]);
            else if(resultData[i].taskState === 'DONE')
                doneItems.push(resultData[i]);
        }
        this.setState({
            todoList: todoItems,
            inProgressList: inProgressItems,
            doneList: doneItems
        });
    }

    componentDidMount() {
        this.taskController.retrieveAllTasks((resultData)=>this.onRetrieveTasksSuccess(resultData));
    }

    render(){
        return (
            <span>
                <TaskInsert isOpen={this.state.isInserting} task={this.updatedTask}
                            onInsertCommitted={(task)=>this.onInsertCommitted(task)}
                            onUpdateCommitted={(task, updatedTask)=>this.onUpdateCommitted(task, updatedTask)}
                            onInsertCanceled={()=>this.onInsertCanceled()}/>
                <Jumbotron className="bg-dark text-center p-2">
                    <span className="align-top">
                        <h1 className="text-light">Task List</h1>
                    </span>
                    <Container>
                        <div style={{minHeight: '85vh'}} className="row">
                            <div className="col-sm">
                                <TaskSection onDelete={(taskId)=>this.onDelete(taskId)}
                                             onInsert={()=>this.onInsertClicked()}
                                             onUpdate={(task)=>this.onUpdateClicked(task)}
                                             onNextState={(task)=>this.onNextState(task)}
                                             taskList={this.state.todoList} sectionName="To Do" className="bg-info"/>
                            </div>
                            <div className="col-sm">
                                <TaskSection onUpdate={(task)=>this.onUpdateClicked(task)}
                                             onNextState={(task)=>this.onNextState(task)}
                                             onPrevState={(task)=>this.onPrevState(task)}
                                             taskList={this.state.inProgressList} sectionName="In Progress" className="bg-warning"/>
                            </div>
                            <div className="col-sm">
                                <TaskSection onUpdate={(task)=>this.onUpdateClicked(task)}
                                             onPrevState={(task)=>this.onPrevState(task)}
                                             onArchive={()=>this.onArchive()}
                                             taskList={this.state.doneList} sectionName="Done" className="bg-primary"/>
                            </div>
                        </div>
                    </Container>
                </Jumbotron>
            </span>
        );
    }
}