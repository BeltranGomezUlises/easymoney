import React from 'react';
import ReactDOM from 'react-dom';
import ClienteCard from './Componentes/ClienteCard.jsx'
import { Container, Segment} from 'semantic-ui-react';

export default class Clientes extends React.Component{
  constructor(props){
    super(props);
    this.state = {};
  }
  render(){
    const Clientes = [
      1,
      2,
    ].map((i) => <ClienteCard key={i}/>);
    return(
      <div>
        <Container>
          <Segment textAlign='center'><h2>CLIENTES</h2></Segment>
          {Clientes}
        </Container>
      </div>
    );
  }
}
