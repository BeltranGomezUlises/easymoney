package com.easymoney.models;

import com.easymoney.entities.DistribucionCobro;
import com.easymoney.entities.Prestamo;

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
