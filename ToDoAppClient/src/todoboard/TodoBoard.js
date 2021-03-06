import { hot } from 'react-hot-loader/root';
import React from "react";
import TaskSection from "./TaskSection";
import TaskRemoteController from "./remote/TaskRemoteController"
import UserRemoteController from "./remote/UserRemoteController";
import { Jumbotron, Container, Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from "reactstrap";
import TaskInsert from "./TaskInsert";
import {UserContext} from "./UserContext";

const TodoBoardHot = class TodoBoard extends React.Component {

    constructor(props) {
        super(props);
        this.taskRemoteController = new TaskRemoteController();
        this.userRemoteController = new UserRemoteController();
        this.state = {
            username: "",
            userMenuOpened: false,
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


    onUpdateClicked(task) {
        this.updatedTask = task;
        this.setState({
            isInserting: true
        });
    }

    onUpdateCommitted(task, updatedTask){
        this.updateTaskOnBoard(task, updatedTask, false);
    }

    onDelete(taskId){
        let result = confirm("Are your sure?");
        if(result)
            this.taskRemoteController.deleteTask(taskId, (resultData)=>this.onDeleteSuccess(taskId, resultData));
    }

    onArchive(){
        let result = confirm("Are your sure you want to archive all completed tasks?");
        if(result)
            this.taskRemoteController.archiveCompletedTasks(this.state.doneList, (resultData)=>this.onArchiveSuccess(resultData));
    }

    onUpdateStateByDrag(taskId, newState){
        let task = this.getTaskOnBoard(taskId);
        if(task.taskState !== newState)
            this.onUpdateState(task, newState);
    }

    onUpdateState(task, newState){
        this.taskRemoteController.updateTaskState(task.taskId, newState, (resultData)=>this.onUpdateSuccess(task, resultData));
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

    getTaskOnBoard(taskId){
        let itemIndex = this.getItemIndexInList(taskId,this.state.todoList);
        if(itemIndex!==-1)
            return this.state.todoList[itemIndex];
        itemIndex = this.getItemIndexInList(taskId,this.state.inProgressList);
        if(itemIndex!==-1)
            return this.state.inProgressList[itemIndex];
        itemIndex = this.getItemIndexInList(taskId,this.state.doneList);
        if(itemIndex!==-1)
            return this.state.doneList[itemIndex];
        return null;
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

    onUserRetrieved(user){
        UserContext.username = user.username;
        UserContext.roles = user.roles;
        this.setState({
            username: user.username
        });
    }

    onUserMenuToggled(){
        let curr = this.state.userMenuOpened;
        this.setState({
            userMenuOpened:!curr
        });
    }

    logout(){
        this.userRemoteController.logout();
    }

    componentDidMount() {
        this.userRemoteController.getUser((resultData)=>this.onUserRetrieved(resultData));
        this.taskRemoteController.retrieveAllTasks((resultData)=>this.onRetrieveTasksSuccess(resultData));
    }

    render(){
        return (
            <span>
                <TaskInsert isOpen={this.state.isInserting} task={this.updatedTask}
                            onInsertCommitted={(task)=>this.onInsertCommitted(task)}
                            onUpdateCommitted={(task, updatedTask)=>this.onUpdateCommitted(task, updatedTask)}
                            onInsertCanceled={()=>this.onInsertCanceled()}/>
                <Jumbotron className="bg-dark text-center p-2">
                    <div className="align-top row">
                        <div className="col-sm"/>
                        <div className="col-sm">
                            <h1 className="text-light">Task List</h1>
                        </div>
                        <div className="col-sm">
                            <Dropdown className="float-right mr-3" isOpen={this.state.userMenuOpened} toggle={()=>this.onUserMenuToggled()}>
                                <DropdownToggle caret className="py-0">
                                    <span className="text-light">{this.state.username}&nbsp;&nbsp;</span>
                                </DropdownToggle>
                                <DropdownMenu className="p-0">
                                    <DropdownItem onClick={()=> this.logout()}>Logout</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                        </div>
                    </div>
                    <Container>
                        <div style={{minHeight: '85vh'}} className="row">
                            <div className="col-sm">
                                <TaskSection onDelete={(taskId)=>this.onDelete(taskId)}
                                             onInsert={()=>this.onInsertClicked()}
                                             onUpdate={(task)=>this.onUpdateClicked(task)}
                                             onUpdateState={(taskId, newState)=>this.onUpdateStateByDrag(taskId, newState)}
                                             onNextState={(task)=>this.onUpdateState(task, 'IN_PROGRESS')}
                                             taskList={this.state.todoList} sectionName="To Do" sectionId="TODO" className="bg-info"/>
                            </div>
                            <div className="col-sm">
                                <TaskSection onUpdate={(task)=>this.onUpdateClicked(task)}
                                             onUpdateState={(taskId, newState)=>this.onUpdateStateByDrag(taskId, newState)}
                                             onNextState={(task)=>this.onUpdateState(task, 'DONE')}
                                             onPrevState={(task)=>this.onUpdateState(task, 'TODO')}
                                             taskList={this.state.inProgressList} sectionName="In Progress" sectionId="IN_PROGRESS"  className="bg-warning"/>
                            </div>
                            <div className="col-sm">
                                <TaskSection onUpdate={(task)=>this.onUpdateClicked(task)}
                                             onUpdateState={(taskId, newState)=>this.onUpdateStateByDrag(taskId, newState)}
                                             onPrevState={(task)=>this.onUpdateState(task, 'IN_PROGRESS')}
                                             onArchive={()=>this.onArchive()}
                                             taskList={this.state.doneList} sectionName="Done" sectionId="DONE" className="bg-primary"/>
                            </div>
                        </div>
                    </Container>
                </Jumbotron>
            </span>
        );
    }
}

export default hot(TodoBoardHot);