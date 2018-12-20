/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.admin;

import com.ub.easymoney.daos.admin.DaoConfig;
import com.ub.easymoney.entities.negocio.Config;
import com.ub.easymoney.managers.commons.ManagerSQL;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerConfig extends ManagerSQL<Config, Integer> {

    public ManagerConfig() {
        super(new DaoConfig());
    }

}
