import React from 'react';
import ReactDOM from 'react-dom';
import { Header, Table, Dimmer, Loader, Segment, Container, Modal, Button} from 'semantic-ui-react'
import MovimientoForm from './MovimientoForm.jsx'
import * as utils from '../../../utils.js';

export default class MovimientoList extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      movimientos: [],
      conMovimientos: true,
      modalOpenAgregar: false,
      modalOpenWarning: false
    }
    this.handleCloseAgregar = this.handleCloseAgregar.bind(this);
    this.handleOpenAgregar = this.handleOpenAgregar.bind(this);
  }

  handleCloseAgregar(hasChanches){    
    this.setState({modalOpenAgregar: false});
    if (hasChanches.hasChanches) {
        this.cargarMovimientos();
    }
  }

  handleOpenAgregar(){
    this.setState({modalOpenAgregar: true});
  }

  componentWillMount(){
    this.cargarMovimientos();
  }

  cargarMovimientos(){
      fetch(localStorage.getItem('url') + 'movimientos',{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        utils.evalResponse(response, ()=>{
          if (response.data.length > 0) {
            this.setState({
              movimientos: response.data,
              conMovimiento:true
            });
          }else{
            this.setState({conMovimiento: false});
          }
        })
      })
  }

  renderMovimientoList(){
    return this.state.movimientos.map((ie) =>{
      return(
        <Table.Row key={ie.id}>
          <Table.Cell>
            <Header textAlign='center'>
              {ie.id}
            </Header>
          </Table.Cell>
          <Table.Cell>
            {new Date(ie.fecha).toLocaleDateString()}
          </Table.Cell>
          <Table.Cell>
            {ie.cantidad}
          </Table.Cell>
          <Table.Cell>
            {ie.descripcion}
          </Table.Cell>
        </Table.Row>
      )
    })
  }

  renderMovimientos(){
    if (this.state.conMovimientos) {
      if (this.state.movimientos.length > 0) {
        return(
          <Table celled selectable>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell textAlign='center'>Id</Table.HeaderCell>
              <Table.HeaderCell>fecha</Table.HeaderCell>
                <Table.HeaderCell>$ cantidad</Table.HeaderCell>
              <Table.HeaderCell>descripcion</Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {this.renderMovimientoList()}
          </Table.Body>
        </Table>
        )
      }else{
          return(
            <div>
              <Dimmer active inverted>
                <Loader size='large'>Descargando...</Loader>
              </Dimmer>
            </div>
          )
      }
    }else{
      return(
        <Container textAlign='center'>
            <h2>Sin Prestamos...</h2>
        </Container>
      );
    }
  }

  render() {
    return (
      <div>
      <Segment>
        <Modal trigger={<Button color='green' onClick={this.handleOpenAgregar}>Agregar</Button>}
          onClose={this.handleCloseAgregar}
          open={this.state.modalOpenAgregar}>
          <Header content='Agregar Ingreso o Egreso' />
          <Modal.Content>
            <MovimientoForm handleClose={this.handleCloseAgregar}/>
          </Modal.Content>
        </Modal>
      </Segment>
        <Segment>
          {this.renderMovimientos()}
        </Segment>
      </div>
    )
  }

}
