import React from 'react';
import ClienteModal from './ClienteModal.jsx';
import { Card , Icon, Input, Button} from 'semantic-ui-react';

export default class ClienteCard extends React.Component{

  constructor(props) {
    super(props);
    this.state = {open:false};
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
               <Button basic color='blue'>Editar</Button>
               <Button basic color='red'>Eliminar</Button>
             </div>
          </Card.Content>
        </Card>
    );
  }
}
