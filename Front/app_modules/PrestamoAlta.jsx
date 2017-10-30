import React, {Component} from 'react';
import { Input, Button, Label, Form, Dropdown, Grid, Segment} from 'semantic-ui-react';

export default class PrestamoAlta extends Component{
  constructor(props){
    super(props)
    this.state={
      clientes:[{
        key: 'AL',
        value: 'AL',
        text: 'pedro gómez'
      },{
        key: 'Me',
        value: 'Me',
        text: 'Juan López'
      }],
      cobradores:[{
        key: '1',
        value: '1',
        text: 'juana Beltrán'
      },{
        key: '2',
        value: '2',
        text: 'Mario Bros'
      }]
    }
  }
  render(){
    return(
      <div style={{width: '600px'}}>
        <Segment>
          <Grid columns={2}>
             <Grid.Row>
               <Grid.Column>
                  <Label pointing='right'>Cliente</Label>
               </Grid.Column>
               <Grid.Column>
                 <Dropdown placeholder='Cliente' search selection options={this.state.clientes}/>
               </Grid.Column>
             </Grid.Row>
            <Grid.Row>
               <Grid.Column>
                  <Label pointing='right'>Cobrador</Label>
               </Grid.Column>
               <Grid.Column>
                 <Dropdown placeholder='Cobrador' search selection options={this.state.cobradores}/>
               </Grid.Column>
            </Grid.Row>
            <Grid.Row>
              <Grid.Column>
                <Label pointing='right'>Cantidad a prestar</Label>
              </Grid.Column>
              <Grid.Column>
                <Input type='number'/>
              </Grid.Column>
            </Grid.Row>
            </Grid>
        </Segment>
      </div>
    )
  }
}
