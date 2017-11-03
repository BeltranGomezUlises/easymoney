import React, {Component} from 'react';
import { Table, Header, Segment } from 'semantic-ui-react';

export default class PrestamoDetalle extends Component{

    constructor(props){
      super(props);
      this.state={
        prestamo: this.props.prestamo
      }
    }

    renderMulta(abono){
      console.log(abono)
      if (abono.multa !== undefined) {
        return <Table.Cell textAlign='center'>{abono.multa.cantidad}</Table.Cell>
      }else{
        return <Table.Cell textAlign='center'>$0</Table.Cell>
      }
    }

    renderAbonos(){
      let {prestamo} = this.state;
      if (prestamo.abonos.length > 0) {
        return prestamo.abonos.map((abono)=>{
          return(
            <Table.Row compact>
              <Table.Cell>{new Date(abono.abonoPK.fecha).toLocaleDateString()}</Table.Cell>
              <Table.Cell textAlign='center'>${abono.cantidad}</Table.Cell>
              {this.renderMulta(abono)}
            </Table.Row>
          )
        })
      }
    }

    render(){
      let {prestamo} = this.state;
      return(
        <Segment>
          <Table celled>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Cliente</Table.HeaderCell>
                <Table.HeaderCell>Cobrador</Table.HeaderCell>
                <Table.HeaderCell textAlign='right'>Cantidad</Table.HeaderCell>
                <Table.HeaderCell textAlign='right'>Cantidad a Pagar</Table.HeaderCell>
                <Table.HeaderCell>Fecha/Hora Prestamo</Table.HeaderCell>
                <Table.HeaderCell>Fecha Limite</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              <Table.Cell>{prestamo.cliente.nombre}</Table.Cell>
              <Table.Cell>{prestamo.cobrador.nombre}</Table.Cell>
              <Table.Cell textAlign='right'>${prestamo.cantidad}</Table.Cell>
              <Table.Cell textAlign='right'>${prestamo.cantidadPagar}</Table.Cell>
              <Table.Cell>{new Date(prestamo.fecha).toLocaleString()}</Table.Cell>
              <Table.Cell>{new Date(prestamo.fechaLimite).toLocaleDateString()}</Table.Cell>
            </Table.Body>
          </Table>
          <Table celled>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Fecha</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Cantidad</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Multa</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              {this.renderAbonos()}
            </Table.Body>
          </Table>
        </Segment>
      );
    }
}
