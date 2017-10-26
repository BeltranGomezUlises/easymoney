/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Cliente;
import com.ub.easymoney.managers.negocio.ManagerCliente;
import com.ub.easymoney.services.commos.ServiceFacade;
import javax.ws.rs.Path;

/**
 * servicios LCRUD de clientes del negocio a los que se les asignaran prestamos
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/clientes")
public class Clientes extends ServiceFacade<Cliente, Integer>{
    
    public Clientes() {
        super(new ManagerCliente());
    }
            
}
