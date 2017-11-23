import React from 'react';
import { Route, Link, HashRouter } from 'react-router-dom';
import { Button, Container, Divider,  Dropdown,  Header,  Message,  Segment,  Menu, Icon, Sidebar, Image } from 'semantic-ui-react';
import Notifications, {notify} from 'react-notify-toast';

import Clientes from './app_modules/Clientes/Clientes.jsx';
import Cobradores from './app_modules/Cobradores/Cobradores.jsx';
import Prestamos from './app_modules/Prestamos/Prestamos.jsx';
import Login from './app_modules/Login.jsx';
import Configuraciones from './app_modules/Configuraciones.jsx';

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
    //localStorage.setItem('url', 'http://localhost:8084/EasyMoney/api/');
  }


  renderMainContent(){
    if (localStorage.getItem('tokenSesion') === '' || localStorage.getItem('tokenSesion') === undefined) {
      return(
        <Login></Login>
      );
    }else{
      return(
        <div style={mystyle}>
          <Menu secondary attached="top">
           <Menu.Item position='left' onClick={() => this.setState({ menuVisible: !this.state.menuVisible })} >
             <strong>Menú</strong>
           </Menu.Item>
           <Menu.Item position='right' onClick={() => {
               localStorage.setItem('tokenSesion', '');
               let ruta = window.location.href.split('#');
               window.location.href = ruta[0] + '#/login';
             }} >
             <strong>Salir</strong>
           </Menu.Item>
         </Menu>
       <Sidebar.Pushable as={Segment} attached="bottom" >
         <Sidebar width='thin' as={Menu} animation="uncover" visible={this.state.menuVisible} icon="labeled" vertical inline inverted>
           <Menu.Item as={Link} to='/clientes'>Clientes</Menu.Item>
           <Menu.Item as={Link} to='/cobradores'>Cobradores</Menu.Item>
           <Menu.Item as={Link} to='/prestamos'>Prestamos</Menu.Item>
           <Menu.Item position='right' as={Link} to='/configuraciones'>configuraciones</Menu.Item>
         </Sidebar>
          <Sidebar.Pusher >
               <div>
                 <Route path="/clientes" component={Clientes}/>
                 <Route path="/cobradores" component={Cobradores}/>
                 <Route path="/prestamos" component={Prestamos}/>
                 <Route path="/login" component={Login}/>
                 <Route path="/configuraciones" component={Configuraciones}/>
               </div>
          </Sidebar.Pusher>
       </Sidebar.Pushable>
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
