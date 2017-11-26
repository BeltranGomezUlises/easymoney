import React from 'react';
import ReactDOM from 'react-dom';
import { Header, Table, Dimmer, Loader, Segment, Container, Modal, Button} from 'semantic-ui-react'

export default class MovimientoList extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      Movimientos: [],
      conMovimientos:true,
      nuevoMovimiento:{
        cantidad:0,
        cliente:{
          id:0
        },
        cobrador:{
          id:0
        }
      }
    }
  }

  componentWillMount(){
    this.cargarMovimientos();
  }



  cargarMovimientos(){
      fetch(localStorage.getItem('url') + 'ingresosegresos',{
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
          this.setState({Movimientos:response.data, conMovimiento:true});
        }else{
          this.setState({conMovimiento: false});
        }
      })
  }

  renderMovimientoList(){
    return this.state.Movimientos.map((ie) =>{
      return(
        <Table.Row key={ie.id}>
          <Table.Cell style={{cursor: 'pointer'}}>
            <Header textAlign='center'>
              {ie.id}
            </Header>
          </Table.Cell>
          <Table.Cell>
            {ie.descripcion}
          </Table.Cell>
          <Table.Cell>
            {ie.cantidad}
          </Table.Cell>
        </Table.Row>
      )
    })
  }

  renderMovimientos(){
    if (this.state.conMovimientos) {
      if (this.state.Movimientos.length > 0) {
        return(
          <Table celled selectable>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell textAlign='center'>Id</Table.HeaderCell>
              <Table.HeaderCell>$ cantidad</Table.HeaderCell>
              <Table.HeaderCell>descripcion</Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {this.renderMovimientoList()}
          </Table.Body>
        </Table>
        )
      }else{
          return(
            <div>
              <Dimmer active inverted>
                <Loader size='large'>Descargando...</Loader>
              </Dimmer>
            </div>
          )
      }
    }else{
      return(
        <Container textAlign='center'>
            <h2>Sin Prestamos...</h2>
        </Container>
      );
    }
  }

  render() {
    return (
        <Segment>
          {this.renderMovimientos()}
        </Segment>
    )
  }
}
