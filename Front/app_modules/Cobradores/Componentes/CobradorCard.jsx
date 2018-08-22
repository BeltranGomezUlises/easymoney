import React from 'react';
import { Card , Icon, Input, Button, Modal, Header, Label, Form} from 'semantic-ui-react';
import CobradorForm from './CobradorForm.jsx';
import DetalleCobros from './DetalleCobros.jsx';
import * as utils from '../../../utils.js';

export default class CobradorCard extends React.Component{

  constructor(props) {
    super(props);
    this.state = {
      modalOpenEditar: false,
      modalOpenEliminar: false,
      modalOpenWarning: false,
      modalOpenCobros: false,
      cobrador: this.props.cobrador
    };

    this.handleOpenEditar = this.handleOpenEditar.bind(this);
    this.handleOpenEliminar = this.handleOpenEliminar.bind(this);

    this.handleOpenCobros = this.handleOpenCobros.bind(this);
    this.handleCloseCobros = this.handleCloseCobros.bind(this);

    this.handleCloseEditar = this.handleCloseEditar.bind(this);
    this.handleCloseEliminar = this.handleCloseEliminar.bind(this);
    this.onEditHandler = this.onEditHandler.bind(this);
    this.editarCobrador = this.editarCobrador.bind(this);
    this.eliminarCobrador = this.eliminarCobrador.bind(this);
    this.handleCloseWarning = this.handleCloseWarning.bind(this);
  }

  handleOpenCobros(){
      this.setState({modalOpenCobros:true});
  }
  handleCloseCobros(){
      this.setState({modalOpenCobros:false});
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
    this.setState({cobrador});
  }

  editarCobrador(){
    if (this.state.cobrador.nombre !== '') {
        fetch(localStorage.getItem('url') + 'usuarios',{
          method: 'PUT',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body: JSON.stringify(this.state.cobrador)
        }).then((res)=> res.json())
        .then((response) =>{
            utils.evalResponse(response);
            this.setState({modalOpenEditar:false});
        })
    }else{
        this.setState({modalOpenWarning:true});
    }
  }

  eliminarCobrador(){
      fetch(localStorage.getItem('url') + 'usuarios',{
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
          utils.evalResponse(response, () => {
            this.props.removeCobrador(response.data);
          });
      })
  }

  renderCobradorContent(){
    return(
      <div>
        {this.state.cobrador.nombreCompleto}
        <br/><br/>
        Contraseña: {this.state.cobrador.contra}
      </div>
    );
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
               {this.renderCobradorContent()}
             </Card.Description>
          </Card.Content>
          <Card.Content extra>
             <div className='ui three buttons'>
               <Modal
                 trigger={<Button basic color='blue' onClick={this.handleOpenEditar}>Editar</Button>}
                 onClose={this.handleCloseEditar}
                 open={this.state.modalOpenEditar}>
                 <Header content='Editar cobrador' />
                 <Modal.Content>
                   <CobradorForm cobrador={this.state.cobrador} getData={this.onEditHandler} updateCobrador={this.editarCobrador}>
                   </CobradorForm>
                 </Modal.Content>
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
               <Modal
                 trigger={<Button basic color='blue' onClick={this.handleOpenCobros}>Cobros</Button>}
                 onClose={this.handleCloseCobros}
                 open={this.state.modalOpenCobros}>
                 <Header content='Cobros de hoy' />
                 <Modal.Content>
                   <DetalleCobros cobrador={this.state.cobrador}/>
                 </Modal.Content>
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
