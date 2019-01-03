/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers;

import com.ub.easymoney.daos.DaoCobro;
import com.ub.easymoney.entities.Cobro;
import com.ub.easymoney.utils.commons.ManagerSQL;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ManagerCobro extends ManagerSQL<Cobro, Integer> {

    public ManagerCobro() {
        super(new DaoCobro());
    }

}
