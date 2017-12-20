import React, {Component} from 'react';
import { Input, Button, Label, Form} from 'semantic-ui-react';

export default class CobradorForm extends Component{

    constructor(props){
      super(props);

      let cobrador = {};
      let text = '';

      if (props.cobrador) {
          cobrador = props.cobrador;
          text = 'Actualizar';
      }else{
        cobrador = {
            nombre:'',
            direccion:''
        }
        text = 'Agregar';
      }
      this.state = {
        cobrador,
        text,
        isLoading: false
      }
      this.updateName = this.updateName.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }

    updateName(e){
      let {cobrador} = this.state;
      cobrador.nombre = e.target.value;
      this.setState({cobrador});
      this.props.getData(cobrador);
    }

    handleSubmit(){
      if (this.state.text == 'Agregar') {
          this.props.agregarCobrador();
      }else{
        this.props.updateCobrador();
      }
    }

    renderButton(){
      if (this.state.isLoading) {
        return (
          <Button color='green' type='sumbit'>{this.state.text}</Button>
        );
      }else{
        return(
          <Button color='green' type='sumbit'>{this.state.text}</Button>
        );
      }
    }

    render(){
      return(
        <Form onSubmit={this.handleSubmit}>
          <Form.Field>
            <label>Nombre:</label>
            <input
              required
              placeholder='Nombre del cobrador'
              value={this.state.cobrador.nombre}
              onChange={this.updateName}/>
          </Form.Field>
          <Form.Field>
            <label>Dirección:</label>
            <input
                placeholder='Dirección del cobrador'
                value={this.state.cobrador.direccion} onChange={ (e) => {
                let {cobrador} = this.state;
                cobrador.direccion = e.target.value;
                this.setState({cobrador});
                this.props.getData(cobrador);
              }}/>
          </Form.Field>
          {this.renderButton()}
        </Form>
      );
    }
}
