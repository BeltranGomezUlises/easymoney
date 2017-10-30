import React, {Component} from 'react';
import { Input, Button, Label, Divider, Form} from 'semantic-ui-react';

export default class ClienteAlta extends Component{
    render(){
      return(
        <Form>
          <Form.Field inline>
            <Label pointing='right'>Nombre</Label>
            <input type='text' />
          </Form.Field>
          <Form.Field inline>
            <Label pointing='right'>Teléfono</Label>
            <input type='text' />
          </Form.Field>
          <Form.Field inline>
            <Label pointing='right'>Dirección</Label>
            <input type='text' />
          </Form.Field>
        </Form>
      );
    }
}
