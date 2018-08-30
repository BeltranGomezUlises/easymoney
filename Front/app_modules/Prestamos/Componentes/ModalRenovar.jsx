import React, {Component} from 'react';
import {Button, Modal, Form, Header, Table,  Icon, Segment, Dimmer, Loader} from 'semantic-ui-react';
import * as utils from '../../../utils.js';

export default class PrestamoDetalle extends Component{

  constructor(props){
    super(props);
    this.state = {
      open : false,
      cantNuevoPrestamo : props.prestamo.cantidad,
      cantidadEntregar : 0,
      loading:false,
      renovado:false
    }

    this.onOpen = this.onOpen.bind(this);
    this.onClose = this.onClose.bind(this);
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
      this.setState({
        cantidadEntregar: response.data,
        renovado:true,
        loading:false
      });
    });
  }

  onOpen(){
    this.setState({open:true});
  }

  onClose(){
    this.setState({open:false});
  }

  renderCantidadEntregar(){
    if (this.state.loading) {
      return(
        <Segment>
          <Dimmer active>
            <Loader size='mini'>Generando</Loader>
          </Dimmer>
          <div style={{'height':'50px'}}></div>
        </Segment>
      );
    }else{
      if (this.state.renovado) {
        return(
          <Segment>
            <h2>Cantidad a entregar: ${this.state.cantidadEntregar}</h2>
          </Segment>
        );
      }
    }
  }

  renderButtonCerrar(){
    if (this.state.renovado) {
      return(
         <Button primary content='Cerrar' onClick={()=>{
          this.props.update();
          this.onClose();
         }} />
      );
    }
  }

  renderButton(){
    if (!this.state.renovado) {
      if (this.state.loading) {
        return(
           <Button primary content='Renovar' loading />
        );
      }else{
        return(
          <Button primary content='Renovar' type='submit' />
        );
      }
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

  render(){
    return(
      <Modal
        trigger={<Button primary>Renovar</Button>}
        onOpen={this.onOpen}
        onClose={this.onClose}
        open={this.state.open}>
        <Modal.Header>Resumen renovación</Modal.Header>
        <Modal.Content>
        <Form onSubmit={this.handleSubmit}>
        <Header as='h2'>          
          <Header.Content>
          {this.props.prestamo.id}
          <Header.Subheader>id</Header.Subheader>
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
            <input type='number' min='100' step='1' max='99999'
              required
              placeholder='Cantidad a prestar en el nuevo prestamo'
              value={this.state.cantNuevoPrestamo}
              onInput={(evt)=>{
                this.setState({cantNuevoPrestamo: evt.target.value})
              }} />
          </Form.Field>
          {this.renderCantidadEntregar()}
          {this.renderButton()}
        </Form>
        <br></br>
        {this.renderButtonCerrar()}
        </Modal.Content>
      </Modal>
    )
  }
}
