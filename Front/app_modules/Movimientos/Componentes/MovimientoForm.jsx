import React, {Component} from 'react';
import { Input, Button, Form} from 'semantic-ui-react';

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
    this.handleSumbit = this.handleSumbit.bind(this);
  }

  handleSumbit(){
    this.props.agregarMovimiento();
    console.log('sumbit')
  }

  render(){
    return(
      <Form onSubmit={this.handleSumbit}>
        <Form.Field>
          <label pointing='right'>Fecha:</label>
          <input type='date'
            onInput={(evt) =>{
                const fecha = evt.target.value;
                let {movimiento} = this.state;
                movimiento.fecha = fecha;
                this.setState({movimiento});
                this.props.getData({movimiento});
            }}
            value={this.state.movimiento.fecha}/>
        </Form.Field>
        <Form.Field>
          <label>Cantidad:</label>
            <input type="text" pattern="[0-9]*" required onInput={(evt)=>{
              if (evt.target.validity.valid) {
                let {movimiento} = this.state;
                movimiento.cantidad = evt.target.value;
                this.setState({movimiento});
                this.props.getData({movimiento});
              };
            }} value={this.state.movimiento.cantidad}/>
        </Form.Field>
        <Form.Field>
          <label pointing='right'>Descripción:</label>
          <input type='text' required onInput={(evt)=>{
              let {movimiento} = this.state;
              movimiento.descripcion = evt.target.value;
              this.setState({movimiento});
              this.props.getData({movimiento});
            }}
            value={this.state.movimiento.descripcion}/>
        </Form.Field>
        <Button color='green' type='sumbit'>Agregar</Button>
      </Form>
    )
  }
}
