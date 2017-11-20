import React from 'react';
import ReactDOM from 'react-dom';
import { Header, Table, Dimmer, Loader, Segment, Container, Modal, Button} from 'semantic-ui-react'
import PrestamoForm from './PrestamoForm.jsx'
import PrestamoDetalle from './PrestamoDetalle.jsx'

export default class PrestamoList extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      prestamos: [],
      modalOpenAgregar: false,
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
  }

  componentWillMount(){
    this.cargarPrestamos();
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
              <PrestamoDetalle prestamo={prestamo}>
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
          {this.renderPrestamos()}
        </Segment>
      </div>
    )
  }
}
