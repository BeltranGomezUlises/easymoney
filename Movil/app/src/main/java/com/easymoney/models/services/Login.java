package com.easymoney.models.services;

import com.easymoney.entities.Usuario;
import com.easymoney.models.Config;

/**
 * Created by ulises on 30/12/17.
 */

public class Login {

    public static class Request {
        private String user;
        private String pass;

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

        @Override
        public String toString() {
            return "Request{" +
                    "user='" + user + '\'' +
                    ", pass='" + pass + '\'' +
                    '}';
        }
    }

    public static class Response {

        private Usuario usuario;
        private Config config;

        public Response() {
        }

        public Response(Usuario usuario, Config config) {
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
}
