import React from 'react';
import {BrowserRouter as Router, Route, Link } from 'react-router-dom';
import { Button, Container, Divider,  Dropdown,  Header,  Message,  Segment,  Menu, Icon, Sidebar, Image } from 'semantic-ui-react';

import Clientes from './app_modules/Clientes.js';
import Cobradores from './app_modules/Cobradores.js';
import Prestamos from './app_modules/Prestamos.js';

var mystyle={
  'height' : '700px'
}

const App = () => (
    <Router>
        <MainContainer></MainContainer>
    </Router>
)

class MainContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {menuVisible: false};
  }

  render() {

    return <div>
      <Menu secondary attached="top">
        <Menu.Item onClick={() => this.setState({ menuVisible: !this.state.menuVisible })} >
          <Icon name="sidebar" />Menu
        </Menu.Item>
      </Menu>
    <Sidebar.Pushable as={Segment} attached="bottom" >
      <Sidebar width='thin' as={Menu} animation="uncover" visible={this.state.menuVisible} icon="labeled" vertical inline inverted>
        <Menu.Item as={Link} to='/clientes'><Icon name="" />Clientes</Menu.Item>
        <Menu.Item as={Link} to='/cobradores'><Icon name="" />Cobradores</Menu.Item>
        <Menu.Item as={Link} to='/prestamos'><Icon name="" />Prestamos</Menu.Item>
      </Sidebar>
       <Sidebar.Pusher >
            <div style={mystyle}>

              <Route path="/clientes" component={Clientes}/>
              <Route path="/cobradores" component={Cobradores}/>
              <Route path="/prestamos" component={Prestamos}/>

            </div>
       </Sidebar.Pusher>
    </Sidebar.Pushable>
    </div>
  }
}
export default App
