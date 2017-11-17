/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.admin;

import com.ub.easymoney.daos.admin.DaoUsuario;
import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.managers.commons.ManagerSQL;
import com.ub.easymoney.models.ModelLogin;
import com.ub.easymoney.models.commons.exceptions.UsuarioInexistenteException;
import com.ub.easymoney.utils.UtilsSecurity;
import java.util.NoSuchElementException;
import javax.ejb.NoSuchEJBException;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerUsuario extends ManagerSQL<Usuario, Integer>{
    
    public ManagerUsuario() {
        super(new DaoUsuario());
    }
    
    public Usuario login(ModelLogin modelLogin) throws UsuarioInexistenteException{
        String user = modelLogin.getUser();
        String pass = UtilsSecurity.cifrarMD5(modelLogin.getPass());        
        try {
            Usuario u = this.dao.stream().where( a -> a.getNombre().equals(user) && a.getContra().equals(pass)).findFirst().get();            
            return u;
        } catch (NoSuchElementException e) {            
            throw new UsuarioInexistenteException("No se reconoce usuario y/o contraseña");
        }                                
    }

    @Override
    public Usuario persist(Usuario entity) throws Exception {
        entity.setContra(UtilsSecurity.cifrarMD5(entity.getContra()));        
        return super.persist(entity); //To change body of generated methods, choose Tools | Templates.
    }
                        
}
