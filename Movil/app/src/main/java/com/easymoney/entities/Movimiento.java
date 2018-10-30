package com.easymoney.entities;

import java.util.Date;

/**
 * Created by ulises on 03/03/2018.
 */

public class Movimiento {

    private Integer id;
    private double cantidad;
    private Date fecha;
    private String descripcion;
    private Usuario usuarioCreador;

    public Movimiento() {
    }

    public Movimiento(Integer id) {
        this.id = id;
    }

    public Movimiento(Integer id, double cantidad, Date fecha) {
        this.id = id;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Movimiento(double cantidad, Date fecha, String descripcion, Usuario usuarioCreador) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.usuarioCreador = usuarioCreador;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Movimiento)) {
            return false;
        }
        Movimiento other = (Movimiento) object;
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.negocio.Movimiento[ id=" + id + " ]";
    }

}
