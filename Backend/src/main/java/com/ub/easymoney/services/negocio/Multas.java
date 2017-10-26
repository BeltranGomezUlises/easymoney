/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Multa;
import com.ub.easymoney.entities.negocio.MultaPK;
import com.ub.easymoney.managers.negocio.ManagerMulta;
import com.ub.easymoney.services.commos.ServiceFacade;
import javax.ws.rs.Path;

/**
 * servicios para registros de multas de los dias de abonos
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/multas")
public class Multas extends ServiceFacade<Multa, MultaPK>{
    
    public Multas() {
        super(new ManagerMulta());
    }
    
}
