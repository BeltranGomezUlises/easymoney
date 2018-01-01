/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.admin;

import com.ub.easymoney.daos.admin.DaoConfig;
import com.ub.easymoney.daos.admin.DaoUsuario;
import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.managers.commons.ManagerSQL;
import com.ub.easymoney.models.ModelLogin;
import com.ub.easymoney.models.commons.exceptions.UserException;
import com.ub.easymoney.models.commons.exceptions.UsuarioInexistenteException;
import com.ub.easymoney.utils.UtilsSecurity;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerUsuario extends ManagerSQL<Usuario, Integer> {

    public ManagerUsuario() {
        super(new DaoUsuario());
    }

    public Usuario login(ModelLogin modelLogin) throws UsuarioInexistenteException {
        String user = modelLogin.getUser();
        String pass = UtilsSecurity.cifrarMD5(modelLogin.getPass());
        try {
            Usuario u = this.dao.stream().where(a -> a.getNombre().equals(user) && a.getContra().equals(pass)).findFirst().get();
            return u;
        } catch (NoSuchElementException e) {
            throw new UsuarioInexistenteException("No se reconoce usuario y/o contraseña");
        }
    }

    @Override
    public void update(Usuario entity) throws Exception {
        String nombreUsuario = entity.getNombre();
        if (!this.dao.stream().where(u -> u.getNombre().equals(nombreUsuario)).findAny().isPresent()) {            
            super.update(entity); //To change body of generated methods, choose Tools | Templates.
        }else{
            throw new UserException.UsuarioYaExistente("Usuario ya existente en el sistema");
        }        
    }

    
    
    @Override
    public Usuario persist(Usuario entity) throws Exception {
        String nombreUsuario = entity.getNombre();
        if (!this.dao.stream().where(u -> u.getNombre().equals(nombreUsuario)).findAny().isPresent()) {
            if (entity.getContra() == null) {
                DaoConfig daoConfig = new DaoConfig();
                entity.setContra(daoConfig.findFirst().getContraDefault());
            }
            entity.setContra(UtilsSecurity.cifrarMD5(entity.getContra()));
            return super.persist(entity);
        }
        throw new UserException.UsuarioYaExistente("Usuario ya existente en el sistema");
    }

    public List<Usuario> usuariosCobradores() {
        DaoUsuario daoUsuario = new DaoUsuario();
        return daoUsuario.usuariosCobradores();
    }

}
