import React from 'react';
import ReactDOM from 'react-dom';
import { Button, Segment, Divider, Header, Input, Form, Container} from 'semantic-ui-react'

export default class Login extends React.Component{

  constructor(props){
    super(props);

    this.state = {
      user: '',
      pass: '',
      message: '',
      loading:false
    }

    localStorage.setItem('tokenSesion', '');
    this.handleSumbit = this.handleSumbit.bind(this);
    this.handleUserChange = this.handleUserChange.bind(this);
    this.handlePassChange = this.handlePassChange.bind(this);

    let ruta = window.location.href.split('#');
    window.location.href = ruta[0] + '#/login';
  }

  handleSumbit(){
    this.setState({loading:true});
    fetch(localStorage.getItem('url') + 'accesos/login', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        user: this.state.user,
        pass: this.state.pass,
      })
    }).then((res) => res.json())
    .then((response) => {
      if (response.meta.status == 'OK') {
        if (response.data.usuario.tipo === true) {
          localStorage.setItem('tokenSesion', response.meta.metaData);
          let ruta = window.location.href.split('#');
          window.location.href = ruta[0] + '#/prestamos';
        } else{
          this.setState({message: 'No tiene permisos de entrar al sistema de administración', loading: false});
        }
      }else{
        if (response.meta.status == 'WARNING') {
          this.setState({message: response.meta.message, loading: false});
        }
      }
    })
  }

  handleUserChange(evt){
    this.setState({user: evt.target.value});
  }

  handlePassChange(evt){
    this.setState({pass: evt.target.value});
  }

  renderMessage(){
    if (this.state.message !== '') {
      return (
        <Segment color='yellow'>
          {this.state.message}
        </Segment>
      )
    }
  }

  renderButton(){
    if (this.state.loading) {
      return(
          <Button loading primary type='sumbit'>Iniciar Sesion</Button>
      );
    }else{
      return(
        <Button primary type='sumbit'>Iniciar Sesion</Button>
      );
    }
  }

  render(){
    return(
      <div>
        <Segment textAlign='center'>
            <h1>Easy Money</h1>
        </Segment>
        <Container text>
          <Segment>
            <Header color='blue'>Iniciar sesion</Header>
            <Divider section/>
              {this.renderMessage()}
              <Form onSubmit={this.handleSumbit}>
                <Form.Field>
                  <label>Usuario:</label>
                  <input type='text' placeholder='Ingrese el usuario...:'onChange={this.handleUserChange} />
                </Form.Field>
                <Form.Field>
                  <label>Contraseña:</label>
                  <input type='password' placeholder='Ingrese la contraseña...' onChange={this.handlePassChange}/>
                </Form.Field>
                {this.renderButton()}
              </Form>
          </Segment>
        </Container>
      </div>
    )
  }
}
