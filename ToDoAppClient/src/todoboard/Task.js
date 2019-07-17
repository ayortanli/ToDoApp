import React from "react";

export default class Task extends React.Component {

    render() {
        return (
            <div className="card mb-3">
                <h5 className="card-header">{this.props.data.taskTitle}</h5>
                <div className="card-body">
                    <p className="card-text">{this.props.data.taskDescription}</p>
                    <a href="#" className="card-link">&lt;&lt;</a>
                    <a href="#" className="card-link">&gt;&gt;</a>
                </div>
            </div>
        );
    }
}