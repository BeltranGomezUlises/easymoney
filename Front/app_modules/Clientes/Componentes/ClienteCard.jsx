import React from 'react';
import ClienteModal from './ClienteModal.jsx';
import { Card , Icon, Input, Button, Header, Modal} from 'semantic-ui-react';

export default class ClienteCard extends React.Component{

  constructor(props){
    super(props);
    this.state = {
      modalOpenEditar: false,
      modalOpenEliminar: false
    };
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

  editarCliente(){
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
          id: this.props.id,
          nombre: this.props.nombre,
          direccion: this.props.direccion,
          telefono: this.props.telefono
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
                 trigger={<Button basic color='blue' onClick={this.handleOpenEditar.bind(this)}>Editar</Button>}
                 onClose={this.handleCloseEditar.bind(this)}
                 open={this.state.modalOpenEditar}
>
                 <Header content='Editar cliente' />
                 <Modal.Content>
                   <h3>contenido cliente</h3>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.editarCliente.bind(this)}>
                     Guardar
                   </Button>
                   <Button color='red' onClick={this.handleCloseEditar.bind(this)}>
                     Cancelar
                   </Button>
                 </Modal.Actions>
               </Modal>
               <Modal
                 trigger={<Button basic color='red' onClick={this.handleOpenEliminar.bind(this)}>Eliminar</Button>}
                 onClose={this.handleCloseEliminar.bind(this)}
                 open={this.state.modalOpenEliminar}>
                 <Header content='Eliminar cliente' />
                 <Modal.Content>
                   <h3>¿Está seguro de eliminar al cliente y borrar su historial?</h3>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.eliminarCliente.bind(this)}>
                     Borrar
                   </Button>
                   <Button color='red' onClick={this.handleCloseEliminar.bind(this)}>
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
