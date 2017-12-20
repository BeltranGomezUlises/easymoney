import React from 'react';
import ReactDOM from 'react-dom';
import { Segment, Modal, Button, Header } from 'semantic-ui-react';
import MovimientoList from './Componentes/MovimientoList.jsx'
import * as utils from '../../utils.js';

export default class Movimientos extends React.Component{

  constructor(props) {
    super(props)
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
          <Segment textAlign='center'>
            <h2>Movimientos Ingresos y Egresos</h2>
          </Segment>
          <MovimientoList/>
      </div>
    );
  }
}
