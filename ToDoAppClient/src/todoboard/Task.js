import React from "react";
import { Card, CardHeader, CardText, CardBody, CardLink, Button } from 'reactstrap';

export default class Task extends React.Component {

    dragStart(event){
        event.dataTransfer.setData("taskId", this.props.data.taskId);
    }

    renderHeader(){
        return  <CardHeader className="row pb-1">
                    <div className="col-2 pl-0">
                        <Button color="secondary" size="sm"
                                onClick={() => this.props.onUpdate()}>Edit</Button>
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
            return <Button close color="dark" size="xs"
                           onClick={() => this.props.onDelete()}
                           className="float-right py-0 px-1"/>;
        return "";
    }

    renderBackButton(){
        if(this.props.data.taskState!=="TODO"){
            return <CardLink href="#" onClick={()=>this.props.onPrevState()} className="col-sm-6 m-0">&lt;&lt;</CardLink>;
        }
        return <span className="col-sm-6"></span>;
    }

    renderForwardButton(){
        if(this.props.data.taskState!=="DONE"){
            return <CardLink href="#" onClick={()=>this.props.onNextState()} className="col-sm-6 m-0">&gt;&gt;</CardLink>;
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