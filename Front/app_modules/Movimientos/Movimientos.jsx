import React from 'react';
import ReactDOM from 'react-dom';
import { Segment, Modal, Button, Header } from 'semantic-ui-react';
import MovimientoList from './Componentes/MovimientoList.jsx'
import MovimientoForm from './Componentes/MovimientoForm.jsx'

export default class Movimientos extends React.Component{

  constructor(props) {
    super(props)
    this.state={
        modalOpenAgregar: false,
        modalOpenWarning: false,
        nuevoMovimiento:{}
    }
    this.handleCloseAgregar = this.handleCloseAgregar.bind(this);
    this.handleOpenAgregar = this.handleOpenAgregar.bind(this);
    this.handleCloseWarning = this.handleCloseWarning.bind(this);
    this.handleOpenWarning = this.handleOpenWarning.bind(this);
    this.agregarMovimiento = this.agregarMovimiento.bind(this);
    this.onCreateHandler = this.onCreateHandler.bind(this);
  }

  handleCloseAgregar(){
    this.setState({modalOpenAgregar: false});
  }

  handleOpenAgregar(){
    this.setState({modalOpenAgregar: true});
  }

  handleCloseWarning(){
    this.setState({modalOpenWarning: false});
  }

  handleOpenWarning(){
    this.setState({modalOpenWarning: true});
  }

  agregarMovimiento(){
    let {nuevoMovimiento} = this.state.nuevoMovimiento;
    if (nuevoMovimiento.cantidad !== '') {
        fetch(localStorage.getItem('url') + 'movimientos',{
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body:JSON.stringify({nuevoMovimiento})
        }).then((res)=> res.json())
        .then((response) =>{
          this.handleCloseAgregar();
        })
    }else{
      this.handleOpenWarning();
    }
  }

  onCreateHandler(nuevoMovimiento){
    this.setState({nuevoMovimiento});
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
          <Segment textAlign='center'>
            <h2>Movimientos Ingresos y Egresos</h2>
          </Segment>
          <Segment>
            <Modal trigger={<Button color='green' onClick={this.handleOpenAgregar}>Agregar</Button>}
              onClose={this.handleCloseAgregar}
              open={this.state.modalOpenAgregar}>
              <Header content='Agregar Ingreso o Egreso' />
              <Modal.Content>
                <MovimientoForm getData={this.onCreateHandler} agregarMovimiento={this.agregarMovimiento}>
                </MovimientoForm>
              </Modal.Content>
            </Modal>
          </Segment>
      </div>
    );
  }
}
