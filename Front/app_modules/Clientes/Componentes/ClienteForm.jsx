import React, {Component} from 'react';
import { Input, Button, Label, Form} from 'semantic-ui-react';

export default class ClienteForm extends Component{

    constructor(props){
      super(props);
      let cliente = {};
      let text = '';
      if (props.cliente) {
        cliente = props.cliente;
        text = 'Actualizar';
      }else{
        cliente = {
          nombre:'',
          direccion:'',
          telefono:'',
          apodo:''
        }
        text = 'Agregar';
      }

      this.state = {
        cliente,
        isLoading: false,
        text
      }

      this.updateName = this.updateName.bind(this);
      this.handleSumbit = this.handleSumbit.bind(this);
    }

    handleSumbit(){
      this.setState({isLoading: true});
      if (this.state.text == 'Agregar') {
          this.props.agregarCliente();
      }else{
        this.props.editarCliente();
      }

    }

    updateName(e){
      let {cliente} = this.state;
      cliente.nombre = e.target.value;
      this.setState({cliente});
      this.props.getData(cliente);
    }

    renderButton(){
      if (this.state.isLoading) {
        <Button loading color='green' type='sumbit'>{this.state.text}</Button>
      }else{
        return(
          <Button color='green' type='sumbit'>{this.state.text}</Button>
        )
      }
    }

    render(){
      return(
        <Form onSubmit={this.handleSumbit}>
          <Form.Field>
            <label>Nombre:</label>
            <input
              required
              placeholder='Nombre del cliente'
              value={this.state.cliente.nombre}
              onChange={this.updateName}/>
          </Form.Field>
          <Form.Field>
            <label>Apodo:</label>
            <input
              placeholder='Apodo del cliente'
              value={this.state.cliente.apodo}
              onInput={ (e) => {
                let {cliente} = this.state;
                cliente.apodo = e.target.value;
                this.setState({cliente});
                this.props.getData(cliente);
              }}/>
          </Form.Field>
          <Form.Field>
            <label>Dirección:</label>
            <input
              required
              placeholder='Dirección del cliente'
              value={this.state.cliente.direccion}
              onInput={ (e) => {
                let {cliente} = this.state;
                cliente.direccion = e.target.value;
                this.setState({cliente});
                this.props.getData(cliente);
              }}/>
          </Form.Field>
          <Form.Field>
            <label>Teléfono:</label>
            <input
               required
               pattern="[0-9]*"
               placeholder='Teléfono del cliente'
               value={this.state.cliente.telefono}
               onInput={ (e) => {
                 if (e.target.value.length <= 10) {
                   const telefono = (e.target.validity.valid) ? e.target.value : this.state.cliente.telefono;
                   let {cliente} = this.state;
                   cliente.telefono = telefono;
                   this.setState({cliente});
                   this.props.getData(cliente);
                 }
              }}/>
          </Form.Field>
          {this.renderButton()}
        </Form>
      );
    }
}
