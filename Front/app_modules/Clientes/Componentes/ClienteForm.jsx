import React, {Component} from 'react';
import { Input, Button, Label, Form} from 'semantic-ui-react';

export default class ClienteForm extends Component{

    constructor(props){
      super(props);
      this.state = {id:0,direccion:'dir', nombre:'name', telefono:'tel'}      
      this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
      //alert('A name was submitted: ' + this.state.value);
      //event.preventDefault();
    }

    render(){
      return(
        <Form>
          <Form.Field>
            <label>Nombre:</label>
            <input placeholder='nombre completo del cliente' value={this.state.nombre} onChange={(evt)=>{this.setState({nombre:evt.target.value})}}/>
          </Form.Field>
          <Form.Field>
            <label>Dirección:</label>
            <input placeholder='dirección del cliente' />
          </Form.Field>
          <Form.Field>
            <label>Teléfono:</label>
            <input placeholder='teléfono del cliente' />
          </Form.Field>
        </Form>
      );
    }
}
