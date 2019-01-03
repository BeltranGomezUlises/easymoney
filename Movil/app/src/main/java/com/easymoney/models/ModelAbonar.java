package com.easymoney.models;

import com.easymoney.entities.Prestamo;

/**
 * Created by ulises on 03/04/2018.
 */

public class ModelAbonar {

    private int prestamoId;
    private int cantidad;
    private String descripcion;

    public ModelAbonar(int prestamoId, int cantidad, String descripcion) {
        this.prestamoId = prestamoId;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public int getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(int prestamoId) {
        this.prestamoId = prestamoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
