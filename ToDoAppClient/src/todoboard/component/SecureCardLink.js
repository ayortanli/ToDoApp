import React from "react";
import { CardLink } from 'reactstrap';
import {UserContext} from "../UserContext";

export default class SecureCardLink extends React.Component {

    constructor(props) {
        super(props);
    }

    render(){
        if(!this.props.role || UserContext.roles.includes(this.props.role))
            return (<CardLink {...this.props}></CardLink>);
        return "";
    }

}