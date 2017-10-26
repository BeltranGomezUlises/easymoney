/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.negocio.ManagerPrestamo;
import com.ub.easymoney.services.commos.ServiceFacade;
import javax.ws.rs.Path;

/**
 * servicios para registros de prestamos a clientes
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/prestamos")
public class Prestamos extends ServiceFacade<Prestamo, Integer>{
    
    public Prestamos() {
        super(new ManagerPrestamo());
    }
    
}
