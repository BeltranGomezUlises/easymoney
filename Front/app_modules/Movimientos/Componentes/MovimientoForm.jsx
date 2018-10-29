import React, {Component} from 'react';
import { Input, Segment, Button, Form, Checkbox } from 'semantic-ui-react';
import * as utils from '../../../utils.js';
export default class MovimientoForm extends Component{

  constructor(props){
    super(props);
    if (props.movimiento) {
      this.state = {movimiento: props.movimiento};
    }else{
      this.state = {
        movimiento:{
          fecha: new Date(),
          cantidad:0,
          descripcion: ''
        },
        fecha:''
      }
    }
    this.state.isLoading = false;
    this.state.tipoMovimiento = false; //false == movimiento positivc
    this.handleSumbit = this.handleSumbit.bind(this);
    this.updateInputCantidad = this.updateInputCantidad.bind(this);
  }

  handleSumbit(){
    this.setState({isLoading: true});
    let {movimiento, fecha} = this.state;
    if (!this.state.tipoMovimiento) {
      movimiento.cantidad *= -1;
    }
    movimiento.usuarioCreador = JSON.parse(localStorage.getItem('logedUser'));
    movimiento.fecha = utils.toUtcDate(fecha);
    fetch(localStorage.getItem('url') + 'movimientos',{
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin':'*',
        'Authorization': localStorage.getItem('tokenSesion')
      },
      body:JSON.stringify(movimiento)
    }).then((res)=> res.json())
    .then((response) =>{
      this.setState({isLoading: false});
      if (response.meta.status == 'WARNING') {
        this.setState({message: response.meta.message});
      }else{
        utils.evalResponse(response, () => {
          this.props.handleClose({hasChanches: true});
        })
      }
    })
  }

  updateInputCantidad(evt){
    const cantidad = (evt.target.validity.valid) ? evt.target.value : this.state.movimiento.cantidad;
    let {movimiento} = this.state;
    movimiento.cantidad = cantidad;
    this.setState({movimiento})
  }

  renderButton(){
    if (this.state.isLoading) {
      return(
        <Button color='green' loading>Agregar</Button>
      );
    }else{
      return(
        <Button color='green' type='sumbit'>Agregar</Button>
      );
    }
  }

  renderMessage(){
    if (this.state.message != null & this.state.message != '') {
      return (
        <Segment color='yellow'>
          <p>{this.state.message}</p>
        </Segment>
      )
    }
  }

  render(){
    return(
      <Form onSubmit={this.handleSumbit}>
        {this.renderMessage()}
        <Form.Field>
          <label pointing='right'>Fecha:</label>
          <input type='date' required onChange={(e) =>{
                let {fecha} = this.state;
                fecha = e.target.value;
                this.setState({fecha});
            }}
            value={this.state.fecha}/>
        </Form.Field>
        <Form.Field>
          <Checkbox label='Marcar como Ingreso' onChange={ (evt, data) => {
            this.setState({tipoMovimiento: data.checked});
          }}/>
        </Form.Field>
        <Form.Field>
          <label>Cantidad:</label>
          <input required type="text" pattern="[0-9]*"
          onChange={this.updateInputCantidad}
          value={this.state.movimiento.cantidad} />
        </Form.Field>
        <Form.Field>
          <label pointing='right'>Descripción:</label>
          <input type='text' required onChange={(evt)=>{
              let {movimiento} = this.state;
              movimiento.descripcion = evt.target.value;
              this.setState({movimiento});
            }}
            value={this.state.movimiento.descripcion}/>
        </Form.Field>
        {this.renderButton()}
      </Form>
    )
  }

}
