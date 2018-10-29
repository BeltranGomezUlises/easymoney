import React, {Component} from 'react';
import {Button, Modal, Form, Header, Message,Table,  Icon, Segment, Dimmer, Loader} from 'semantic-ui-react';
import * as utils from '../../../utils.js';

export default class PrestamoDetalle extends Component{

  constructor(props){
    super(props);
    this.state = {
      open : false,
      cantNuevoPrestamo : props.prestamo.cantidad,
      cantidadEntregar : 0,
      loading:false,
      renovado:false,
      message: ''
    }

    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(){
    this.setState({loading:true})
    fetch(localStorage.getItem('url') + 'prestamos/renovar/'
    + this.props.prestamo.id + '/'
    + this.state.cantNuevoPrestamo,{
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin':'*',
        'Authorization': localStorage.getItem('tokenSesion')
      }
    }).then((res)=> res.json())
    .then((response) =>{
      this.setState();
      if (response.meta.status == 'WARNING') {
        this.setState({
          message: response.meta.message,
          loading:false
        });
      }else{
        this.setState({
          cantidadEntregar: response.data,
          message: '',
          renovado:true,
          loading:false
        });
        this.props.update();
      }
    });
  }

  renderCantidadEntregar(){
    if (this.state.renovado) {
      return(
        <Segment>
          <h2>Cantidad a entregar: ${this.state.cantidadEntregar}</h2>
        </Segment>
      );
    }
  }

  renderButton(){
    if (!this.state.renovado) {
        return(
           <Button primary content='Renovar'
           type={this.state.loading ? 'button':'submit' }
           loading={this.state.loading}
           />
        );
      }
  }

  renderTotales(){
    let {totales, prestamo} = this.props;
    if (totales !== null) {
      var paraSaldar = prestamo.cantidadAPagar - totales.totalAbonado;
      var entregarAlRenovar = this.state.cantNuevoPrestamo - paraSaldar;
      return(
        <Table.Body>
          <Table.Cell textAlign='center'>${totales.totalAbonado}</Table.Cell>
          <Table.Cell textAlign='center'>${totales.totalMultado}</Table.Cell>
          <Table.Cell textAlign='center'>${totales.totalRecuperado}</Table.Cell>
          <Table.Cell textAlign='center'>{totales.porcentajePagado}%</Table.Cell>
          <Table.Cell textAlign='center'>${prestamo.cantidadAPagar}</Table.Cell>
          <Table.Cell textAlign='center'>${paraSaldar}</Table.Cell>
          <Table.Cell textAlign='center'><b>${entregarAlRenovar}</b></Table.Cell>
        </Table.Body>
      );
    }else{
      return(
        <Table.Body>
          <Table.Cell textAlign='center'>$XXX</Table.Cell>
          <Table.Cell textAlign='center'>$XXX</Table.Cell>
          <Table.Cell textAlign='center'>$XXX</Table.Cell>
          <Table.Cell textAlign='center'>XXX%</Table.Cell>
          <Table.Cell textAlign='center'>$XXX</Table.Cell>
          <Table.Cell textAlign='center'>$XXX</Table.Cell>
          <Table.Cell textAlign='center'>$XXX</Table.Cell>
        </Table.Body>
      );
    }

  }

  renderMessage(){
    if (this.state.message != '') {
      return(
        <Message warning>
          <Message.Header>Atención!</Message.Header>
          <p>{this.state.message}</p>
        </Message>
      );
    }
  }

  render(){
    return(
      <Modal
        trigger={<Button primary>Renovar</Button>}
        onOpen={()=>{this.setState({open:true})}}
        onClose={()=>{this.setState({open:false})}}
        open={this.state.open}>
        <Modal.Header>Resumen renovación</Modal.Header>
        <Modal.Content>
          {this.renderMessage()}
          <Form onSubmit={this.handleSubmit}>
          <Header as='h2'>
            <Header.Content>
            {this.props.prestamo.id}
            <Header.Subheader>Prestamo id</Header.Subheader>
            </Header.Content>
          </Header>
          <Table celled>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell textAlign='center'>Total Abonado</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Total Multado</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Total Recuperado</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Porcentaje Pagado</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Total a pagar</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Para saldar</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Entregar al renovar</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            {this.renderTotales()}
          </Table>
            <Form.Field>
              <label>Cantidad a prestar:</label>
              <input type='number' min='100' step='1' max='99999' required
                placeholder='Cantidad a prestar en el nuevo prestamo'
                value={this.state.cantNuevoPrestamo}
                onChange={(evt)=>{
                  this.setState({cantNuevoPrestamo: evt.target.value})
                }} />
            </Form.Field>
            {this.renderCantidadEntregar()}
            {this.renderButton()}
          </Form>
          <br></br>
        </Modal.Content>
      </Modal>
    )
  }
}
