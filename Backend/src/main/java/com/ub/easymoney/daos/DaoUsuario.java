/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos;

import com.ub.easymoney.utils.commons.DaoSQLFacade;
import com.ub.easymoney.entities.Usuario;
import com.ub.easymoney.utils.UtilsDB;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class DaoUsuario extends DaoSQLFacade<Usuario, Integer> {

    public DaoUsuario() {
        super(UtilsDB.getEMFactoryCG(), Usuario.class, Integer.class);
    }

    public List<Usuario> usuariosCobradores() {
        return this.stream().where(u -> u.getTipo() == false).collect(toList());
    }

}
