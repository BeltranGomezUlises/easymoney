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
    this.updateInputContraDefault = this.updateInputContraDefault.bind(this);
    this.updateInputCantidadMultaMes = this.updateInputCantidadMultaMes.bind(this);
    this.updateInputCantidadMultaDiaria = this.updateInputCantidadMultaDiaria.bind(this);
    this.handleSumbit = this.handleSumbit.bind(this);
  }

  componentWillMount(){
    this.cargarConfigs();
  }

  handleSumbit(){
    this.actualizarConfigs();
  }

  updateInputDiasPrestamo(evt){
      const diasPrestamo = evt.target.value;
      let {configuraciones} = this.state;
      configuraciones.diasPrestamo = diasPrestamo;
      this.setState({configuraciones})
  }

  updateInputPorcentajeInteresPrestamo(evt){
      const porcentajeInteresPrestamo = evt.target.value;
      let {configuraciones} = this.state;
      configuraciones.porcentajeInteresPrestamo = porcentajeInteresPrestamo;
      this.setState({configuraciones})
  }

  updateInputContraDefault(evt){
      const contraDefault = evt.target.value;
      let {configuraciones} = this.state;
      configuraciones.contraDefault = contraDefault;
      this.setState({configuraciones});
  }

  updateInputCantidadMultaDiaria(evt){
    const cantidadMultaDiaria = evt.target.value;
    let {configuraciones} = this.state;
    configuraciones.cantidadMultaDiaria = cantidadMultaDiaria;
    this.setState({configuraciones});
  }

  updateInputCantidadMultaMes(evt){
    const cantidadMultaMes = evt.target.value;
    let {configuraciones} = this.state;
    configuraciones.cantidadMultaMes = cantidadMultaMes;
    this.setState({configuraciones});
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
      body:JSON.stringify(this.state.configuraciones)
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

  renderButton(){
    if (this.state.loading) {
      return <Button primary loading>Actualizar</Button>
    }else{
      return <Button primary type='sumbit'>Actualizar</Button>
    }
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
                  <input type='number' min='1' step='1' max='99'
                    required
                    placeholder='Ingrese los días de plazo que se le asignarán a los prestamos'
                    value={this.state.configuraciones.diasPrestamo}
                    onInput={this.updateInputDiasPrestamo} />
                </Form.Field>
                <Form.Field>
                  <label>Porcentaje de impuesto:</label>
                  <input type='number' min='1' step='1' max='1000'
                    required
                    placeholder='Ingrese el porcentaje de impuesto que se le asinarán a los prestamos'
                    value={this.state.configuraciones.porcentajeInteresPrestamo}
                    onInput={this.updateInputPorcentajeInteresPrestamo}/>
                </Form.Field>
                {
                  //cantidades de las multas, utilizadas por el movil para generar los cobros con el negocio de las multas
                }
                <Form.Field>
                  <label>Cantidad de multa diaria pago tardío:</label>
                  <input type='number' min='1' step='1' max='1000'
                    required
                    placeholder='Ingrese la cantidad de dinero a cobrar por día de retraso al cliente en sus pagos'
                    value={this.state.configuraciones.cantidadMultaDiaria}
                    onInput={this.updateInputCantidadMultaDiaria}/>
                </Form.Field>
                <Form.Field>
                  <label>Cantidad de multa diaria de pago tardío depues del plazo de pago:</label>
                  <input type='number' min='1' step='1' max='1000'
                    required
                    placeholder='Ingrese la cantidad de dinero a cobrar por día de retraso al cliente en sus pagos, cuando exceda el plazo de pago'
                    value={this.state.configuraciones.cantidadMultaMes}
                    onInput={this.updateInputCantidadMultaMes}/>
                </Form.Field>
                {
                  //contraseña default con la que se crean los cobradores, usuarios de la aplicacion movil
                }
                <Form.Field>
                  <label>Contraseña default cobrador:</label>
                  <input type='text'
                    required
                    placeholder='Ingrese la contraseña default para los usuarios cobradores'
                    value={this.state.configuraciones.contraDefault}
                    onInput={this.updateInputContraDefault} />
                </Form.Field>
                {this.renderButton()}
              </Form>
          </Segment>
        </Container>
      </div>
    );
  }

}
