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
public class ModelPrestamoAbonado {

    Prestamo prestamo;
    ModelPrestamoTotales modelPrestamoTotales;
    ModelDistribucionAbono modelDistribucionAbono;

    public ModelPrestamoAbonado() {
    }

    public ModelPrestamoAbonado(Prestamo prestamo, ModelPrestamoTotales modelPrestamoTotales, ModelDistribucionAbono modelDistribucionAbono) {
        this.prestamo = prestamo;
        this.modelPrestamoTotales = modelPrestamoTotales;
        this.modelDistribucionAbono = modelDistribucionAbono;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public ModelPrestamoTotales getModelPrestamoTotales() {
        return modelPrestamoTotales;
    }

    public void setModelPrestamoTotales(ModelPrestamoTotales modelPrestamoTotales) {
        this.modelPrestamoTotales = modelPrestamoTotales;
    }

    public ModelDistribucionAbono getModelDistribucionAbono() {
        return modelDistribucionAbono;
    }

    public void setModelDistribucionAbono(ModelDistribucionAbono modelDistribucionAbono) {
        this.modelDistribucionAbono = modelDistribucionAbono;
    }

}
