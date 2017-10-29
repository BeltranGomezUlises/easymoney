import React from 'react';
import ReactDOM from 'react-dom';
import ClienteCard from './Componentes/ClienteCard.jsx'
import { Container, Segment, Card} from 'semantic-ui-react';

export default class Clientes extends React.Component{

  constructor(props) {
    super(props)
    this.state = { clientes: [] }
  }

  componentWillMount() {
    this.cargarClientes();
  }

  cargarClientes(){
    fetch('http://localhost:8084/EasyMoney/api/accesos/login', {
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
      fetch('http://localhost:8084/EasyMoney/api/clientes',{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        this.setState({clientes:response.data});
      })
     })
  }

  renderClientes(){
    return(
     <Card.Group>
       {this.state.clientes.map((c) =>{
         return (
           <ClienteCard key={c.id} nombre={c.nombre} direccion={c.direccion} telefono={c.telefono}>
           </ClienteCard>
         )
       })}
      </Card.Group>
    )
  }

  render(){
    const Clientes = [
      1,
      2,
    ].map((i) => <ClienteCard key={i}/>);
    return(
      <div style={{'padding':'10px'}}>

          <Segment textAlign='center'>
            <h2>CLIENTES</h2>

          </Segment>
          {this.renderClientes()}
      </div>
    );
  }
}
