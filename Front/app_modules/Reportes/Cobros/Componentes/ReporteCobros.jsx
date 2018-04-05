import React from 'react';
import { Segment, Divider, Form, Radio, Select, Button} from 'semantic-ui-react';
import * as utils from '../../../../utils.js';

const options = [
  { key: 'm', text: 'Male', value: 'male' },
  { key: 'f', text: 'Female', value: 'female' },
]

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
      optionsUsuarios:[]
    }
    this.handleChangeRadio = this.handleChangeRadio.bind(this);
  }

  componentWillMount() {
    this.cargarClientes();
    this.cargarUsuarios();
  }

  handleChangeRadio(e, {value}){
    let {filtro} = this.state;
    filtro.tipo = value;
    this.setState(filtro);
  }

  handleChangeDropDown(e, {value}){
    let {filtro} = this.state;
    filtro.agrupadorId = value;
    this.setState(filtro);
  }

  cargarCobros(){
    this.setState({buscando:false});
    console.log(this.state.filtro)
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
          optionsClientes.push({ key: 0, text: 'Todos', value: 0});
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
          optionsUsuarios.push({ key: 0, text: 'Todos', value: 0});
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
        <Button primary type='submit' onClick={()=>{
            this.setState({buscando:true})
            this.cargarCobros()
        }}>Buscar</Button>
      );
    }
  }

  renderDropDownAgrupador(){
    if (this.state.filtro.tipo === '1') {
      return(
        <Form.Field control={Select} label='Cliente:' options={this.state.optionsClientes} placeholder='Cliente' />
      );
    }else{
      return (
        <Form.Field control={Select} label='Usuario:' options={this.state.optionsUsuarios} placeholder='Usuario' />
      );
    }
  }

  renderFiltros(){
    return (
      <div>
        <Divider horizontal>Filtros</Divider>
        <Form>
          <Form.Group inline>
           <label>Agrupar Por:</label>
           <Form.Field control={Radio} label='Cliente' value='1' checked={this.state.filtro.tipo === '1'} onChange={this.handleChangeRadio} />
           <Form.Field control={Radio} label='Cobrador' value='2' checked={this.state.filtro.tipo === '2'} onChange={this.handleChangeRadio} />
         </Form.Group>
          <Form.Group>
            {/* agrupador */ }
            {this.renderDropDownAgrupador()}
            {/*fechas*/}
            <Form.Field>
              <label>Cobros despues de:</label>
              <input
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
    return (
      <div>
      cobros
      </div>
    );
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
