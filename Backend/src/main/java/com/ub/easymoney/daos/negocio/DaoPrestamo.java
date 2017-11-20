/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos.negocio;

import com.ub.easymoney.daos.commons.DaoSQLFacade;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.utils.UtilsDB;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class DaoPrestamo extends DaoSQLFacade<Prestamo, Integer> {
    
    public DaoPrestamo() {
        super(UtilsDB.getEMFactoryCG(), Prestamo.class, Integer.class);
    }
     
}
