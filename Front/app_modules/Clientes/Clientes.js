import React from 'react';
import ReactDOM from 'react-dom';
import ClienteCard from './Componentes/ClienteCard.jsx'
import { Container, Segment} from 'semantic-ui-react';

export default class Clientes extends React.Component{
  render(){
    return(
      <div>
        <Container>
          <Segment textAlign='center'><h2>CLIENTES</h2></Segment>

          <ClienteCard></ClienteCard>
        </Container>
      </div>
    );
  }
}
