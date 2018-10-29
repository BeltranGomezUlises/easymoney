import React, {Component} from 'react';
import { Table, Loader, Header, Segment, Checkbox, Form, Button} from 'semantic-ui-react';
import ModalRenovar from './ModalRenovar.jsx';
import ModalReasignar from './ModalReasignar.jsx';
import * as utils from '../../../utils.js';

export default class PrestamoDetalle extends Component{

    constructor(props){
      super(props);
      this.state={
        prestamo: this.props.prestamo,
        abonos:[],
        totales: null,
        loading: false,
        renovando:false
      }
      this.actualizarPrestamo = this.actualizarPrestamo.bind(this);
    }

    componentWillMount(){
      this.cargarAbonosPrestamo();
      this.cargarTotales();
      this.cargarDetallePrestamo();
    }

    cargarAbonosPrestamo(){
      fetch(localStorage.getItem('url') + 'abonos/prestamo/' + this.props.prestamo.id,{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        this.setState({abonos: response.data})
      });
    }

    cargarTotales(){
      fetch(localStorage.getItem('url') + 'prestamos/totales/' + this.props.prestamo.id,{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        this.setState({totales: response.data})
      })
    }

    cargarDetallePrestamo(){
      fetch(localStorage.getItem('url') + 'prestamos/' + this.props.prestamo.id,{
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        }
      }).then((res)=> res.json())
      .then((response) =>{
        this.setState({prestamo:response.data})
      });
    }

    actualizarPrestamo(){
      this.setState({loading: true});
      let {prestamo} = this.state;
      prestamo.abonos = this.state.abonos;

      fetch(localStorage.getItem('url') + 'prestamos',{
        method: 'PUT',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization': localStorage.getItem('tokenSesion')
        },
        body: JSON.stringify(prestamo)
      }).then((res)=> res.json())
      .then((response) =>{
        if (response.meta.status == 'OK') {
          this.setState({loading: false});
          this.cargarTotales();
          this.props.update();
        }else{
          this.setState({loading: false});
        }
      })
    }

    renderAbonos(){
      if (this.state.abonos.length > 0) {
        return this.state.abonos.map((abono)=>{
          return(
            <Table.Row key={abono.abonoPK.fecha}>
              <Table.Cell>{new Date(abono.abonoPK.fecha).toLocaleDateString()}</Table.Cell>
              <Table.Cell textAlign='center'>
                <Form size='small'>
                  <Form.Field>
                    <input type="text" pattern="[0-9]*" onInput={(evt)=>{
                      if(evt.target.value.length <= 4){
                        let {abonos} = this.state;
                        const cantidad = (evt.target.validity.valid) ? evt.target.value : abono.cantidad;
                        for (var i = 0; i < abonos.length; i++) {
                          if (abonos[i].abonoPK.fecha == abono.abonoPK.fecha) {
                            abonos[i].cantidad = cantidad;
                            break;
                          }
                        }
                        this.setState({abonos});
                      }
                    }} value={abono.cantidad} />
                  </Form.Field>
                </Form>
              </Table.Cell>
              <Table.Cell textAlign='center'>
                <Form size='small'>
                  <Form.Field>
                    <input type="text" pattern="[0-9]*" onInput={(evt)=>{
                      if(evt.target.value.length <= 4){
                        let {abonos} = this.state;
                        const cantidad = (evt.target.validity.valid) ? evt.target.value : abono.multa.multa;
                        for (var i = 0; i < abonos.length; i++) {
                          if (abonos[i].abonoPK.fecha == abono.abonoPK.fecha) {
                            abonos[i].multa.multa = cantidad;
                            break;
                          }
                        }
                        this.setState({abonos});
                      }
                    }} value={abono.multa.multa} />
                  </Form.Field>
                </Form>
              </Table.Cell>
              <Table.Cell textAlign='center'>
                <Form size='small'>
                  <Form.Field>
                    <input type="text" onInput={(evt)=>{
                        let {abonos} = this.state;
                        for (var i = 0; i < abonos.length; i++) {
                          if (abonos[i].abonoPK.fecha == abono.abonoPK.fecha) {
                            abonos[i].multa.multaDes = evt.target.value
                            break;
                          }
                        }
                        this.setState({abonos});
                      }
                    } value={abono.multa.multaDes} />
                  </Form.Field>
                </Form>
              </Table.Cell>
              <Table.Cell textAlign='center'>
                <Checkbox key={abono.abonoPK.fecha} checked={abono.abonado} onChange={()=>{
                    let {abonos} = this.state;
                    for (var i = 0; i < abonos.length; i++) {
                      if (abonos[i].abonoPK.fecha == abono.abonoPK.fecha) {
                        abonos[i].abonado = !abonos[i].abonado;
                        break;
                      }
                    }
                    this.setState(abonos);
                  }}></Checkbox>
              </Table.Cell>
            </Table.Row>
          )
        })
      }else{
        return(
          <Table.Row>
            <Table.Cell>
              <div>
                <Loader size='large'>Descargando...</Loader>
              </div>
            </Table.Cell>
          </Table.Row>
        );
      }
    }

    renderTotales(){
      if (this.state.totales !== null) {
        return(
          <Table.Body>
            <Table.Row>
              <Table.Cell textAlign='center'>${this.state.totales.totalAbonado}</Table.Cell>
              <Table.Cell textAlign='center'>${this.state.totales.totalMultado}</Table.Cell>
              <Table.Cell textAlign='center'>${this.state.totales.totalRecuperado}</Table.Cell>
              <Table.Cell textAlign='center'>{this.state.totales.porcentajePagado}%</Table.Cell>
            </Table.Row>
          </Table.Body>
        );
      }else{
        return(
          <Table.Body>
          <Table.Row>
            <Table.Cell textAlign='center'>$XXX</Table.Cell>
            <Table.Cell textAlign='center'>$XXX</Table.Cell>
            <Table.Cell textAlign='center'>$XXX</Table.Cell>
            <Table.Cell textAlign='center'>XXX%</Table.Cell>
            </Table.Row>
          </Table.Body>
        );
      }

    }

    renderButton(){
        return(
          <div>
            <Button
              loading={this.state.loading}
              color='green'
              onClick={this.actualizarPrestamo}>
              Actualizar
            </Button>
            <ModalRenovar
              prestamo={this.props.prestamo}
              totales={this.state.totales}
              update={this.props.update}/>
            <ModalReasignar
              prestamo={this.props.prestamo}
              update={this.props.update}/>
          </div>
        );
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
          <Table celled compact='very'>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Fecha</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Cantidad</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Multa</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Description Multa</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Abonado</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              {this.renderAbonos()}
            </Table.Body>
          </Table>
          <Table celled>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell textAlign='center'>Total Abonado</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Total Multado</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Total Recuperado</Table.HeaderCell>
                <Table.HeaderCell textAlign='center'>Porcentaje Pagado</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            {this.renderTotales()}
          </Table>
          <Segment>
            {this.renderButton()}
          </Segment>
        </Segment>
      );
    }
}
