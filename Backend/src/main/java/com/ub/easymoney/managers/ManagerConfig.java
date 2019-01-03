/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers;

import com.ub.easymoney.daos.DaoConfig;
import com.ub.easymoney.entities.Config;
import com.ub.easymoney.utils.commons.ManagerSQL;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerConfig extends ManagerSQL<Config, Integer> {

    public ManagerConfig() {
        super(new DaoConfig());
    }

}
