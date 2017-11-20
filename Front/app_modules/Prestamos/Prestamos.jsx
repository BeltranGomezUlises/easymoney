import React from 'react';
import ReactDOM from 'react-dom';
import { Segment } from 'semantic-ui-react';
import PrestamoList from './Componentes/PrestamoList.jsx'

export default class Prestamos extends React.Component{

  constructor(props) {
    super(props)
  }

  render(){
    return(
      <div style={{'padding':'10px'}}>
          <Segment textAlign='center'>
            <h2>PRESTAMOS</h2>
          </Segment>
          <PrestamoList>
          </PrestamoList>
      </div>
    );
  }
}
