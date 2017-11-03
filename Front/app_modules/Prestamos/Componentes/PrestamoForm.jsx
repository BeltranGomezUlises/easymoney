import React, {Component} from 'react';
import { Input, Button, Label, Form, Select} from 'semantic-ui-react';

export default class PrestamoForm extends Component{

    constructor(props){
      super(props);
      this.state={
        clientes: [],
        cobradores: [],
        cantidad: 0,
        prestamo:{
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
    }


    updateInputCliente(e){
      console.log(e.target.name);
    }

    updateInputCobrador(e){
      console.log(e.target.valueKey);
    }

    updateInputCantidad(evt){
      const cantidad = (evt.target.validity.valid) ? evt.target.value : this.state.cantidad;
      this.setState({cantidad})
    }

    componentWillMount(){
      this.cargarClientes();
      this.cargarCobradores()
    }

    cargarClientes(){
      fetch(localStorage.getItem('url') + 'accesos/login', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          user: 'admin',
          pass: '1234',
        })
      }).then((res) => res.json())
      .then((response) => localStorage.setItem('tokenSesion', response.meta.metaData))
      .then(()=>{
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
          let clientes = response.data.map((c) =>{
            return { key: c.id, text: c.nombre, value: c.id }
          });

          this.setState({clientes});
        })
       })
    }

    cargarCobradores(){
      fetch(localStorage.getItem('url') + 'accesos/login', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          user: 'admin',
          pass: '1234',
        })
      }).then((res) => res.json())
      .then((response) => localStorage.setItem('tokenSesion', response.meta.metaData))
      .then(()=>{
        fetch(localStorage.getItem('url') + 'cobradores',{
          method: 'GET',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          }
        }).then((res)=> res.json())
        .then((response) =>{
          let cobradores = response.data.map((c) =>{
            return {key: c.id, text: c.nombre, value: c.id}
          })
          this.setState({cobradores});
        })
       })
    }

    render(){
      return(
        <Form>
          <Form.Group widths='equal'>
             <Form.Dropdown control={Select} label='Cliente:' options={this.state.clientes} placeholder='cliente...' onChange={this.updateInputCliente}/>
             <Form.Dropdown control={Select} label='Cobrador:' options={this.state.cobradores} placeholder='cobrador...' onChange={this.updateInputCobrador} />
          </Form.Group>
          <input type="text" pattern="[0-9]*" onInput={this.updateInputCantidad} value={this.state.cantidad} />
        </Form>
      );
    }
}
