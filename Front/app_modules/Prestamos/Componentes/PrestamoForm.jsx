import React, {Component} from 'react';
import { Input, Button, Label, Form, Select, Dropdown, Segment} from 'semantic-ui-react';
import * as utils from '../../../utils.js';

export default class PrestamoForm extends Component{

    constructor(props){
      super(props);
      this.state={
        clientes: [],
        cobradores: [],
        nuevoPrestamo:{
          cantidad:0,
          cliente:{
            id:0
          },
          cobrador:{
            id:0
          }
        },
        optionsClientes:[],
        optionsUsuarios:[],
        loading:false,
        warning:false
      }
      this.updateInputCantidad = this.updateInputCantidad.bind(this);
      this.handleUpdateCliente = this.handleUpdateCliente.bind(this);
      this.handleUpdateUsuarios = this.handleUpdateUsuarios.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleUpdateCliente(e, {value}){
      let {nuevoPrestamo} = this.state;
      nuevoPrestamo.cliente.id = value;
      this.setState(nuevoPrestamo);
    }

    handleUpdateUsuarios(e, {value}){
      let {nuevoPrestamo} = this.state;
      nuevoPrestamo.cobrador.id = value;
      this.setState(nuevoPrestamo);
    }

    handleSubmit(){
      const np = this.state.nuevoPrestamo;
      console.log(np)
      if (np.cantidad == 0 || np.cliente.id == 0 || np.cobrador.id == 0) {
        this.setState({warning:true});
      }else{
        this.setState({loading:true, warning:false});
        fetch(localStorage.getItem('url') + 'prestamos',{
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body:JSON.stringify(this.state.nuevoPrestamo)
        }).then((res)=> res.json())
        .then((response) =>{
          this.setState({loading:false});
          this.props.close();
        })
      }
    }

    updateInputCantidad(evt){
      const cantidad = (evt.target.validity.valid) ? evt.target.value : this.state.nuevoPrestamo.cantidad;
      let {nuevoPrestamo} = this.state;
      nuevoPrestamo.cantidad = cantidad;
      this.setState({nuevoPrestamo})
      this.props.getData({nuevoPrestamo})
    }

    componentWillMount(){
      this.cargarClientes();
      this.cargarUsuarios()
    }

    cargarClientes(){
        fetch(localStorage.getItem('url') + 'clientes',{
          method: 'GET',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          }
        }).then((res)=> res.json())
        .then((response) =>{
          utils.evalResponse(response, () => {
            let optionsClientes = [];
            response.data.forEach((c)=>{
              optionsClientes.push({ key: c.id, text: c.nombre, value: c.id});
            })
            this.setState({optionsClientes})
          });
        })
    }

    cargarUsuarios(){
        fetch(localStorage.getItem('url') + 'usuarios',{
          method: 'GET',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          }
        }).then((res)=> res.json())
        .then((response) =>{
          utils.evalResponse(response, () => {
            let optionsUsuarios = [];
            response.data.forEach((c)=>{
              optionsUsuarios.push({ key: c.id, text: c.nombre, value: c.id});
            })
            this.setState({optionsUsuarios})
          });
        })
    }

    renderButton(){
      if (this.state.loading) {
        return(
          <Button color='green' loading>Agregar</Button>
        );
      }else{
        return(
          <Button color='green' type='submit'>Agregar</Button>
        );
      }
    }

    renderWarning(){
      if (this.state.warning){
        return(
          <Segment color='yellow'>
            <p>Verifique que tiene todos los campos llenos</p>
          </Segment>
        );
      }
    }

    render(){
      return(
        <div>
          {this.renderWarning()}
          <Form onSubmit={this.handleSubmit}>
            <Form.Group widths='equal'>
              <Form.Field required>
                <label>Cliente:</label>
                <Dropdown
                  search
                  required
                  options={this.state.optionsClientes}
                  selection
                  placeholder='Cliente'
                  onChange={this.handleUpdateCliente}
                />
              </Form.Field>
              <Form.Field required>
              <label>Usuario:</label>
              <Dropdown
                search
                required
                options={this.state.optionsUsuarios}
                selection
                placeholder='Usuario'
                onChange={this.handleUpdateUsuarios}
                />
              </Form.Field>
            </Form.Group>
            <Form.Field required>
              <label>Cantidad a prestar:</label>
              <input type='number' min='0' step='1' max='99999'
                required
                placeholder='Cantidad a prestar en el nuevo prestamo'
                value={this.state.nuevoPrestamo.cantidad}
                onInput={this.updateInputCantidad} />
            </Form.Field>
            {this.renderButton()}
          </Form>
        </div>
      );
    }
}
