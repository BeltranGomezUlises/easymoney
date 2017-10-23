/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Cobrador;
import com.ub.easymoney.managers.negocio.ManagerCobrador;
import com.ub.easymoney.services.commos.ServiceFacade;
import javax.ws.rs.Path;

/**
 * servicios LCRUd para la entidad de cobradores
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/cobradores")
public class Cobradores  extends ServiceFacade<Cobrador, Integer>{
    
    public Cobradores() {
        super(new ManagerCobrador());
    }
    
}
