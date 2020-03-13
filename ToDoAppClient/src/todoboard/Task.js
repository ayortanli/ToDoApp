import React from "react";
import { Card, CardHeader, CardText, CardBody, CardLink } from 'reactstrap';
import SecureButton from "./component/SecureButton";
import SecureCardLink from "./component/SecureCardLink";

export default class Task extends React.Component {

    dragStart(event){
        event.dataTransfer.setData("taskId", this.props.data.taskId);
    }

    renderHeader(){
        return  <CardHeader className="row pb-1">
                    <div className="col-2 pl-0">
                        <SecureButton color="secondary" size="sm" role="ROLE_TASK_MODIFY"
                                onClick={() => this.props.onUpdate()}>Edit</SecureButton>
                    </div>
                    <div className="col-8">
                        <h5 className="text-center">{this.props.data.taskTitle}</h5>
                    </div>
                    <div className="col-2 pr-1">
                        {this.renderCloseButton()}
                    </div>
                </CardHeader>;
    }

    renderCloseButton(){
        if(this.props.data.taskState==="TODO")
            return <SecureButton close color="dark" size="xs" role="ROLE_TASK_MODIFY"
                           onClick={() => this.props.onDelete()}
                           className="float-right py-0 px-1"/>;
        return "";
    }

    renderBackButton(){
        if(this.props.data.taskState!=="TODO"){
            return <SecureCardLink href="#" role="ROLE_TASK_MODIFY" onClick={()=>this.props.onPrevState()} className="col-sm-6 m-0">&lt;&lt;</SecureCardLink>;
        }
        return <span className="col-sm-6"></span>;
    }

    renderForwardButton(){
        if(this.props.data.taskState!=="DONE"){
            return <SecureCardLink href="#" role="ROLE_TASK_MODIFY" onClick={()=>this.props.onNextState()} className="col-sm-6 m-0">&gt;&gt;</SecureCardLink>;
        }
        return <span className="col-sm-6"></span>;

    }

    render() {
        return (
            <Card draggable="true" onDragStart={(event)=>this.dragStart(event)} className="mb-3">
                {this.renderHeader()}
                <CardBody>
                    <CardText>{this.props.data.taskDescription}</CardText>
                    <div className="row">
                        {this.renderBackButton()}
                        {this.renderForwardButton()}
                    </div>
                </CardBody>
            </Card>
        );
    }
}