import React, {Component} from 'react';
import { Label, Loader, Table} from 'semantic-ui-react';
import * as utils from '../../../utils.js';

export default class DetalleCobros extends Component{

    constructor(props){
      super(props);
      this.state = {
        cobros : [],
        buscando: true
      }
    }

    componentDidMount(){
      this.cargarDetalleCobros();
    }

    cargarDetalleCobros(){
      var date = new Date();
      var actualDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());      
      let fechaInicial = actualDate.getTime() + (actualDate.getTimezoneOffset() * 60000);
      let fechaFinal = fechaInicial + 86400000;
      let cobradorId = this.props.cobrador.id;
      this.setState({buscando:true});
      fetch(localStorage.getItem('url') + 'cobros/cobrador',{
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        },
        body: JSON.stringify({
          agrupadorId: cobradorId,
          fechaInicial,
          fechaFinal
        })
      }).then((res)=> res.json())
      .then((response) =>{
        utils.evalResponse(response, () => {
          this.setState({
            cobros: response.data,
            buscando:false
          });
        });
      })
    }

    renderCobroList(){
      return (this.state.cobros.map((cobro) => {
        return (
          <Table.Row key={cobro.cobroid}>
            <Table.Cell>
              {cobro.cliente}
            </Table.Cell>
            <Table.Cell textAlign='right'>
                ${cobro.cantidad}
            </Table.Cell>
            <Table.Cell>
                {new Date(cobro.fecha).toLocaleString()}
            </Table.Cell>
          </Table.Row>
        )
      }))
    }

    renderTotalCantidad(){
      var sumatoria = 0;
      this.state.cobros.forEach( c => {
        sumatoria += c.cantidad;
      });
      return(
            <Table.HeaderCell textAlign='right'>${sumatoria}</Table.HeaderCell>
      )
    }

    renderCobros(){
      if (this.state.cobros.length > 0) {
        return(
          <Table>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Cliente</Table.HeaderCell>
                <Table.HeaderCell>Cobrado</Table.HeaderCell>
                <Table.HeaderCell>Fecha/Hora</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              {this.renderCobroList()}
            </Table.Body>
            <Table.Footer>
              <Table.HeaderCell>TOTAL COBRADO HOY</Table.HeaderCell>
              {this.renderTotalCantidad()}
              <Table.HeaderCell></Table.HeaderCell>
            </Table.Footer>
          </Table>
        );
      }else{
        return(
          <h3>Sin cobros</h3>
        );
      }
    }

    render(){
        if (this.state.buscando) {
          return(
              <Loader style={{'minHeight':'100px'}} active size='big'>
                Cargando...
              </Loader>
          );
        }else{
          return(
            <div>
              <h4>Cobrador: {this.props.cobrador.nombreCompleto}</h4>
              {this.renderCobros()}
            </div>
          );
        }
    }
}
