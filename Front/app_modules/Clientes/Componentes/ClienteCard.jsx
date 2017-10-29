import React from 'react';
import ClienteModal from './ClienteModal.jsx';
import { Card , Icon, Input, Button} from 'semantic-ui-react';

export default class ClienteCard extends React.Component{

  constructor(props) {
    super(props);
    this.state = { open: false }
  }
  toggleModal = () =>{
    this.setState({open: !this.state.open});
  }
  render(){

    return(
      <Card.Group>
        <Card>
          <Card.Content>
             <Card.Header>
               Nombre
             </Card.Header>
             <Card.Meta>
               Telefono
             </Card.Meta>
             <Card.Description>
               Dirección
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
      </Card.Group>
      <ClienteModal></ClienteModal>>
    );
  }
}
