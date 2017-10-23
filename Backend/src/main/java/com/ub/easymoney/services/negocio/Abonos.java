/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Abono;
import com.ub.easymoney.entities.negocio.AbonoPK;
import com.ub.easymoney.managers.negocio.ManagerAbono;
import com.ub.easymoney.services.commos.ServiceFacade;
import javax.ws.rs.Path;

/**
 * servicios para registros de abono a un prestamo
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/abonos")
public class Abonos extends ServiceFacade<Abono, AbonoPK>{
    
    public Abonos() {
        super(new ManagerAbono());
    }
    
}
