import React from 'react';
import ReactDOM from 'react-dom';
import ClienteCard from './Componentes/ClienteCard.jsx'
import ClienteForm from './Componentes/ClienteForm.jsx'
import { Container, Segment, Card, Button, Icon, Image, Modal, Header, Dimmer, Loader} from 'semantic-ui-react';

export default class Clientes extends React.Component{

  constructor(props) {
    super(props)
    this.state = {
      clientes: [],
      modalOpenAgregar: false,
      modalOpenWarning: false,
      nuevoCliente:{},
      conClientes: true
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
    //1
    if (this.state.nuevoCliente.nombre) {
        //2
        fetch(localStorage.getItem('url') + 'clientes',{
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body:JSON.stringify({
            nombre: this.state.nuevoCliente.nombre,
            direccion: this.state.nuevoCliente.direccion,
            telefono: this.state.nuevoCliente.telefono
          })
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
    }else{
      //3
      this.setState({modalOpenWarning:true});
    }
    //4
  }

  cargarClientes(){
    //1
      fetch(localStorage.getItem('url') + 'clientes',{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        //2
        if (response.data.length > 0) {
          //3
          this.setState({clientes:response.data, conClientes:true});
        }else{
          //4
          this.setState({conClientes:false});
        }

      })
      //5
  }


  renderClientesCards(){
    //1
    if (this.state.conClientes) {
      //2
      if (this.state.clientes.length > 0) {
        //3
        return(
          this.state.clientes.map((cliente) =>{
            return (
              <ClienteCard key={cliente.id} cliente={cliente} removeCliente={this.removeCliente}>
              </ClienteCard>
            )
          })
        );
      }else{
        //4
        return (
          <div>
            <Dimmer active inverted>
              <Loader size='large'>Descargando...</Loader>
            </Dimmer>
            <Image src='/assets/images/descargandoClientes.png'/>
          </div>
        );
      }
    }else{
      //5
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
          <Modal
            trigger={<Button color='green'  onClick={this.handleOpenAgregar}>Agregar</Button>}
            onClose={this.handleCloseAgregar}
            open={this.state.modalOpenAgregar}>
            <Header content='Agregar cliente' />
            <Modal.Content>
                <ClienteForm getData={this.onCreateHandler}></ClienteForm>
            </Modal.Content>
            <Modal.Actions>
              <Button color='green' onClick={this.agregarCliente}>
                Guardar
              </Button>
              <Button color='red' onClick={this.handleCloseAgregar}>
                Cancelar
              </Button>
            </Modal.Actions>
          </Modal>
        </Segment>
        <Segment>
          <Card.Group>
           {this.renderClientesCards()}
          </Card.Group>
        </Segment>
      </div>
    )
  }

  removeCliente(cliente){
    const clientesViejo = this.state.clientes.filter((c) =>{
      return c.id != cliente.id
    });
    this.setState({clientes: clientesViejo});
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
