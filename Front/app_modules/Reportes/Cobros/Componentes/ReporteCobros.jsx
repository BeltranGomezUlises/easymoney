import React from 'react';
import { Segment, Divider, Form, Radio, Select, Button, Input, Dropdown, Table} from 'semantic-ui-react';
import * as utils from '../../../../utils.js';

export default class ReporteCobros extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      buscando: false,
      filtro:{
        tipo:'1',
        agrupadorId:0,
        fechaInicial:'',
        fechaFinal:''
      },
      optionsClientes:[],
      optionsUsuarios:[],
      cobros:[]
    }
    this.handleChangeRadio = this.handleChangeRadio.bind(this);
    this.handleChangeInputPrestamo = this.handleChangeInputPrestamo.bind(this);
    this.handleChangeDropDown = this.handleChangeDropDown.bind(this);
  }

  componentWillMount() {
    this.cargarClientes();
    this.cargarUsuarios();
  }

  handleChangeRadio(e, {value}){
    let {filtro} = this.state;
    filtro.tipo = value;
    this.setState({filtro});
  }

  handleChangeDropDown(e, {value}){
    let {filtro} = this.state;
    filtro.agrupadorId = value;
    this.setState({filtro});
  }

  handleChangeInputPrestamo(evt){
    const val = evt.target.value;
    let {filtro} = this.state;
    filtro.agrupadorId = val;
    this.setState({filtro});
  }

  cargarCobros(){
    let {filtro} = this.state;
    let fechaInicial = filtro.fechaInicial !== '' ? utils.toUtcDate(filtro.fechaInicial) : '';
    let fechaFinal = filtro.fechaFinal !== '' ? utils.toUtcDate(filtro.fechaFinal) + 86400000  : '';

    switch (this.state.filtro.tipo) {
      case '1':
        fetch(localStorage.getItem('url') + 'cobros/cliente',{
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body: JSON.stringify({
            agrupadorId: filtro.agrupadorId,
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
        break;
        case '2':
          fetch(localStorage.getItem('url') + 'cobros/cobrador',{
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Access-Control-Allow-Origin':'*',
              'Authorization': localStorage.getItem('tokenSesion')
            },
            body: JSON.stringify({
              agrupadorId: filtro.agrupadorId,
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
          break;
        case '3':
        fetch(localStorage.getItem('url') + 'cobros/prestamo',{
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin':'*',
            'Authorization': localStorage.getItem('tokenSesion')
          },
          body: JSON.stringify({
            agrupadorId: filtro.agrupadorId,
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
          break;
        case '4':
          fetch(localStorage.getItem('url') + 'cobros/rango',{
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Access-Control-Allow-Origin':'*',
              'Authorization': localStorage.getItem('tokenSesion')
            },
            body: JSON.stringify({
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
          break;
    }
  }

  cargarClientes(){
      fetch(localStorage.getItem('url') + 'clientes',{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        utils.evalResponse(response, () => {
          let optionsClientes = [];
          response.data.forEach((c)=>{
            optionsClientes.push({ key: c.id, text: c.nombre, value: c.id});
          })
          this.setState({optionsClientes})
        });
      })
  }

  cargarUsuarios(){
      fetch(localStorage.getItem('url') + 'usuarios',{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        utils.evalResponse(response, () => {
          let optionsUsuarios = [];
          response.data.forEach((c)=>{
            optionsUsuarios.push({ key: c.id, text: c.nombre, value: c.id});
          })
          this.setState({optionsUsuarios})
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

  renderDropDownAgrupador(){
    switch (this.state.filtro.tipo) {
      case '1':
        return(
          <Form.Field>
            <label>Cliente:</label>
            <Dropdown
              search selection
              required
              options={this.state.optionsClientes}
              placeholder='Cliente'
              onChange={this.handleChangeDropDown}
            />
          </Form.Field>

        );
        break;
      case '2':
        return (
          <Form.Field>
          <label>Usuario:</label>
          <Dropdown
            search selection
            required
            options={this.state.optionsUsuarios}
            placeholder='Usuario'
            onChange={this.handleChangeDropDown}
            />
          </Form.Field>
        );
        break;
      case '3':
        return (
          <Form.Field>
            <label>Prestamo identificador:</label>
            <input type='number' min='1' step='1'
              required
              placeholder='Ingrese identificador del prestamo'
              value={this.state.filtro.agrupadorId}
              onInput={this.handleChangeInputPrestamo} />
          </Form.Field>
        );
        break;
      case '4':

        break;
    }
  }

  renderFiltros(){
    return (
      <div>
        <Divider horizontal>Filtros</Divider>
        <Form onSubmit={()=>{
          this.setState({buscando:true})
          this.cargarCobros()
        }}>
          <Form.Group inline>
           <label>Filtrar Por:</label>
           <Form.Field control={Radio} label='Cliente' value='1' checked={this.state.filtro.tipo === '1'} onChange={this.handleChangeRadio} />
           <Form.Field control={Radio} label='Cobrador' value='2' checked={this.state.filtro.tipo === '2'} onChange={this.handleChangeRadio} />
           <Form.Field control={Radio} label='Prestamo' value='3' checked={this.state.filtro.tipo === '3'} onChange={this.handleChangeRadio} />
           <Form.Field control={Radio} label='Nada' value='4' checked={this.state.filtro.tipo === '4'} onChange={this.handleChangeRadio} />
         </Form.Group>
          <Form.Group>
            {/* agrupador */ }
            {this.renderDropDownAgrupador()}
            {/*fechas*/}
            <Form.Field>
              <label>Cobros despues de:</label>
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
              <label>Cobros antes de:</label>
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
      </div>
    );
  }

  renderCobros(){
      return(
        <Table celled selectable striped>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell textAlign='center'>Cobro id</Table.HeaderCell>
              <Table.HeaderCell>Cliente</Table.HeaderCell>
              <Table.HeaderCell>Cobrador</Table.HeaderCell>
              <Table.HeaderCell textAlign='center'>Prestamo id</Table.HeaderCell>
              <Table.HeaderCell textAlign='right'>Cantidad</Table.HeaderCell>
              <Table.HeaderCell>Fecha/Hora Cobro</Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {this.renderCobrosList()}
          </Table.Body>
          <Table.Footer>
            <Table.HeaderCell textAlign='center'></Table.HeaderCell>
            <Table.HeaderCell></Table.HeaderCell>
            <Table.HeaderCell></Table.HeaderCell>
            <Table.HeaderCell textAlign='center'></Table.HeaderCell>
            {this.renderTotalCantidad()}
            <Table.HeaderCell></Table.HeaderCell>
          </Table.Footer>
        </Table>
      );
  }

  renderCobrosList(){
    if (this.state.cobros.length > 0) {
      return (this.state.cobros.map((cobro) => {
        return (
          <Table.Row key={cobro.cobroid}>
            <Table.Cell textAlign='center'>
              {cobro.cobroid}
            </Table.Cell>
            <Table.Cell>
              {cobro.cliente}
            </Table.Cell>
            <Table.Cell>
              {cobro.usuario}
            </Table.Cell>
            <Table.Cell textAlign='center'>
              {cobro.prestamo}
            </Table.Cell>
            <Table.Cell textAlign='right'>
                ${cobro.cantidad}
            </Table.Cell>
            <Table.Cell>
                {new Date(cobro.fecha).toLocaleString()}
            </Table.Cell>
          </Table.Row>
        )
      }));
    }else{
      return(
        <div></div>
      );
    }
  }

  renderTotalCantidad(){
    if (this.state.cobros.length > 0) {
      let totalCobro = 0;
      this.state.cobros.forEach(c => totalCobro += c.cantidad);
      return <Table.HeaderCell textAlign='right'>Total cobrado: ${totalCobro}</Table.HeaderCell>
    }else{
      return <Table.HeaderCell textAlign='right'></Table.HeaderCell>
    }
  }

  render(){
    return(
      <div>
        <Segment>
          {this.renderFiltros()}
        </Segment>
        <Segment>
          {this.renderCobros()}
        </Segment>
      </div>
    )
  }

}
