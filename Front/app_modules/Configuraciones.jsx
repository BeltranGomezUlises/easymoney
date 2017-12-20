import React from 'react';
import ReactDOM from 'react-dom';
import { Container, Segment, Form, Button} from 'semantic-ui-react';
import {notify} from 'react-notify-toast';

export default class Clientes extends React.Component{

  constructor(props) {
    super(props)
    this.state = {
      configuraciones:{},
      loading: false
    }

    this.updateInputDiasPrestamo = this.updateInputDiasPrestamo.bind(this);
    this.updateInputPorcentajeInteresPrestamo = this.updateInputPorcentajeInteresPrestamo.bind(this);
    this.handleSumbit = this.handleSumbit.bind(this);
  }

  componentWillMount(){
    this.cargarConfigs();
  }

  handleSumbit(){
    this.actualizarConfigs();
  }

  updateInputDiasPrestamo(evt){
    if (evt.target.value.length <= 2) {
      const diasPrestamo = (evt.target.validity.valid) ? evt.target.value : this.state.configuraciones.diasPrestamo;
      let {configuraciones} = this.state;
      configuraciones.diasPrestamo = diasPrestamo;
      this.setState({configuraciones})
    }
  }

  updateInputPorcentajeInteresPrestamo(evt){
    if (evt.target.value.length <= 2) {
      const porcentajeInteresPrestamo = (evt.target.validity.valid) ? evt.target.value : this.state.configuraciones.porcentajeInteresPrestamo;
      let {configuraciones} = this.state;
      configuraciones.porcentajeInteresPrestamo = porcentajeInteresPrestamo;
      this.setState({configuraciones})
    }
  }

  actualizarConfigs(){
    this.setState({loading: true});

    fetch(localStorage.getItem('url') + 'configs',{
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin':'*',
        'Authorization': localStorage.getItem('tokenSesion')
      },
      body:JSON.stringify({
        id: this.state.configuraciones.id,
        diasPrestamo: this.state.configuraciones.diasPrestamo,
        porcentajeInteresPrestamo: this.state.configuraciones.porcentajeInteresPrestamo
      })
    }).then((res)=> res.json())
    .then((response) =>{
      this.setState({loading: false});
      notify.show('Configuraciones actualizadas', 'success', 3500);
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
        this.setState({configuraciones:response});
      })
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
        <Segment textAlign='center'>
          <h2>CONFIGURACIONES</h2>
        </Segment>
          <Container>
            <Segment>
              <Form onSubmit={this.handleSumbit}>
                <Form.Field>
                  <label>Días de los prestamos:</label>
                  <input type='text' pattern="[0-9]*" 
                    placeholder='ingrese los días de plazo que se le asignarán a los prestamos' 
                    value={this.state.configuraciones.diasPrestamo} 
                    onInput={this.updateInputDiasPrestamo} />
                </Form.Field>
                <Form.Field>
                  <label>Porcentaje de impuesto:</label>
                  <input type='text' pattern="[0-9]*" 
                    placeholder='ingrese el porcentaje de impuesto que se le asinarán a los prestamos' 
                    value={this.state.configuraciones.porcentajeInteresPrestamo} 
                    onInput={this.updateInputPorcentajeInteresPrestamo}/>
                </Form.Field>
                <Button primary type='sumbit'>Actualizar</Button>
              </Form>
          </Segment>
        </Container>
      </div>
    );
  }

}
