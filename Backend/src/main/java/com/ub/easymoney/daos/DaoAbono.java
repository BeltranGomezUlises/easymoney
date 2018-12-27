/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos;

import com.ub.easymoney.utils.commons.DaoSQLFacade;
import com.ub.easymoney.entities.Abono;
import com.ub.easymoney.entities.AbonoPK;
import com.ub.easymoney.utils.UtilsDB;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class DaoAbono extends DaoSQLFacade<Abono, AbonoPK> {

    public DaoAbono() {
        super(UtilsDB.getEMFactoryCG(), Abono.class, AbonoPK.class);
    }

}
