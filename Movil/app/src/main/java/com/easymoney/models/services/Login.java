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

}
