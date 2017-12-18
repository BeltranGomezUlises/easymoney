import React from 'react';
import ReactDOM from 'react-dom';
import { Header, Table, Dimmer, Loader, Segment, Container, Modal, Button} from 'semantic-ui-react'
import PrestamoForm from './PrestamoForm.jsx'
import PrestamoDetalle from './PrestamoDetalle.jsx'
import * as utils from '../../../utils.js';

export default class PrestamoList extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      prestamos:[],
      totalesPrestamos:{},
      modalOpenAgregar:false,
      conPrestamos:true,
      nuevoPrestamo:{
        cantidad:0,
        cliente:{
          id:0
        },
        cobrador:{
          id:0
        }
      }
    }

    this.handleCloseAgregar = this.handleCloseAgregar.bind(this);
    this.handleOpenAgregar = this.handleOpenAgregar.bind(this);
    this.handleCloseWarning = this.handleCloseWarning.bind(this);
    this.handleOpenWarning = this.handleOpenWarning.bind(this);
    this.agregarPrestamo = this.agregarPrestamo.bind(this);
    this.onCreateHandler = this.onCreateHandler.bind(this);
    this.cargarTotalesPrestamos = this.cargarTotalesPrestamos.bind(this);
  }

  componentWillMount(){
    this.cargarPrestamos();
    this.cargarTotalesPrestamos();
  }

  handleCloseAgregar(){
    this.setState({modalOpenAgregar: false});
  }

  handleOpenAgregar(){
    this.setState({modalOpenAgregar: true});
  }

  handleCloseWarning(){
    this.setState({modalOpenWarning: false});
  }

  handleOpenWarning(){
    this.setState({modalOpenWarning: true});
  }

  agregarPrestamo(){
    let {nuevoPrestamo} = this.state.nuevoPrestamo;
    if (nuevoPrestamo.cantidad > 0) {
        fetch(localStorage.getItem('url') + 'prestamos',{
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body:JSON.stringify({
            cantidad: nuevoPrestamo.cantidad,
            cliente: nuevoPrestamo.cliente,
            cobrador: nuevoPrestamo.cobrador
          })
        }).then((res)=> res.json())
        .then((response) =>{
          this.handleCloseAgregar();
          this.cargarPrestamos();
          this.cargarTotalesPrestamos();
        })
    }else{
      this.handleOpenWarning();
    }
  }

  onCreateHandler(nuevoPrestamo){
    this.setState({nuevoPrestamo});
  }

  cargarPrestamos(){
      fetch(localStorage.getItem('url') + 'prestamos',{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        if (response.data.length > 0) {
          this.setState({prestamos:response.data, conPrestamos:true});
        }else{
          this.setState({conPrestamos: false});
        }
      })
  }

  cargarTotalesPrestamos(){
    fetch(localStorage.getItem('url') + 'prestamos/totalesGenerales',{
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
        this.setState({totalesPrestamos: response.data});
      });
    })
  }

  renderPrestamosList(){
    return this.state.prestamos.map((prestamo) =>{
      return(
        <Table.Row key={prestamo.id}>
          <Modal trigger={
              <Table.Cell style={{cursor: 'pointer'}}>
                <Header textAlign='center'>
                  {prestamo.id}
                </Header>
              </Table.Cell>
            }>
            <Modal.Header>Detalle Prestamo</Modal.Header>
            <Modal.Content>
              <PrestamoDetalle prestamo={prestamo} update={this.cargarTotalesPrestamos}>
              </PrestamoDetalle>
            </Modal.Content>
          </Modal>
          <Table.Cell>
            {prestamo.cliente.nombre}
          </Table.Cell>
          <Table.Cell>
            {prestamo.cobrador.nombre}
          </Table.Cell>
          <Table.Cell textAlign='right'>
            ${prestamo.cantidad}
          </Table.Cell>
          <Table.Cell textAlign='right'>
              ${prestamo.cantidadPagar}
          </Table.Cell>
          <Table.Cell>
              {new Date(prestamo.fecha).toLocaleString()}
          </Table.Cell>
          <Table.Cell>
              {new Date(prestamo.fechaLimite).toLocaleDateString()}
          </Table.Cell>
        </Table.Row>
      )
    })
  }

  renderPrestamos(){
    if (this.state.conPrestamos) {
      if (this.state.prestamos.length > 0) {
        return(
          <Table celled selectable>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell textAlign='center'>Id</Table.HeaderCell>
              <Table.HeaderCell>Cliente</Table.HeaderCell>
              <Table.HeaderCell>Cobrador</Table.HeaderCell>
              <Table.HeaderCell textAlign='right'>Cantidad</Table.HeaderCell>
              <Table.HeaderCell textAlign='right'>Cantidad a Pagar</Table.HeaderCell>
              <Table.HeaderCell>Fecha/Hora Prestamo</Table.HeaderCell>
              <Table.HeaderCell>Fecha Limite</Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {this.renderPrestamosList()}
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

  renderTotalesPrestamosList(){
    let {totalesPrestamos} = this.state;
    if(totalesPrestamos.totalPrestado !== undefined){
      return(
          <Table.Row>
            <Table.Cell>
              ${totalesPrestamos.totalPrestado}
            </Table.Cell>
            <Table.Cell>
              ${totalesPrestamos.totalAbonado}
            </Table.Cell>
            <Table.Cell>
              ${totalesPrestamos.totalMultado}
            </Table.Cell>
            <Table.Cell>
              ${totalesPrestamos.totalRecuperado}
            </Table.Cell>
            <Table.Cell>
              ${totalesPrestamos.capital}
            </Table.Cell>
            <Table.Cell>
              %{totalesPrestamos.porcentajeCompletado}
            </Table.Cell>
          </Table.Row>
      );
    }
  }

  renderTotalesPrestamos(){
    return(
      <Table celled>
        <Table.Header celled>
          <Table.Row>
            <Table.HeaderCell>Total Prestado</Table.HeaderCell>
            <Table.HeaderCell>Total Abonado</Table.HeaderCell>
            <Table.HeaderCell>Total Multado</Table.HeaderCell>
            <Table.HeaderCell>Total Recuperado</Table.HeaderCell>
            <Table.HeaderCell>Capital</Table.HeaderCell>
            <Table.HeaderCell>Porcentaje Cobrado</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {this.renderTotalesPrestamosList()}
        </Table.Body>
      </Table>
    );
  }

  render() {
    return (
      <div>
        <Modal open={this.state.modalOpenWarning} onClose={this.handleCloseWarning} closeOnRootNodeClick={false}>
          <Modal.Content>
            <h3>Es necesario colocar una cantidad al prestamo</h3>
          </Modal.Content>
          <Modal.Actions>
            <Button color='green' onClick={this.handleCloseWarning} inverted> Entendido </Button>
          </Modal.Actions>
        </Modal>

        <Segment>
          <Modal trigger={<Button color='green' onClick={this.handleOpenAgregar}>Agregar</Button>}
            onClose={this.handleCloseAgregar}
            open={this.state.modalOpenAgregar}>
            <Header content='Agregar prestamo' />
            <Modal.Content>
                <PrestamoForm getData={this.onCreateHandler}></PrestamoForm>
            </Modal.Content>
            <Modal.Actions>
              <Button color='green' onClick={this.agregarPrestamo}>
                Guardar
              </Button>
              <Button color='red' onClick={this.handleCloseAgregar}>
                Cancelar
              </Button>
            </Modal.Actions>
          </Modal>
        </Segment>
        <Segment>
          <div>
            {this.renderPrestamos()}
          </div>
          <Segment>
            {this.renderTotalesPrestamos()}
          </Segment>
        </Segment>
      </div>
    )
  }
}
