import React from "react";
import TaskController from "./remote/TaskController";
import {
    Button,
    Modal,
    ModalHeader,
    ModalBody,
    ModalFooter,
    FormGroup,
    Label,
    Input
} from 'reactstrap';

export default class TaskInsert extends React.Component {

    constructor(props){
        super(props);
        this.taskController = new TaskController();
        this.state= {
            taskTitle: "",
            taskDescription: ""
        }
    }

    isUpdateState(){
        if(this.props.task!==undefined && this.props.task!==null)
            return true;
        return false;
    }

    componentDidUpdate(prevProps) {
        if(this.props.task !== prevProps.task){
            let taskTitle = "";
            let taskDescription = "";
            if(this.isUpdateState()) {
                taskTitle = this.props.task.taskTitle;
                taskDescription =  this.props.task.taskDescription;
            }
            this.setState({
                taskTitle: taskTitle,
                taskDescription: taskDescription
            });
        }
    }

    toggle() {
        this.props.onInsertCanceled();
    }

    insertUpdateTask() {
        if(this.isUpdateState())
            this.taskController.updateTask(this.props.task.taskId, this.state.taskTitle, this.state.taskDescription,
                (resultData)=>this.onUpdateSuccess(this.props.task, resultData));
        else
            this.taskController.createTask(this.state.taskTitle, this.state.taskDescription,
                (resultData)=>this.onCreationSuccess(resultData));
    }

    onCreationSuccess(resultData){
        this.props.onInsertCommitted(resultData);
        this.toggle();
    }

    onUpdateSuccess(task, resultData){
        this.props.onUpdateCommitted(task, resultData);
        this.toggle();
    }

    setTitle(title){
        this.setState({
            taskTitle:title
        });
    }

    setDescription(title){
        this.setState({
            taskDescription:title
        });
    }

    getWindowTitle(){
        if(this.props.task===undefined || this.props.task===null)
            return "Add New Task...";
        return "Update Task...";
    }

    render() {
        return (
            <Modal isOpen={this.props.isOpen} toggle={()=>this.toggle()} backdrop={false}>
                <ModalHeader toggle={()=>this.toggle()}>{this.getWindowTitle()}</ModalHeader>
                <ModalBody>
                    <FormGroup>
                        <Label htmlFor="taskTitle">Task Title</Label>
                        <Input type="text" id="taskTitle" value={this.state.taskTitle}
                               onChange={e=>this.setTitle(e.target.value)}
                               placeholder="Example Task"/>
                    </FormGroup>
                    <FormGroup>
                        <Label htmlFor="taskDescription">Task Description</Label>
                        <Input type="textarea" id="taskDescription" value={this.state.taskDescription}
                               onChange={e=>this.setDescription(e.target.value)}
                               placeholder="Write task detail here..."/>
                    </FormGroup>
                </ModalBody>
                <ModalFooter>
                    <Button color="primary" onClick={()=>this.insertUpdateTask()}>Save</Button>{' '}
                    <Button color="secondary" onClick={()=>this.toggle()}>Cancel</Button>
                </ModalFooter>
            </Modal>
        );
    }
}