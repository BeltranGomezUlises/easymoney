/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities.negocio;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Embeddable
public class MultaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "prestamo")
    private int prestamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public MultaPK() {
    }

    public MultaPK(int prestamo, Date fecha) {
        this.prestamo = prestamo;
        this.fecha = fecha;
    }

    public int getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(int prestamo) {
        this.prestamo = prestamo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) prestamo;
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MultaPK)) {
            return false;
        }
        MultaPK other = (MultaPK) object;
        if (this.prestamo != other.prestamo) {
            return false;
        }
        return this.fecha.equals(other.fecha);
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.negocio.MultaPK[ prestamo=" + prestamo + ", fecha=" + fecha + " ]";
    }

}
