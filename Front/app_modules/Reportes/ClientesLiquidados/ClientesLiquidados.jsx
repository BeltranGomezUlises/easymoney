import React from 'react';
import ReactDOM from 'react-dom';
import { Segment, Dimmer, Loader, Table} from 'semantic-ui-react';
import * as utils from '../../../utils.js';


export default class ClientesLiquidados extends React.Component{

  constructor(props) {
    super(props)

    this.state={
      buscando:true,
      clientesLiquidados:[]
    }

  }

  componentWillMount(){
    fetch(localStorage.getItem('url') + 'reportes/clientesLiquidados',{
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin':'*',
        'Authorization': localStorage.getItem('tokenSesion')
      }
    }).then((res)=> res.json())
    .then((response) =>{
      this.setState({buscando:false})
      utils.evalResponse(response, () => {
        this.setState({clientesLiquidados: response.data})
      });
    })
  }

  renderClientesLiquidadosRow(){
    return this.state.clientesLiquidados.map( c => {
      return (
        <Table.Row>
          <Table.Cell>{c.id}</Table.Cell>
          <Table.Cell>{c.nombre}</Table.Cell>
          <Table.Cell>{c.apodo}</Table.Cell>
          <Table.Cell>{c.telefono}</Table.Cell>
          <Table.Cell>{new Date(c.fechaUltimoAbono + (new Date().getTimezoneOffset() * 60000)).toLocaleDateString()}</Table.Cell>
        </Table.Row>
      );
    })
  }

  renderClientesLiquidados(){
    if (this.state.clientesLiquidados.length > 0) {
        return (
          <Table celled>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Id</Table.HeaderCell>
                <Table.HeaderCell>Nombre</Table.HeaderCell>
                <Table.HeaderCell>Apodo</Table.HeaderCell>
                <Table.HeaderCell>Teléfono</Table.HeaderCell>
                <Table.HeaderCell>Fecha último abono</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              {this.renderClientesLiquidadosRow()}
            </Table.Body>
          </Table>
        );
    }else{
      return(
        <h2>Sín clientes liquidados</h2>
      )
    }
  }

  renderContent(){
    if (this.state.buscando) {
      return(
          <Segment style={{'height':'350px'}}>
                <Loader active size='large'>Descargando...</Loader>
          </Segment>
      );
    }else{
      return(
        <Segment>
          {this.renderClientesLiquidados()}
        </Segment>
      );
    }
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
          <Segment textAlign='center'>
            <h2>REPORTE DE CLIENTES LIQUIDADOS</h2>
          </Segment>
          {this.renderContent()}
      </div>
    );
  }
}
