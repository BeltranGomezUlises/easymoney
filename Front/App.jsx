import React from 'react';
import { Button,Segment,Divider } from 'semantic-ui-react'

export default class App extends React.Component {
  render (){
    return(
      <div className="ui center aligned segment" style={{width: "50%", aling: "center"}}>
        <h1>Easy Money</h1>
        <Segment>
          <Divider section/>
          <input type="text" placeholder="Usuario"/><br/>
          <input type="pass" placeholder="Contraseña"/>
          <Button>boton</Button>
        </Segment>
      </div>
    )
  }
}
