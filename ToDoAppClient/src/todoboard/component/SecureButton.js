import React from "react";
import { Button } from 'reactstrap';
import {UserContext} from "../UserContext";

export default class SecureButton extends React.Component {

    constructor(props) {
        super(props);
    }

    render(){
        if(!this.props.role || UserContext.roles.includes(this.props.role))
            return (<Button {...this.props}></Button>);
        return "";
    }

}
