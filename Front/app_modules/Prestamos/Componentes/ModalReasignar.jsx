import React, {Component} from 'react';
import {Button, Modal, Form, Segment, Dimmer, Loader, Dropdown, Message} from 'semantic-ui-react';
import * as utils from '../../../utils.js';

export default class ModalReasignar extends Component{

  constructor(props){
    super(props);
    this.state = {
      open : false,
      nuevoCobrador: null,
      loading:false,
      optionsUsuarios:[],
      nuevoCobrador: null,
      message: null
    }
    this.onOpen = this.onOpen.bind(this);
    this.onClose = this.onClose.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleUpdateUsuarios = this.handleUpdateUsuarios.bind(this);
  }

  componentDidMount(){
    this.cargarUsuarios();
  }

  handleUpdateUsuarios(e, {value}){
    let {nuevoCobrador} = this.state;
    nuevoCobrador = value;
    this.setState({nuevoCobrador});
  }

  handleSubmit(){
    var body = {
      prestamoId:this.props.prestamo.id,
      cobradorId:this.state.nuevoCobrador
    }
    if (body.cobradorId != null) {
      this.setState({loading:true})
      fetch(localStorage.getItem('url') + 'prestamos/cambiarCobrador',{
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        },
        body:JSON.stringify(body)
      }).then((res)=> res.json())
      .then((response) =>{
        this.setState({loading:false});
        utils.evalResponse(response, () => {
            this.onClose();
            this.props.update();            
        });
      });
    }else{
      this.setState({message:'Seleccione cobrador'})
    }
  }

  onOpen(){
    this.setState({open:true});
  }

  onClose(){
    this.setState({open:false});
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

  renderMessage(){
    if (this.state.message != null) {
      return (
        <Message>
          <Message.Header>Atención</Message.Header>
          <p>{this.state.message}</p>
        </Message>
      )
    }
  }

  renderButton(){
    if (this.state.loading) {
        return(
           <Button primary content='Reasignar' loading />
        );
      }else{
        return(
          <Button primary content='Reasignar' type='submit' />
        );
      }
  }

  render(){
    return(
      <Modal
        trigger={<Button primary>Reasignar</Button>}
        onOpen={this.onOpen}
        onClose={this.onClose}
        open={this.state.open}>
        <Modal.Header>Reasignar a otro cobrador</Modal.Header>
        <Modal.Content>
          <Form onSubmit={this.handleSubmit}>
            {this.renderMessage()}
            <Form.Field required>
              <label>Cobrador:</label>
              <Dropdown
                search
                required
                options={this.state.optionsUsuarios}
                selection
                placeholder='Cobrador'
                onChange={this.handleUpdateUsuarios}
                />
            </Form.Field>
            {this.renderButton()}
          </Form>
        </Modal.Content>
      </Modal>
    )
  }
}
