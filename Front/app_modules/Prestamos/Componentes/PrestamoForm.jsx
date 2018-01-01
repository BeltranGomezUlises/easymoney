import React, {Component} from 'react';
import { Input, Button, Label, Form, Select} from 'semantic-ui-react';

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
        }
      }
      this.cargarClientes = this.cargarClientes.bind(this);
      this.cargarCobradores = this.cargarCobradores.bind(this);
      this.updateInputCantidad = this.updateInputCantidad.bind(this);
      this.updateInputCliente = this.updateInputCliente.bind(this);
      this.updateInputCobrador = this.updateInputCobrador.bind(this);
      this.renderClientes = this.renderClientes.bind(this);
      this.renderCobradores = this.renderCobradores.bind(this);
    }

    updateInputCliente(e){
      let {nuevoPrestamo} = this.state;
      nuevoPrestamo.cliente.id = e.target.value;
      this.setState({nuevoPrestamo})
      this.props.getData({nuevoPrestamo})
    }

    updateInputCobrador(e){
      let {nuevoPrestamo} = this.state;
      nuevoPrestamo.cobrador.id = e.target.value;
      this.setState({nuevoPrestamo})
      this.props.getData({nuevoPrestamo})
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
      this.cargarCobradores()
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
          let {nuevoPrestamo} = this.state;
          nuevoPrestamo.cliente.id = response.data[0].id;
          this.setState({
            clientes:response.data,
            nuevoPrestamo
          });
          this.props.getData({nuevoPrestamo})
        })
    }

    cargarCobradores(){
        fetch(localStorage.getItem('url') + 'usuarios/usuariosCobradores',{
          method: 'GET',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          }
        }).then((res)=> res.json())
        .then((response) =>{
          let {nuevoPrestamo} = this.state;
          nuevoPrestamo.cobrador.id = response.data[0].id;
          this.setState({cobradores:response.data, nuevoPrestamo});
          this.props.getData({nuevoPrestamo})
        })
    }

    renderClientes(){
          return this.state.clientes.map((c) =>{
            return <option key={c.id} value={c.id}>{c.nombre}</option>
          })
    }

    renderCobradores(){
      return this.state.cobradores.map((c) =>{
        return <option key={c.id} value={c.id}>{c.nombre}</option>
      })
    }

    render(){
      return(
        <Form>
          <Form.Group widths='equal'>
             <Label>Cliente:</Label>
             <select value={this.state.nuevoPrestamo.cliente.id} onChange={this.updateInputCliente}>
               {this.renderClientes()}
             </select>
             <Label>Cobrador:</Label>
             <select value={this.state.nuevoPrestamo.cobrador.id} onChange={this.updateInputCobrador}>
               {this.renderCobradores()}
             </select>
          </Form.Group>
          <input type="text" pattern="[0-9]*" onInput={this.updateInputCantidad} value={this.state.nuevoPrestamo.cantidad} />
        </Form>
      );
    }
}
