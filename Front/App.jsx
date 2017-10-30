import React from 'react';
import { Route, Link, HashRouter } from 'react-router-dom';
import { Button, Container, Divider,  Dropdown,  Header,  Message,  Segment,  Menu, Icon, Sidebar, Image } from 'semantic-ui-react';

import Clientes from './app_modules/Clientes/Clientes.jsx';
import Cobradores from './app_modules/Cobradores/Cobradores.jsx';
import Prestamos from './app_modules/Prestamos/Prestamos.jsx';
import ClienteAlta from './app_modules/Clientes/ClienteAlta.jsx';
import CobradorAlta from './app_modules/Cobradores/CobradorAlta.jsx';
import PrestamoAlta from './app_modules/PrestamoAlta.jsx';

const App =()=>(
    <HashRouter>
        <MainContainer></MainContainer>
    </HashRouter>
)

class MainContainer extends React.Component{
  constructor(props) {
    super(props);
    this.state = {menuVisible: true};
    localStorage.setItem('url', 'http://201.165.0.142:8383/EasyMoney/api/');
  }

  render() {
    return( <div style={mystyle}>
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
          <Menu.Item as={Link} to='/clienteAlta'>Cliente Registro</Menu.Item>
          <Menu.Item as={Link} to='/cobradorAlta'>Cobrador Registro</Menu.Item>
          <Menu.Item as={Link} to='/prestamoAlta'>Prestamo Registro</Menu.Item>
        </Sidebar>
         <Sidebar.Pusher >
              <div>
                <Route path="/clientes" component={Clientes}/>
                <Route path="/cobradores" component={Cobradores}/>
                <Route path="/prestamos" component={Prestamos}/>
                <Route path="/clienteAlta" component={ClienteAlta}/>
                <Route path="/cobradorAlta" component={CobradorAlta}/>
                <Route path="/prestamoAlta" component={PrestamoAlta}/>
              </div>
         </Sidebar.Pusher>
      </Sidebar.Pushable>
      </div>
    )
  }
}

//estilos
var mystyle={
  'height' : 'inherit'
}
export default App
