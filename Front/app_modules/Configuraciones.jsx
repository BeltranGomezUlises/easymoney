import React from 'react';
import ReactDOM from 'react-dom';
import { Container, Segment, Form, Button} from 'semantic-ui-react';

export default class Clientes extends React.Component{

  constructor(props) {
    super(props)
    this.state = {
      configuraciones:{}
    }
  }

  actualizarConfigs(){
      fetch(localStorage.getItem('url') + 'configs',{
        method: 'PUT',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        },
        body:JSON.stringify({
          diasPrestamo: this.state.configuraciones.diasPrestamo,
          porcentajeInteresPrestamo: this.state.configuraciones.porcentajeInteresPrestamo
        })
      }).then((res)=> res.json())
      .then((response) =>{
        console.log(response);
      })
  }

  cargarConfigs(){
      fetch(localStorage.getItem('url') + 'configs',{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        console.log(response);
      })
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
          <Segment textAlign='center'>
            <h2>CONFIGURACIONES</h2>
          </Segment>
          <Form onSubmit={this.handleSumbit}>
            <Form.Field>
              <label>Días de los prestamos:</label>
              <input type='text' placeholder='ingrese los días de plazo que se le asignarán a los prestamos' onChange={this.handleDiasChange} />
            </Form.Field>
            <Form.Field>
              <label>Porcentaje de impuesto:</label>
              <input type='text' placeholder='ingrese el porcentaje de impuesto que se le asinarán a los prestamos' onChange={this.handlePorcentajeChange}/>
            </Form.Field>
            <Button primary type='sumbit'>Actualizar</Button>
          </Form>
      </div>
    );
  }

}
