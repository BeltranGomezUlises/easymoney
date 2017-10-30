import React from 'react';
import { Card , Icon, Input, Button, Header, Modal, Label, Form} from 'semantic-ui-react';
import ClienteForm from './ClienteForm.jsx'

export default class ClienteCard extends React.Component{

  constructor(props){
    super(props);
    this.state = {
      modalOpenEditar: false,
      modalOpenEliminar: false
    };

    this.handleOpenEditar = this.handleOpenEditar.bind(this);
    this.handleOpenEliminar = this.handleOpenEliminar.bind(this);
    this.handleCloseEditar = this.handleCloseEditar.bind(this);
    this.handleCloseEliminar = this.handleCloseEliminar.bind(this);
  }

  handleOpenEditar(){
    const {id, nombre, direccion, telefono}  = this.props;
    this.setState({ modalOpenEditar: true })
  }

  handleCloseEditar(){
    this.setState({ modalOpenEditar: false })
  }

  handleOpenEliminar(){
    this.setState({ modalOpenEliminar: true })
  }

  handleCloseEliminar(){
    this.setState({ modalOpenEliminar: false })
  }

  editarCliente(cliente){
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
          id: cliente.id,
          nombre: cliente.nombre,
          direccion: cliente.direccion,
          telefono: cliente.telefono
        })
      }).then((res)=> res.json())
      .then((response) =>{
          this.setState({modalOpenEditar:false});
      })
     })
  }

  eliminarCliente(){
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
          id: this.props.id
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
               {this.props.nombre}
             </Card.Header>
             <Card.Meta>
               {this.props.telefono}
             </Card.Meta>
             <Card.Description>
               {this.props.direccion}
             </Card.Description>
          </Card.Content>
          <Card.Content extra>
             <div className='ui two buttons'>
               <Modal
                 trigger={<Button basic color='blue' onClick={this.handleOpenEditar}>Editar</Button>}
                 onClose={this.handleCloseEditar.bind(this)}
                 open={this.state.modalOpenEditar}>
                 <Header content='Editar cliente' />
                 <Modal.Content>
                   <ClienteForm>

                   </ClienteForm>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.editarCliente}>
                     Guardar
                   </Button>
                   <Button color='red' onClick={this.handleCloseEditar}>
                     Cancelar
                   </Button>
                 </Modal.Actions>
               </Modal>
               <Modal
                 trigger={<Button basic color='red' onClick={this.handleOpenEliminar}>Eliminar</Button>}
                 onClose={this.handleCloseEliminar.bind(this)}
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
             </div>
          </Card.Content>
        </Card>
      </div>
    );
  }
}
