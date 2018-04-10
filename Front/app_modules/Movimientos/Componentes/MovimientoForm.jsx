import React, {Component} from 'react';
import { Input, Button, Form, Checkbox } from 'semantic-ui-react';
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
        }
      }
    }
    this.state.isLoading = false;
    this.state.tipoMovimiento = false; //false == movimiento positivc
    this.handleSumbit = this.handleSumbit.bind(this);
  }

  handleSumbit(){
    this.setState({isLoading: true});
    if (this.props.movimiento) { //actualizar si se creó con un movimiento
      this.actualizarMovimiento();
    }else{ //agregar
      this.agregarMovimiento();
    }
  }

  agregarMovimiento(){
    let {movimiento} = this.state;
    if (!this.state.tipoMovimiento) {
      movimiento.cantidad *= -1;
    }
    movimiento.usuarioCreador = JSON.parse(localStorage.getItem('logedUser'));
    movimiento.fecha = utils.toUtcDate(movimiento.fecha);
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
      utils.evalResponse(response, () => {
        this.setState({isLoading: false});
        this.props.handleClose({hasChanches: true});
      })
    })
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

  render(){
    return(
      <Form onSubmit={this.handleSumbit}>
        <Form.Field>
          <label pointing='right'>Fecha:</label>
          <input type='date' required onInput={(evt) =>{
                const fecha = evt.target.value;                
                let {movimiento} = this.state;
                movimiento.fecha = fecha;
                this.setState({movimiento});
            }}
            value={this.state.movimiento.fecha}/>
        </Form.Field>
        <Form.Field>
          <Checkbox label='Marcar como Ingreso' onChange={ (evt, data) => {
            this.setState({tipoMovimiento: data.checked});
          }}/>
        </Form.Field>
        <Form.Field>
          <label>Cantidad:</label>
          <input type="text" pattern="[0-9]*" required onInput={(evt)=>{
              if (evt.target.validity.valid) {
                let {movimiento} = this.state;
                movimiento.cantidad = evt.target.value;
                this.setState({movimiento});
              };
            }} value={this.state.movimiento.cantidad}/>
        </Form.Field>
        <Form.Field>
          <label pointing='right'>Descripción:</label>
          <input type='text' required onInput={(evt)=>{
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
