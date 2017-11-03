import React from 'react';
import ReactDOM from 'react-dom';
import CobradorCard from './Componentes/CobradorCard.jsx';
import CobradorForm from './Componentes/CobradorForm.jsx';
import { Segment, Card, Button, Image, Modal, Header, Dimmer, Loader} from 'semantic-ui-react';

export default class Cobradores extends React.Component{

  constructor(props) {
    super(props)
    this.state = {
      cobradores: [],
      modalOpenAgregar: false,
      modalOpenWarning: false,
      nuevoCobrador: {}
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
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body:JSON.stringify({
            nombre: this.state.nuevoCobrador.nombre,
            direccion: this.state.nuevoCobrador.direccion
        })
      }).then((res)=> res.json())
      .then((response) =>{
        console.log(response);
        //agregar nuevocobrador a la lista actual
        let cobradores = [...this.state.cobradores, response.data];
        //limpiar nuevo cobrador
        this.setState({
          cobradores,
          nuevocobrador:{}
        });
        this.handleCloseAgregar();
      })
     })
   }else{
     console.log('');
     this.setState({modalOpenWarning:true});
   }
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

  renderCobradoresCards(){
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
          <Image src='/assets/images/descargandoCobradores.png'/>
        </div>
      )
    }
  }

  renderCobradores(){
    return(
      <Segment>
          <Modal
            trigger={<Button color='green' style={{ 'margin-bottom': '15px'}} onClick={this.handleOpenAgregar}>Agregar</Button>}
            onClose={this.handleCloseAgregar}
            open={this.state.modalOpenAgregar}>
            <Header content='Agregar cobrador' />
            <Modal.Content>
              <CobradorForm getData={this.onCreateHandler}></CobradorForm>
            </Modal.Content>
            <Modal.Actions>
              <Button color='green' onClick={this.agregarCobrador}>
                Guardar
              </Button>
              <Button color='red' onClick={this.handleCloseAgregar}>
                Cancelar
              </Button>
            </Modal.Actions>
          </Modal>
         <Card.Group>
           {this.renderCobradoresCards()}
          </Card.Group>
      </Segment>
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
          <Modal open={this.state.modalOpenWarning} onClose={this.handleCloseWarning} closeOnRootNodeClick={false}>
            <Modal.Content>
              <h3>Es necesario llenar el nombre del cobrador</h3>
            </Modal.Content>
            <Modal.Actions>
              <Button color='green' onClick={this.handleCloseWarning} inverted> Entendido </Button>
            </Modal.Actions>
          </Modal>
            {this.renderCobradores()}
      </div>
    );
  }

}
