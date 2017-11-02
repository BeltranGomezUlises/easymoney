import React from 'react';
import ReactDOM from 'react-dom';
import CobradorCard from './Componentes/CobradorCard.jsx'
import { Container, Segment, Card} from 'semantic-ui-react';

export default class Cobradores extends React.Component{

  constructor(props) {
    super(props)
    this.state = { cobradores: [] }
  }

  componentWillMount() {
    this.cargarCobradores();
  }

  cargarCobradores(){
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
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        this.setState({cobradores:response.data});
      })
     })
  }

  renderCobradores(){
    return(
     <Card.Group>
       {this.state.cobradores.map((c) =>{
         return (
           <CobradorCard key={c.id} nombre={c.nombre} direccion={c.direccion} id={c.id}>
           </CobradorCard>
         )
       })}
      </Card.Group>
    )
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
          <Segment textAlign='center'>
            <h2>COBRADORES</h2>
          </Segment>
            {this.renderCobradores()}
      </div>
    );
  }

}
