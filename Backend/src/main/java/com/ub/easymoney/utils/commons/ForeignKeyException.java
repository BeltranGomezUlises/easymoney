/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.utils.commons;

/**
 * Exception para identificar las violaciones a llaves foraneas en bases de datos relacionales
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ForeignKeyException extends Exception {

    private String message;

    public ForeignKeyException(String message) {
        this.message = message;
    }

    public ForeignKeyException() {
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
