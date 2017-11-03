import React from 'react';
import ReactDOM from 'react-dom';
import { Header, Table, Dimmer, Loader, Segment, Modal, Button} from 'semantic-ui-react'
import PrestamoForm from './PrestamoForm.jsx'


export default class PrestamoList extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      prestamos: [],
      modalOpenAgregar: false,
      nuevoPrestamo:{}
    }

    this.handleCloseAgregar = this.handleCloseAgregar.bind(this);
    this.handleOpenAgregar = this.handleOpenAgregar.bind(this);
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

  agregarPrestamo(){

  }

  onCreateHandler(prestamo){

  }

  cargarPrestamos(){
    fetch(localStorage.getItem('url') + 'accesos/login', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        user: 'admin',
        pass: '1234',
      })
    }).then((res) => res.json())
    .then((response) => localStorage.setItem('tokenSesion', response.meta.metaData))
    .then(()=>{
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
        this.setState({prestamos:response.data});
      })
     })
  }

  renderPrestamos(){
    if (this.state.prestamos.length > 0) {
      return(
          this.state.prestamos.map((prestamo) =>{
            return(
              <Table.Row key={prestamo.id}>
                <Table.Cell>
                  <Header as='h3' textAlign='center'>
                    {prestamo.id}
                  </Header>
                </Table.Cell>
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
                <Table.Cell>
                    30%
                </Table.Cell>
              </Table.Row>
            )
          })
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
  }

  render() {
    return (
      <div>
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
          <Table celled padded>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Id</Table.HeaderCell>
                <Table.HeaderCell>Cliente</Table.HeaderCell>
                <Table.HeaderCell>Cobrador</Table.HeaderCell>
                <Table.HeaderCell>Cantidad</Table.HeaderCell>
                <Table.HeaderCell>Cantidad a Pagar</Table.HeaderCell>
                <Table.HeaderCell>Fecha/Hora Prestamo</Table.HeaderCell>
                <Table.HeaderCell>Fecha Limite</Table.HeaderCell>
                <Table.HeaderCell>Porcentaje Pagado</Table.HeaderCell>
              </Table.Row>
            </Table.Header>

            <Table.Body>
              {this.renderPrestamos()}
            </Table.Body>
          </Table>
        </Segment>
      </div>
    )
  }
}
