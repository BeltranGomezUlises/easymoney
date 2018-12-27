/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

import com.ub.easymoney.entities.DistribucionCobro;
import com.ub.easymoney.entities.Prestamo;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ModelPrestamoAbonado {

    Prestamo prestamo;
    DistribucionCobro distribucionCobro;

    public ModelPrestamoAbonado() {
    }

    public ModelPrestamoAbonado(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public DistribucionCobro getDistribucionCobro() {
        return distribucionCobro;
    }

    public void setDistribucionCobro(DistribucionCobro distribucionCobro) {
        this.distribucionCobro = distribucionCobro;
    }

}
