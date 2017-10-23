/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.negocio.DaoAbono;
import com.ub.easymoney.entities.negocio.Abono;
import com.ub.easymoney.entities.negocio.AbonoPK;
import com.ub.easymoney.managers.commons.ManagerSQL;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerAbono extends ManagerSQL<Abono, AbonoPK>{
    
    public ManagerAbono() {
        super(new DaoAbono());
    }
    
}
