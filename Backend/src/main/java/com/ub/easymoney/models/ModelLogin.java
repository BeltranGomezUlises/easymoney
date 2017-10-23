/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

/**
 * modelo para consumo de servicio de inicio de sesion
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ModelLogin {
    
    private String user;
    private String pass;

    public ModelLogin() {
    }

    public ModelLogin(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
            
}
