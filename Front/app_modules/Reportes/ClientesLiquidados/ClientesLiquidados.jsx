import React from 'react';
import ReactDOM from 'react-dom';
import { Segment, Dimmer, Loader } from 'semantic-ui-react';
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
    console.log("hola")
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
              <Table.Row>
                <Table.Cell>c1</Table.Cell>
                <Table.Cell>c2</Table.Cell>
                <Table.Cell>c3</Table.Cell>
                <Table.Cell>c4</Table.Cell>
                <Table.Cell>c4</Table.Cell>
              </Table.Row>
            </Table.Body>
          </Table>
        );
    }else{
      return(
        <h2>Sin clientes liquidados</h2>
      )
    }
  }

  renderContent(){
    if (this.state.buscando) {
      return(
          <Segment style={{'height':'350px'}}>
              <Dimmer active inverted>
                <Loader size='large'>Descargando...</Loader>
              </Dimmer>
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
