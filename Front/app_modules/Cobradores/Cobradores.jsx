import React from 'react';
import ReactDOM from 'react-dom';
import CobradorCard from './Componentes/CobradorCard.jsx';
import CobradorForm from './Componentes/CobradorForm.jsx';
import { Segment, Container, Divider, Form, Input, Card, Button, Image, Modal, Header, Dimmer, Loader} from 'semantic-ui-react';
import * as utils from '../../utils.js';

export default class Cobradores extends React.Component{

  constructor(props) {
    super(props)
    this.state = {
      cobradores: [],
      cobradoresRes:[],
      modalOpenAgregar: false,
      modalOpenWarning: false,
      nuevoCobrador: {},
      conCobradores: true,
      filtro: {
        nombre: ''
      }
     }

    this.removeCobrador = this.removeCobrador.bind(this);
    this.handleCloseAgregar = this.handleCloseAgregar.bind(this);
    this.handleOpenAgregar = this.handleOpenAgregar.bind(this);
    this.onCreateHandler = this.onCreateHandler.bind(this);
    this.agregarCobrador = this.agregarCobrador.bind(this);
    this.handleCloseWarning = this.handleCloseWarning.bind(this);
  }

  handleCloseWarning(){
    this.setState({modalOpenWarning: false});
  }

  handleCloseAgregar(){
    this.setState({modalOpenAgregar: false});
  }

  handleOpenAgregar(){
    this.setState({modalOpenAgregar: true});
  }

  onCreateHandler(nuevoCobrador){
    this.setState({nuevoCobrador});
  }

  agregarCobrador(){
    if (this.state.nuevoCobrador.nombre){
      fetch(localStorage.getItem('url') + 'usuarios',{
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body:JSON.stringify({
            nombre: this.state.nuevoCobrador.nombre,
            nombreCompleto: this.state.nuevoCobrador.nombreCompleto
        })
      }).then((res)=> res.json())
      .then((response) =>{
        utils.evalResponse(response, () => {
          //agregar nuevocobrador a la lista actual
          let cobradores = [...this.state.cobradores, response.data];
          //limpiar nuevo cobrador
          this.setState({
            cobradores,
            nuevocobrador:{},
            conCobradores: true
          });
        });
        this.handleCloseAgregar();
      })
   }else{
     this.setState({modalOpenWarning:true});
   }
  }

  cargarCobradores(){
      fetch(localStorage.getItem('url') + 'usuarios/usuariosCobradores',{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        if (response.data.length > 0) {
            this.setState({
              cobradores:response.data,
              cobradoresRes:response.data,
              conCobradores: true
            });
        }else{
          this.setState({conCobradores:false});
        }
      })
  }

  filtrar(){
    const {nombre} = this.state.filtro;
    let filtrado = this.state.cobradoresRes.filter( (cobrador) => cobrador.nombre.toLowerCase().includes(nombre.toLowerCase()));
    this.setState({
      cobradores: filtrado
    })
  }

  renderCobradoresCards(){
    if (this.state.conCobradores) {
      if (this.state.cobradores.length > 0) {
        return(
          this.state.cobradores.map((cobrador) =>{
            return (
              <CobradorCard key={cobrador.id} cobrador={cobrador} removeCobrador={this.removeCobrador}>
              </CobradorCard>
            )
          })
        )
      }else{
        return (
          <div>
            <Dimmer active inverted>
              <Loader size='large'>Descargando...</Loader>
            </Dimmer>
            <Image src='assets/images/descargandoCobradores.png'/>
          </div>
        )
      }
    }else{
      return(
        <Container textAlign='center'>
            <h2>Sin Cobradores...</h2>
        </Container>
      );
    }
  }

  renderFiltros(){
    return (
      <Form>
        <Form.Group>
          <Form.Field
             control={Input}
             label='Nombre de usuario de cobrador:'
             type='text'
             placeholder='Nombre de cobrador'
             value={this.state.filtro.nombre}
             onChange={ (evt) => {
               let {filtro} = this.state;
               filtro.nombre = evt.target.value;
               this.setState({filtro});
               this.filtrar();
             }}
          />
        </Form.Group>
        <Modal
          trigger={<Button color='green' onClick={this.handleOpenAgregar}>Agregar</Button>}
          onClose={this.handleCloseAgregar}
          open={this.state.modalOpenAgregar}>
          <Header content='Agregar cobrador' />
          <Modal.Content>
            <CobradorForm getData={this.onCreateHandler} agregarCobrador={this.agregarCobrador}></CobradorForm>
          </Modal.Content>
        </Modal>
      </Form>
    );
  }

  renderCobradores(){
    return(
      <div>
        <Segment>
          <Divider horizontal>Filtros</Divider>
          {this.renderFiltros()}
        </Segment>
        <Segment>
           <Card.Group>
             {this.renderCobradoresCards()}
            </Card.Group>
        </Segment>
      </div>
    )
  }

  removeCobrador(cobrador){
    const cobradoresViejos = this.state.cobradores.filter((c)=>{
      return c.id != cobrador.id
    });
    this.setState({cobradores: cobradoresViejos});
  }

  componentWillMount() {
    this.cargarCobradores();
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
