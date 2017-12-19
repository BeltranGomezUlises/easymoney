import React from 'react';
import ReactDOM from 'react-dom';
import { Header, Table, Divider, Form, Checkbox, Input, Dimmer, Loader, Segment, Container, Modal, Button} from 'semantic-ui-react'
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
      },
      filtro:{
        nombreCliente:'',
        nombreCobrador:'',
        fechaPrestamoFinal: '',
        fechaPrestamoInicial: '',
        fechaLimiteInicial: '',
        fechaLimiteFinal: ''
      }
    }

    this.handleCloseAgregar = this.handleCloseAgregar.bind(this);
    this.handleOpenAgregar = this.handleOpenAgregar.bind(this);
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
    let {filtro} = this.state;

    let fechaPrestamoFinal = filtro.fechaPrestamoFinal !== '' ? (new Date(filtro.fechaPrestamoFinal)).getTime() : '';
    let fechaPrestamoInicial = filtro.fechaPrestamoInicial !== '' ? (new Date(filtro.fechaPrestamoInicial)).getTime() : '';
    let fechaLimiteInicial = filtro.fechaLimiteInicial !== '' ? (new Date(filtro.fechaLimiteInicial)).toISOString() : '';
    let fechaLimiteFinal = filtro.fechaLimiteFinal !== '' ? (new Date(filtro.fechaLimiteFinal)).toISOString() : '';

    fetch(localStorage.getItem('url') + 'prestamos/cargarPrestamos',{
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin':'*',
        'Authorization': localStorage.getItem('tokenSesion')
      },
      body:JSON.stringify({
        nombreCliente: filtro.nombreCliente,
        nombreCobrador : filtro.nombreCobrador,
        fechaPrestamoInicial,
        fechaPrestamoFinal,
        fechaLimiteInicial,
        fechaLimiteFinal
      })
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

          <Divider horizontal>Filtros</Divider>
          <Form>
            <Form.Group>
              <Form.Field
                 control={Input}
                 label='Nombre Cliente:'
                 type='text'
                 placeholder='nombre de cliente...'
                 value={this.state.filtro.nombreCliente}
                 onChange={ (evt) => {
                   let {filtro} = this.state;
                   filtro.nombreCliente = evt.target.value;
                   this.setState({filtro});
                   this.cargarPrestamos();
                 }}/>
              <Form.Field
                 control={Input}
                 label='Nombre Cobrador:'
                 type='text'
                 placeholder='Nombre de Cobrador...'
                 value={this.state.filtro.nombreCobrador}
                 onChange={ (evt) => {
                   let {filtro} = this.state;
                   filtro.nombreCobrador = evt.target.value;
                   this.setState({filtro});
                   this.cargarPrestamos();
                 }}/>

              <Form.Field>
                <label>Prestamos despues de:</label>
                <input
                  type={'date'}
                  value={this.state.filtro.fechaPrestamoInicial}
                  onChange={(evt) => {
                    let {filtro} = this.state;
                    filtro.fechaPrestamoInicial = evt.target.value;
                    this.setState({filtro});
                    this.cargarPrestamos();
                  }}/>
              </Form.Field>
              <Form.Field>
                <label>Prestamos antes de:</label>
                <input
                  type={'date'}
                  value={this.state.filtro.fechaPrestamoFinal}
                  onChange={(evt) => {
                    let {filtro} = this.state;
                    filtro.fechaPrestamoFinal = evt.target.value;
                    this.setState({filtro});
                    this.cargarPrestamos();
                  }}/>
              </Form.Field>

              <Form.Field>
                <label>Fecha limite pago despues de:</label>
                <input
                  type={'date'}
                  value={this.state.filtro.fechaLimiteInicial}
                  onChange={(evt) => {
                    let {filtro} = this.state;
                    filtro.fechaLimiteInicial = evt.target.value;
                    this.setState({filtro});
                    this.cargarPrestamos();
                  }}/>
              </Form.Field >
              <Form.Field>
                <label>Fecha limite pago antes de:</label>
                <input
                  type={'date'}
                  value={this.state.filtro.fechaLimiteFinal}
                  onChange={(evt) => {
                    let {filtro} = this.state;
                    filtro.fechaLimiteFinal = evt.target.value;
                    this.setState({filtro});
                    this.cargarPrestamos();
                  }}/>
              </Form.Field >
           </Form.Group>

            <Form.Field>
              <Checkbox label='Prestamos 100% abonados'
                onChange={ (evt, data) => {
                  let {filtro} = this.state;
                  filtro.acreditados = data.checked;
                  this.setState({filtro});
                  this.cargarPrestamos();
                }}
                checked={this.state.filtro.acreditados} />
            </Form.Field>
            <Form.Field control={Button} primary onClick={ () => {
              let filtro = {
                nombreCliente:'',
                nombreCobrador:'',
                fechaPrestamoFinal:'',
                fechaPrestamoInicial: '',
                fechaLimiteInicial: '',
                fechaLimiteFinal: ''
              }
              this.setState({filtro});
              this.cargarClientes();
            }}>Limpiar filtros</Form.Field>
          </Form>

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
