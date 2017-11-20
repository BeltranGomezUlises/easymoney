import React, {Component} from 'react';
import { Input, Button, Label, Form} from 'semantic-ui-react';

export default class ClienteForm extends Component{

    constructor(props){
      super(props);
      if (props.cliente) {
        this.state = {
          cliente: props.cliente
        }
      }else{
        this.state={
          cliente:{
            nombre:'',
            direccion:'',
            telefono:''
          }
        }
      }
      this.updateName = this.updateName.bind(this);
    }

    updateName(e){
      let {cliente} = this.state;
      cliente.nombre = e.target.value;
      this.setState({cliente});
      this.props.getData(cliente);
    }

    renderInputName(){
      if(this.state.cliente.nombre !== ''){
        return(
          <Input value={this.state.cliente.nombre} onChange={this.updateName}/>
        )
      }else{
        return(
          <Input error placeholder='nombre de cliente requerido...' onChange={this.updateName}/>
        )
      }
    }

    render(){
      return(
        <Form>
          <Form.Field>
            <label>Nombre:</label>
            {this.renderInputName()}
          </Form.Field>
          <Form.Field>
            <label>Dirección:</label>
            <Input placeholder='dirección del cliente' value={this.state.cliente.direccion} onChange={ (e) => {
                let {cliente} = this.state;
                cliente.direccion = e.target.value;
                this.setState({cliente});
                this.props.getData(cliente);
              }}/>
          </Form.Field>
          <Form.Field>
            <label>Teléfono:</label>
            <Input placeholder='teléfono del cliente' value={this.state.cliente.telefono} onChange={ (e) => {
                let {cliente} = this.state;
                cliente.telefono = e.target.value;
                this.setState({cliente});
                this.props.getData(cliente);
              }}/>
          </Form.Field>
        </Form>
      );
    }
}
