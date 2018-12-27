/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easymoney.entities;

import java.io.Serializable;
import java.util.Date;

public class Cobro implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private int cantidad;
    private Date fecha;
    private Usuario cobrador;

    public Cobro() {
    }

    public Cobro(Integer id) {
        this.id = id;
    }

    public Cobro(Integer id, int cantidad, Date fecha) {
        this.id = id;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getCobrador() {
        return cobrador;
    }

    public void setCobrador(Usuario cobrador) {
        this.cobrador = cobrador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cobro)) {
            return false;
        }
        Cobro other = (Cobro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
