import React from 'react';
import ReactDOM from 'react-dom';
import ClienteCard from './Componentes/ClienteCard.jsx'
import ClienteForm from './Componentes/ClienteForm.jsx'
import { Container, Segment, Card, Button, Form, Input, Image, Modal, Header, Dimmer, Loader, Divider} from 'semantic-ui-react';

export default class Clientes extends React.Component{

  constructor(props) {
    super(props)
    this.state = {
      clientes: [],
      modalOpenAgregar: false,
      modalOpenWarning: false,
      nuevoCliente:{},
      conClientes: true,
      filtro:{
        nombre:'',
        apodo:''
      }
    }

    this.removeCliente = this.removeCliente.bind(this);
    this.handleCloseAgregar = this.handleCloseAgregar.bind(this);
    this.handleOpenAgregar = this.handleOpenAgregar.bind(this);
    this.onCreateHandler = this.onCreateHandler.bind(this);
    this.agregarCliente = this.agregarCliente.bind(this);
    this.handleCloseWarning = this.handleCloseWarning.bind(this);
  }

  handleCloseWarning(){
    this.setState({modalOpenWarning: false});
  }

  handleCloseAgregar(){
    this.setState({modalOpenAgregar: false});
  }

  handleOpenAgregar(){
    this.setState({modalOpenAgregar: true});
  }

  onCreateHandler(nuevoCliente){
    this.setState({nuevoCliente});
  }

  agregarCliente(){
      fetch(localStorage.getItem('url') + 'clientes',{
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        },
        body:JSON.stringify(this.state.nuevoCliente)
      })
      .then((res)=> res.json())
      .then((response) =>{
        //agregar nuevoCliente a la lista actual
        let clientes = [...this.state.clientes, response.data];
        //limpiar nuevo cliente
        this.setState({
          clientes,
          nuevoCliente:{},
          conClientes:true
        });
        this.handleCloseAgregar();
      })
  }

  cargarClientes(){
      fetch(localStorage.getItem('url') + 'clientes/cargarClientes',{
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        },
        body: JSON.stringify(this.state.filtro)
      }).then((res)=> res.json())
      .then((response) =>{
        if (response.data.length > 0) {
          this.setState({clientes:response.data, conClientes:true});
        }else{
          this.setState({conClientes:false});
        }
      })
  }

  removeCliente(cliente){
    const clientesViejo = this.state.clientes.filter((c) =>{
      return c.id != cliente.id
    });
    this.setState({clientes: clientesViejo});
  }

  renderClientesCards(){
    if (this.state.conClientes) {
      if (this.state.clientes.length > 0) {
        return(
          this.state.clientes.map((cliente) =>{
            return (
              <ClienteCard
                key={cliente.id}
                cliente={cliente}
                removeCliente={this.removeCliente}
                clientes={this.state.clientes}
              >
              </ClienteCard>
            )
          })
        );
      }else{
        return (
          <div>
            <Dimmer active inverted>
              <Loader size='large'>Descargando...</Loader>
            </Dimmer>
            <Image src='assets/images/descargandoClientes.png'/>
          </div>
        );
      }
    }else{
      return(
        <Container textAlign='center'>
            <h2>Sin Clientes...</h2>
        </Container>
      );
    }
  }

  renderClientes(){
    return(
      <div>
        <Segment>
          <Divider horizontal>Filtros</Divider>
            <Form>
              <Form.Group>
                <Form.Field
                   control={Input}
                   label='Nombre Cliente:'
                   type='text'
                   placeholder='Nombre de cliente'
                   value={this.state.filtro.nombre}
                   onChange={ (evt) => {
                     let {filtro} = this.state;
                     filtro.nombre = evt.target.value;
                     this.setState({filtro});
                     this.cargarClientes();
                   }}
                />
                <Form.Field
                   control={Input}
                   label='Apodo Cliente:'
                   type='text'
                   placeholder='Apodo de cliente'
                   value={this.state.filtro.apodo}
                   onChange={ (evt) => {
                     let {filtro} = this.state;
                     filtro.apodo = evt.target.value;
                     this.setState({filtro});
                     this.cargarClientes();
                   }}
                />
              </Form.Group>
              <Form.Group>
                <Form.Field control={Button} onClick={ () => {
                  let filtro = {
                    nombre:'',
                    apodo:''
                  }
                  this.setState({filtro});
                  this.cargarClientes();
                }}>Limpiar filtros</Form.Field>
                <Modal
                  trigger={
                    <Button color='green'
                    onClick={this.handleOpenAgregar}>Agregar</Button>
                  }
                  onClose={this.handleCloseAgregar}
                  open={this.state.modalOpenAgregar}>
                  <Header content='Agregar cliente' />
                  <Modal.Content>
                      <ClienteForm
                      getData={this.onCreateHandler}
                      agregarCliente={this.agregarCliente}
                      clientes={this.state.clientes}/>
                  </Modal.Content>
                </Modal>
              </Form.Group>
            </Form>

        </Segment>
        <Segment>
          <Card.Group>
           {this.renderClientesCards()}
          </Card.Group>
        </Segment>
      </div>
    )
  }

  componentWillMount() {
    this.cargarClientes();
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
          <Segment textAlign='center'>
            <h2>CLIENTES</h2>
          </Segment>
          <Modal open={this.state.modalOpenWarning} onClose={this.handleCloseWarning} closeOnRootNodeClick={false}>
            <Modal.Content>
              <h3>Es necesario llenar el nombre del cliente</h3>
            </Modal.Content>
            <Modal.Actions>
              <Button color='green' onClick={this.handleCloseWarning} inverted> Entendido </Button>
            </Modal.Actions>
          </Modal>
          {this.renderClientes()}
      </div>
    );
  }

}
