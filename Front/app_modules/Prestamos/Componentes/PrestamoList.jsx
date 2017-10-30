import React from 'react';
import ReactDOM from 'react-dom';
import { Header, Table, Rating } from 'semantic-ui-react'

export default class PrestamoList extends React.Component {

  constructor(props){
    super(props);
    this.state = { prestamos: [] }
  }

  componentWillMount(){
    this.cargarPrestamos();
  }

  cargarPrestamos(){
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
      fetch(localStorage.getItem('url') + 'prestamos',{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        this.setState({prestamos:response.data});
      })
     })
  }

  renderPrestamos(){
    return(
        this.state.prestamos.map((prestamo) =>{
          return(
            <Table.Row key={prestamo.id}>
              <Table.Cell>
                <Header as='h3' textAlign='center'>
                  {prestamo.id}
                </Header>
              </Table.Cell>
              <Table.Cell singleLine>
                {prestamo.clienteId}
              </Table.Cell>
              <Table.Cell textAlign='right'>
                ${prestamo.cantidad}
              </Table.Cell>
              <Table.Cell textAlign='right'>
                  ${prestamo.cantidadPagar}
              </Table.Cell>
              <Table.Cell>
                  {new Date(prestamo.fecha).toLocaleString()}
              </Table.Cell>
              <Table.Cell>
                  {new Date(prestamo.fechaLimite).toLocaleDateString()}
              </Table.Cell>
              <Table.Cell>
                  30%
              </Table.Cell>
            </Table.Row>
          )
        })
    )
  }

  render() {
    return (
      <Table celled padded>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell>Id</Table.HeaderCell>
            <Table.HeaderCell>Cliente</Table.HeaderCell>
            <Table.HeaderCell>Cantidad</Table.HeaderCell>
            <Table.HeaderCell>Cantidad a Pagar</Table.HeaderCell>
            <Table.HeaderCell>Fecha/Hora Prestamo</Table.HeaderCell>
            <Table.HeaderCell>Fecha Limite</Table.HeaderCell>
            <Table.HeaderCell>Porcentaje Pagado</Table.HeaderCell>
          </Table.Row>
        </Table.Header>

        <Table.Body>
          {this.renderPrestamos()}
        </Table.Body>
      </Table>
    )
  }
}
