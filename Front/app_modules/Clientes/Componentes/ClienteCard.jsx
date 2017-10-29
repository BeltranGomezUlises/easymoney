import React from 'react';
import ClienteModal from './ClienteModal.jsx';
import { Card , Icon, Input, Button} from 'semantic-ui-react';

export default class ClienteCard extends React.Component{

  constructor(props) {
    super(props);    
  }
  toggleModal = () =>{
    this.setState({open: !this.state.open});
  }
  render(){

    return(
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
               <Button >Tiny</Button>
               <Button basic color='blue' onClick={.show()}>Editar</Button>
               <Button basic color='red'>Eliminar</Button>
             </div>
          </Card.Content>
        </Card>
    );
  }
}
