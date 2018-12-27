package com.easymoney.models;

import com.easymoney.entities.Prestamo;

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
