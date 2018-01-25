package com.easymoney.models.services;

import com.easymoney.entities.Usuario;

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

        public static class Config {
            private int diasPrestamo;
            private int porcentajeInteresPrestamo;
            private Integer id;

            public Config() {
            }

            public int getDiasPrestamo() {
                return diasPrestamo;
            }

            public void setDiasPrestamo(int diasPrestamo) {
                this.diasPrestamo = diasPrestamo;
            }

            public int getPorcentajeInteresPrestamo() {
                return porcentajeInteresPrestamo;
            }

            public void setPorcentajeInteresPrestamo(int porcentajeInteresPrestamo) {
                this.porcentajeInteresPrestamo = porcentajeInteresPrestamo;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }
        }
    }

}
