/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ModelSaldoPrestamos {

    /**
     * prestamo id
     */
    private int id;
    private int cantidadAPagar;
    private int abonado;

    public ModelSaldoPrestamos() {
    }

    public ModelSaldoPrestamos(int id, int cantidadAPagar, int abonado) {
        this.id = id;
        this.cantidadAPagar = cantidadAPagar;
        this.abonado = abonado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidadAPagar() {
        return cantidadAPagar;
    }

    public void setCantidadAPagar(int cantidadAPagar) {
        this.cantidadAPagar = cantidadAPagar;
    }

    public int getAbonado() {
        return abonado;
    }

    public void setAbonado(int abonado) {
        this.abonado = abonado;
    }

}
