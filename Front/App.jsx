import React from 'react';
import { Route, Link, HashRouter } from 'react-router-dom';
import { Button, Container, Divider,  Dropdown,  Header,  Message,  Segment,  Menu, Icon, Sidebar, Image } from 'semantic-ui-react';

import Clientes from './app_modules/Clientes/Clientes.js';
import Cobradores from './app_modules/Cobradores.js';
import Prestamos from './app_modules/Prestamos.js';

const App =()=>(
    <HashRouter>
        <MainContainer></MainContainer>
    </HashRouter>
)

class MainContainer extends React.Component{
  constructor(props) {
    super(props);
    this.state = {menuVisible: false};
  }

  render() {
    return( <div>
        <Menu secondary attached="top">
          <Menu.Item onClick={() => this.setState({ menuVisible: !this.state.menuVisible })} >
            <strong>Menú</strong>
          </Menu.Item>
        </Menu>
      <Sidebar.Pushable as={Segment} attached="bottom" >
        <Sidebar width='thin' as={Menu} animation="uncover" visible={this.state.menuVisible} icon="labeled" vertical inline inverted>
          <Menu.Item as={Link} to='/clientes'>Clientes</Menu.Item>
          <Menu.Item as={Link} to='/cobradores'>Cobradores</Menu.Item>
          <Menu.Item as={Link} to='/prestamos'>Prestamos</Menu.Item>
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
    )
  }
}

//estilos
var mystyle={
  'height' : '700px'
}
export default App
