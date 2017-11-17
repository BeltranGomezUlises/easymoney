import React from 'react';
import { Card , Icon, Input, Button, Modal, Header, Label, Form} from 'semantic-ui-react';
import CobradorForm from './CobradorForm.jsx';

export default class CobradorCard extends React.Component{

  constructor(props) {
    super(props);
    this.state = {
      modalOpenEditar: false,
      modalOpenEliminar: false,
      modalOpenWarning: false,
      cobrador: this.props.cobrador
    };

    this.handleOpenEditar = this.handleOpenEditar.bind(this);
    this.handleOpenEliminar = this.handleOpenEliminar.bind(this);
    this.handleCloseEditar = this.handleCloseEditar.bind(this);
    this.handleCloseEliminar = this.handleCloseEliminar.bind(this);
    this.onEditHandler = this.onEditHandler.bind(this);
    this.editarCobrador = this.editarCobrador.bind(this);
    this.eliminarCobrador = this.eliminarCobrador.bind(this);
    this.handleCloseWarning = this.handleCloseWarning.bind(this);
  }

  handleCloseWarning(){
    this.setState({modalOpenWarning:false});
  }

  handleOpenEditar(){
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

  onEditHandler(cobrador){
    this.setItem({cobrador});
  }

  editarCobrador(){
    if (this.state.cobrador.nombre !== '') {
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
        fetch(localStorage.getItem('url') + 'cobradores',{
          method: 'PUT',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body: JSON.stringify({
            id: this.state.cobrador.id,
            nombre: this.state.cobrador.nombre,
            direccion: this.state.cobrador.direccion
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

  eliminarCobrador(){    
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
      fetch(localStorage.getItem('url') + 'cobradores',{
        method: 'DELETE',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        },
        body: JSON.stringify({
          id: this.state.cobrador.id
        })
      }).then((res)=> res.json())
      .then((response) =>{
          this.setState({modalOpenEliminar:false});
          this.props.removeCobrador(response.data);
      })
     })
  }

  render(){
    return(
      <div style={{padding:'10px'}}>
        <Card>
          <Card.Content>
             <Card.Header>
               {this.state.cobrador.nombre}
             </Card.Header>
             <Card.Description>
               {this.state.cobrador.direccion}
             </Card.Description>
          </Card.Content>
          <Card.Content extra>
             <div className='ui two buttons'>
               <Modal
                 trigger={<Button basic color='blue' onClick={this.handleOpenEditar}>Editar</Button>}
                 onClose={this.handleCloseEditar}
                 open={this.state.modalOpenEditar}>
                 <Header content='Editar cobrador' />
                 <Modal.Content>
                   <CobradorForm cobrador={this.state.cobrador} getData={this.onEditHandler}>

                   </CobradorForm>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.editarCobrador}>
                     Guardar
                   </Button>
                   <Button color='red' onClick={this.handleCloseEditar}>
                     Cancelar
                   </Button>
                 </Modal.Actions>
               </Modal>
               <Modal
                 trigger={<Button basic color='red' onClick={this.handleOpenEliminar}>Eliminar</Button>}
                 onClose={this.handleCloseEliminar}
                 open={this.state.modalOpenEliminar}>
                 <Header content='Eliminar cobrador' />
                 <Modal.Content>
                   <h3>¿Está seguro de eliminar al cobrador y borrar su historial?</h3>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.eliminarCobrador}>
                     Borrar
                   </Button>
                   <Button color='red' onClick={this.handleCloseEliminar}>
                     Cancelar
                   </Button>
                 </Modal.Actions>
               </Modal>
               <Modal open={this.state.modalOpenWarning} onClose={this.handleCloseWarning} closeOnRootNodeClick={false}>
                 <Modal.Content>
                   <h3>Es necesario llenar el nombre del cobrador</h3>
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
