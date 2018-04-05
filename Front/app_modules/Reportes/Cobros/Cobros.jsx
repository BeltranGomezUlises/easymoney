import React from 'react';
import ReactDOM from 'react-dom';
import { Segment } from 'semantic-ui-react';
import ReporteCobros from './Componentes/ReporteCobros.jsx'

export default class Cobros extends React.Component{

  constructor(props) {
    super(props)
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
          <Segment textAlign='center'>
            <h2>REPORTE DE COBROS</h2>
          </Segment>
          <ReporteCobros>
          </ReporteCobros>
      </div>
    );
  }
}
