/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.admin;

import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.managers.admin.ManagerUsuario;
import com.ub.easymoney.services.commos.ServiceFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * servicios LCRUD de usuarios del sistema
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Usuarios extends ServiceFacade<Usuario, Integer>{
    
    public Usuarios() {
        super(new ManagerUsuario());
    }
                    
}
