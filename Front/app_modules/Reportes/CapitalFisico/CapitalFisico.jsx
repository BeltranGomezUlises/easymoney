import React from 'react';
import ReactDOM from 'react-dom';
import { Segment, Form, Button, Table} from 'semantic-ui-react';
import * as utils from '../../../utils.js';

export default class CapitalFisico extends React.Component{

  constructor(props) {
    super(props)
    this.state = {
      filtro:{
        fechaInicial:'',
        fechaFinal:''
      },
      reporte:{
        totalRecuperado:0,
        totalMovsIngreso:0,
        totalMovsEgreso:0,
        capital:0
      }
    }
    this.handleSumbit = this.handleSumbit.bind(this);
  }

  handleSumbit(){
    this.setState({buscando:true})
    let {filtro} = this.state;

    let fechaInicial = utils.toUtcDate(filtro.fechaInicial);
    let fechaFinal = utils.toUtcDate(filtro.fechaFinal) + 86400000;


    fetch(localStorage.getItem('url') + 'reportes/capital/' + fechaInicial + '/' + fechaFinal,{
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
        this.setState({reporte: response.data})
      });
    })

  }

  renderButtonBuscar(){
    if (this.state.buscando) {
      return(
        <Button primary loading>Buscar</Button>
      );
    }else{
      return(
        <Button primary type='submit'>Buscar</Button>
      );
    }
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
          <Segment textAlign='center'>
            <h2>REPORTE DE CAPITAL FÍSICO</h2>
          </Segment>
          {/*filtros*/}
          <Segment>
            <Form onSubmit={this.handleSumbit}>
              <Form.Group>
                {/*fechas*/}
                <Form.Field>
                  <label>Fecha inicial:</label>
                  <input
                  required
                    type={'date'}
                    value={this.state.filtro.fechaInicial}
                    onChange={(evt) => {
                      let {filtro} = this.state;
                      filtro.fechaInicial = evt.target.value;
                      this.setState({filtro});
                    }}/>
                </Form.Field>
                <Form.Field>
                  <label>Fecha final:</label>
                  <input
                  required
                    type={'date'}
                    value={this.state.filtro.fechaFinal}
                    onChange={(evt) => {
                      let {filtro} = this.state;
                      filtro.fechaFinal = evt.target.value;
                      this.setState({filtro});
                    }}/>
                </Form.Field>
              </Form.Group>
              <Form.Field>
                {this.renderButtonBuscar()}
              </Form.Field>
            </Form>

          </Segment>
          {/*tabla de reporte*/}
          <Table celled>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell textAlign='center'>Total recuperado</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Total movs. ingreso</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Total movs. egreso</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Capital</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              <Table.Row>
                <Table.Cell textAlign='right'>${this.state.reporte.totalRecuperado}</Table.Cell>
                <Table.Cell textAlign='right'>${this.state.reporte.totalMovsIngreso}</Table.Cell>
                <Table.Cell textAlign='right'>${this.state.reporte.totalMovsEgreso}</Table.Cell>
                <Table.Cell textAlign='right'>${this.state.reporte.capital}</Table.Cell>
              </Table.Row>
            </Table.Body>
          </Table>
      </div>
    );
  }
}
