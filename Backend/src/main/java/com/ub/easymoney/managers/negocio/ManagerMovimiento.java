/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.negocio.DaoMovimiento;
import com.ub.easymoney.entities.negocio.Movimiento;
import com.ub.easymoney.managers.commons.ManagerSQL;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerMovimiento extends  ManagerSQL<Movimiento, Integer> {
    
    public ManagerMovimiento() {
        super(new DaoMovimiento());
    }
    
    
    
}
