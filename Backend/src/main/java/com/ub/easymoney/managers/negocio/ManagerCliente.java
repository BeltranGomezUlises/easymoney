/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.negocio.DaoCliente;
import com.ub.easymoney.entities.negocio.Cliente;
import com.ub.easymoney.managers.commons.ManagerSQL;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerCliente extends ManagerSQL<Cliente, Integer> {

    public ManagerCliente() {
        super(new DaoCliente());
    }
 
}
