import React, {Component} from 'react';
import { Input, Button, Label, Form} from 'semantic-ui-react';

export default class CobradorForm extends Component{

    constructor(props){
      super(props);
      if (props.cobrador) {
        this.state = {
          cobrador: props.cobrador
        }
      }else{
        this.state={
          cobrador:{
            nombre:'',
            direccion:''
          }
        }
      }
      this.updateName = this.updateName.bind(this);
    }

    updateName(e){
      let {cobrador} = this.state;
      cobrador.nombre = e.target.value;
      this.setState({cobrador});
      this.props.getData(cobrador);
    }

    renderInputName(){
      if(this.state.cobrador.nombre !== ''){
        return(
          <Input value={this.state.cobrador.nombre} onChange={this.updateName}/>
        )
      }else{
        return(
          <Input error placeholder='nombre de cobrador requerido...' onChange={this.updateName}/>
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
            <Input placeholder='Dirección del cobrador' value={this.state.cobrador.direccion} onChange={ (e) => {
                let {cobrador} = this.state;
                cobrador.direccion = e.target.value;
                this.setState({cobrador});
                this.props.getData(cobrador);
              }}/>
          </Form.Field>
        </Form>
      );
    }
}
