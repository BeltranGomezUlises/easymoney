import React from 'react';
import { Route, Link, HashRouter } from 'react-router-dom';
import { Button, Container, Divider,  Dropdown,  Header,  Message,  Segment,  Menu, Icon, Sidebar, Image } from 'semantic-ui-react';
import Notifications, {notify} from 'react-notify-toast';

import Clientes from './app_modules/Clientes/Clientes.jsx';
import Cobradores from './app_modules/Cobradores/Cobradores.jsx';
import Prestamos from './app_modules/Prestamos/Prestamos.jsx';
import Movimientos from './app_modules/Movimientos/Movimientos.jsx'
import Login from './app_modules/Login.jsx';
import Configuraciones from './app_modules/Configuraciones.jsx';
import Cobros from './app_modules/Reportes/Cobros/Cobros.jsx'
import config from './config.json';

const App =()=>(
    <HashRouter>
        <MainContainer></MainContainer>
    </HashRouter>
)

class MainContainer extends React.Component{

  constructor(props) {
    super(props);
    this.state = {menuVisible: true};
  }


  renderMainContent(){
    localStorage.setItem('url', config.apiUrl );
    if (localStorage.getItem('tokenSesion') === '' || localStorage.getItem('tokenSesion') === null) {
      return(
        <Login></Login>
      );
    }else{
      return(
        <div style={mystyle}>
          <Menu attached="top" >
           <Menu.Item as={Link} to='/clientes'>Clientes</Menu.Item>
           <Menu.Item as={Link} to='/cobradores'>Cobradores</Menu.Item>
           <Menu.Item as={Link} to='/prestamos'>Préstamos</Menu.Item>
           <Menu.Item as={Link} to='/movimientos'>Movimientos Ingresos y Egresos</Menu.Item>
           <Dropdown item simple text='Reportes'>
             <Dropdown.Menu>
               <Dropdown.Item as={Link} to='/cobros'>Cobros</Dropdown.Item>
               <Dropdown.Divider />
               <Dropdown.Item>Clientes liquidados</Dropdown.Item>
               <Dropdown.Item>Prestamos general</Dropdown.Item>
             </Dropdown.Menu>
           </Dropdown>

           <Menu.Menu position='right'>
           <Dropdown item simple text='Sistema'>
               <Dropdown.Menu>
                   <Menu.Item as={Link} to='/configuraciones'>Configuraciones</Menu.Item>
                   <Menu.Item onClick={() => {
                       localStorage.setItem('tokenSesion', '');
                       let ruta = window.location.href.split('#');
                       window.location.href = ruta[0] + '#/login';
                     }} >
                     <strong>Salir</strong>
                   </Menu.Item>
               </Dropdown.Menu>
           </Dropdown>
           </Menu.Menu>
          </Menu>
           <div>
             <Route path="/clientes" component={Clientes}/>
             <Route path="/cobradores" component={Cobradores}/>
             <Route path="/prestamos" component={Prestamos}/>
             <Route path="/movimientos" component={Movimientos}/>
             <Route path="/login" component={Login}/>
             <Route path="/configuraciones" component={Configuraciones}/>
             <Route path="/cobros" component={Cobros}/>
           </div>
          </div>
      );
    }
  }

  render() {
    return(
      <div style={mystyle}>
        <Notifications />
        {this.renderMainContent()}
      </div>
    )
  }
}

//estilos
var mystyle={
  'height' : 'inherit'
}

export default App
