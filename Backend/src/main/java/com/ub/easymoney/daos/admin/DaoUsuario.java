/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos.admin;

import com.ub.easymoney.daos.commons.DaoSQLFacade;
import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.utils.UtilsDB;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class DaoUsuario extends DaoSQLFacade<Usuario, Integer>{
  
    public DaoUsuario(){
        super(UtilsDB.getEMFactoryCG(), Usuario.class, Integer.class);
    }
  
}
