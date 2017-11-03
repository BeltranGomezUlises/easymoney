import React from 'react';
import { Card , Icon, Input, Button, Header, Modal, Label, Form} from 'semantic-ui-react';
import ClienteForm from './ClienteForm.jsx'

export default class ClienteCard extends React.Component{

  constructor(props){
    super(props);
    this.state = {
      modalOpenEditar: false,
      modalOpenEliminar: false,
      modalOpenWarning: false,
      cliente: this.props.cliente,
      clienteOriginal: {}
    };

    Object.assign(this.state.clienteOriginal, this.props.cliente);

    this.handleOpenEditar = this.handleOpenEditar.bind(this);
    this.handleOpenEliminar = this.handleOpenEliminar.bind(this);
    this.handleCloseEditar = this.handleCloseEditar.bind(this);
    this.handleCloseEliminar = this.handleCloseEliminar.bind(this);
    this.onEditHandler = this.onEditHandler.bind(this);
    this.editarCliente = this.editarCliente.bind(this);
    this.eliminarCliente = this.eliminarCliente.bind(this);
    this.handleCloseWarning = this.handleCloseWarning.bind(this);
    this.cancelEditar = this.cancelEditar.bind(this);
  }

  handleCloseWarning(){
    this.setState({modalOpenWarning: false});
  }

  handleOpenEditar(){
    console.log("open", this.state);
    this.setState({ modalOpenEditar: true})
  }

  handleCloseEditar(){
    this.setState({ modalOpenEditar: false })
  }

  cancelEditar(){
    console.log("cancel",this.state);
    let {clienteOriginal} = this.state;
    this.setState({ modalOpenEditar: false, cliente: clienteOriginal })
  }

  handleOpenEliminar(){
    this.setState({ modalOpenEliminar: true })
  }

  handleCloseEliminar(){
    this.setState({ modalOpenEliminar: false })
  }

  onEditHandler(cliente){
    this.setState({cliente});
  }

  editarCliente(){
    if (this.state.cliente.nombre !== '') {
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
        fetch(localStorage.getItem('url') + 'clientes',{
          method: 'PUT',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body: JSON.stringify({
            id: this.state.cliente.id,
            nombre: this.state.cliente.nombre,
            direccion: this.state.cliente.direccion,
            telefono: this.state.cliente.telefono
          })
        }).then((res)=> res.json())
        .then((response) =>{
            this.setState({modalOpenEditar:false});
        })
       })
    }else{
        this.setState({modalOpenWarning:true});
    }
  }

  eliminarCliente(){
    console.log(this.state.cliente)
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
      fetch(localStorage.getItem('url') + 'clientes',{
        method: 'DELETE',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        },
        body: JSON.stringify({
          id: this.state.cliente.id
        })
      }).then((res)=> res.json())
      .then((response) =>{
          this.setState({modalOpenEliminar:false});
          this.props.removeCliente(response.data);
      })
     })
  }

  render(){
    return(
      <div style={{padding:'10px'}}>
        <Card>
          <Card.Content>
             <Card.Header>
               {this.state.cliente.nombre}
             </Card.Header>
             <Card.Meta>
               {this.state.cliente.telefono}
             </Card.Meta>
             <Card.Description>
               {this.state.cliente.direccion}
             </Card.Description>
          </Card.Content>
          <Card.Content extra>
             <div className='ui two buttons'>
               <Modal
                 trigger={<Button basic color='blue' onClick={this.handleOpenEditar}>Editar</Button>}
                 onClose={this.handleCloseEditar}
                 open={this.state.modalOpenEditar}>
                 <Header content='Editar cliente' />
                 <Modal.Content>
                   <ClienteForm cliente={this.state.cliente} getData={this.onEditHandler}>

                   </ClienteForm>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.editarCliente}>
                     Guardar
                   </Button>
                   <Button color='red' onClick={this.cancelEditar}>
                     Cancelar
                   </Button>
                 </Modal.Actions>
               </Modal>
               <Modal
                 trigger={<Button basic color='red' onClick={this.handleOpenEliminar}>Eliminar</Button>}
                 onClose={this.handleCloseEliminar}
                 open={this.state.modalOpenEliminar}>
                 <Header content='Eliminar cliente' />
                 <Modal.Content>
                   <h3>¿Está seguro de eliminar al cliente y borrar su historial?</h3>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.eliminarCliente}>
                     Borrar
                   </Button>
                   <Button color='red' onClick={this.handleCloseEliminar}>
                     Cancelar
                   </Button>
                 </Modal.Actions>
               </Modal>
               <Modal open={this.state.modalOpenWarning} onClose={this.handleCloseWarning} closeOnRootNodeClick={false}>
                 <Modal.Content>
                   <h3>Es necesario llenar el nombre del cliente</h3>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.handleCloseWarning} inverted> Entendido </Button>
                 </Modal.Actions>
               </Modal>
             </div>
          </Card.Content>
        </Card>
      </div>
    );
  }
}
