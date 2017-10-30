import React from 'react';
import { Card , Icon, Input, Button, Modal, Header} from 'semantic-ui-react';

export default class CobradorCard extends React.Component{

  constructor(props) {
    super(props);
    this.state = {
      modalOpenEditar: false,
      modalOpenEliminar: false
    };
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

  render(){
    return(
        <Card>
          <Card.Content>
             <Card.Header>
               {this.props.nombre}
             </Card.Header>
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
                 <Header content='Editar cobrador' />
                 <Modal.Content>
                   <h3>contenido cobrador</h3>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.handleCloseEditar.bind(this)}>
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
                 open={this.state.modalOpenEliminar}
               >
                 <Header content='Eliminar cobrador' />
                 <Modal.Content>
                   <h3>¿Está seguro de eliminar al cobrador?</h3>
                 </Modal.Content>
                 <Modal.Actions>
                   <Button color='green' onClick={this.handleCloseEliminar.bind(this)}>
                     Eliminar
                   </Button>
                   <Button color='red' onClick={this.handleCloseEliminar.bind(this)}>
                     Cancelar
                   </Button>
                 </Modal.Actions>
               </Modal>
             </div>
          </Card.Content>
        </Card>
    );
  }
}
