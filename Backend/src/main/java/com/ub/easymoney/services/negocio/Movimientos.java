/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Movimiento;
import com.ub.easymoney.managers.negocio.ManagerMovimiento;
import com.ub.easymoney.services.commos.ServiceFacade;
import javax.ws.rs.Path;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/movimientos")
public class Movimientos extends ServiceFacade<Movimiento, Integer>{
    
    public Movimientos() {
        super(new ManagerMovimiento());
    }
    
}
