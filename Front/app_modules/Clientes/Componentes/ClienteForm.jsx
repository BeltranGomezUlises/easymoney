import React, {Component} from 'react';
import { Input, Button, Label, Form, Checkbox} from 'semantic-ui-react';

export default class ClienteForm extends Component{

    constructor(props){
      super(props);
      let cliente = {};
      let text = '';
      // dias de la semana que aplica multa empezando por lunes
      let checks = [true, true, true, true, true, true, true];
      if (props.cliente) {
        cliente = props.cliente;
        text = 'Actualizar';
        let dias = cliente.diasSinMulta.split(",");        
        dias.forEach(d => {
          checks[d] = false;
        })
      }else{
        cliente = {
          nombre:'',
          direccion:'',
          telefono:'',
          apodo:'',
          diasSinMulta:''
        }
        text = 'Agregar';
      }
      this.state = {
        cliente,
        isLoading: false,
        text,
        checks
      }

      this.updateName = this.updateName.bind(this);
      this.handleSumbit = this.handleSumbit.bind(this);
    }

    handleSumbit(){
      this.setState({isLoading: true});
      //colocar al cliente los dias concatenados por coma
      let diasSinMulta = '';
      for (var i = 0; i < this.state.checks.length; i++) {
        if (!this.state.checks[i]) {
          if (diasSinMulta === '') {
            diasSinMulta += i;
          }else{
            diasSinMulta += ',' + i;
          }
        }
      }

      let {cliente} = this.state;
      cliente.diasSinMulta = diasSinMulta;
      this.setState({cliente});

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
        return (<Button loading color='green' type='sumbit'>{this.state.text}</Button>)
      }else{
        return(
          <Button color='green' type='sumbit'>{this.state.text}</Button>
        )
      }
    }

    updateDiasSinMulta(indiceDia){
      let {checks} = this.state;
      checks[indiceDia] = !checks[indiceDia];
      this.setState({checks});
    }

    renderNombresClientes(){
      let {clientes} = this.props;
      if (clientes) {
        if (clientes.length > 0) {
          return(
           clientes.map( c => {
             return   <option value={c.nombre} />
           })
          );
        }
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
              list="nombreClientes"
              value={this.state.cliente.nombre}
              onChange={this.updateName}/>
              <datalist id='nombreClientes'>
                {this.renderNombresClientes()}
              </datalist>
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
              }}>
              </input>
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
          <label>Días que aplica multa:</label>
          <Form.Field>
            <Checkbox
            label='Lunes'
            checked={this.state.checks[1]}
            onChange={ (evt, data) => {
              this.updateDiasSinMulta(1);
            }}/>
            <label></label>
            <Checkbox
            label='Martes'
            checked={this.state.checks[2]}
            onChange={ (evt, data) => {
              this.updateDiasSinMulta(2);
            }}/>
            <label></label>
            <Checkbox
            label='Miercoles'
            checked={this.state.checks[3]}
            onChange={ (evt, data) => {
              this.updateDiasSinMulta(3);
            }}/>
            <label></label>
            <Checkbox
            label='Jueves'
            checked={this.state.checks[4]}
            onChange={ (evt, data) => {
              this.updateDiasSinMulta(4);
            }}/>
            <label></label>
            <Checkbox
            label='Viernes'
            checked={this.state.checks[5]}
            onChange={ (evt, data) => {
              this.updateDiasSinMulta(5);
            }}/>
            <label></label>
            <Checkbox
            label='Sabado'
            checked={this.state.checks[6]}
            onChange={ (evt, data) => {
              this.updateDiasSinMulta(6);
            }}/>
            <label></label>
            <Checkbox
            label='Domingo'
            checked={this.state.checks[0]}
            onChange={ (evt, data) => {
              this.updateDiasSinMulta(0);
            }}/>
          </Form.Field>
          {this.renderButton()}
        </Form>
      );
    }
}
