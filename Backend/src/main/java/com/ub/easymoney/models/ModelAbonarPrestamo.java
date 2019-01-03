/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

import com.ub.easymoney.entities.Prestamo;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ModelAbonarPrestamo {

    private int cantidadAbono;
    private int cobradorId;
    private Prestamo prestamo;

    public ModelAbonarPrestamo() {
    }

    public ModelAbonarPrestamo(int cantidadAbono, int cobradorId, Prestamo prestamo) {
        this.cantidadAbono = cantidadAbono;
        this.cobradorId = cobradorId;
        this.prestamo = prestamo;
    }

    public int getCantidadAbono() {
        return cantidadAbono;
    }

    public void setCantidadAbono(int cantidadAbono) {
        this.cantidadAbono = cantidadAbono;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public int getCobradorId() {
        return cobradorId;
    }

    public void setCobradorId(int cobradorId) {
        this.cobradorId = cobradorId;
    }

}
