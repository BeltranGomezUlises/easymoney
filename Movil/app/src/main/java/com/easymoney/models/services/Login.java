package com.easymoney.models.services;

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
        private int id;
        private String nombre;
        private String nombreCompleto;
        private String contra;
        private boolean tipo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getContra() {
            return contra;
        }

        public void setContra(String contra) {
            this.contra = contra;
        }

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        public void setNombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
        }

        public boolean isTipo() {
            return tipo;
        }

        public void setTipo(boolean tipo) {
            this.tipo = tipo;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    ", nombre='" + nombre + '\'' +
                    ", nombreCompleto='" + nombreCompleto + '\'' +
                    ", contra='" + contra + '\'' +
                    ", tipo=" + tipo +
                    '}';
        }
    }
}
