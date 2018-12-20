/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

import com.ub.easymoney.entities.negocio.Config;
import com.ub.easymoney.entities.negocio.Usuario;

/**
 *
 * @author ulises
 */
public class ModelLoginResponse {

    private Usuario usuario;
    private Config config;

    public ModelLoginResponse() {
    }

    public ModelLoginResponse(Usuario usuario, Config config) {
        this.usuario = usuario;
        this.config = config;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

}
