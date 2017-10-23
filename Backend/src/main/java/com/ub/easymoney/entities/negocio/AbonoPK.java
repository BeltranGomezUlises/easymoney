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
 * modelo de llave primaria de un abono
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Embeddable
public class AbonoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "prestamo")
    private int prestamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public AbonoPK() {
    }

    public AbonoPK(int prestamo, Date fecha) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbonoPK)) {
            return false;
        }
        AbonoPK other = (AbonoPK) object;
        if (this.prestamo != other.prestamo) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.negocio.AbonoPK[ prestamo=" + prestamo + ", fecha=" + fecha + " ]";
    }
    
}
