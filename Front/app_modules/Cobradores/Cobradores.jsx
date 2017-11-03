import React from 'react';
import ReactDOM from 'react-dom';
import CobradorCard from './Componentes/CobradorCard.jsx'
import { Container, Segment, Card, Button, Modal, Header} from 'semantic-ui-react';

export default class Cobradores extends React.Component{

  constructor(props) {
    super(props)
    this.state = {
      cobradores: [],
      modalOpenAgregar: false,
      modalOpenWarning: false
     }

    this.handleCloseAgregar = this.handleCloseAgregar.bind(this);
    this.handleOpenAgregar = this.handleOpenAgregar.bind(this);
    this.onCreateHandler = this.onCreateHandler.bind(this);
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

  onCreateHandler(nuevoCliente){
    this.setState({nuevoCliente});
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
      <Segment>
          <Modal
            trigger={<Button color='green' style={{ 'margin-bottom': '15px'}} onClick={this.handleOpenAgregar}>Agregar</Button>}
            onClose={this.handleCloseAgregar}
            open={this.state.modalOpenAgregar}>
            <Header content='Agregar cobrador' />
            <Modal.Content>
              <CobradorForm ></CobradorForm>
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
           {this.state.cobradores.map((c) =>{
             return (
               <CobradorCard key={c.id} nombre={c.nombre} direccion={c.direccion} id={c.id}>
               </CobradorCard>
             )
           })}
          </Card.Group>
      </Segment>
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
