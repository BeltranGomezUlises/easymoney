/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities;

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
 * @author ulises
 */
@Embeddable
public class RenovadosLogPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "pretamo_renovado")
    private int pretamoRenovado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pretamo_nuevo")
    private int pretamoNuevo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    public RenovadosLogPK() {
    }

    public RenovadosLogPK(int pretamoRenovado, int pretamoNuevo, Date fechaHora) {
        this.pretamoRenovado = pretamoRenovado;
        this.pretamoNuevo = pretamoNuevo;
        this.fechaHora = fechaHora;
    }

    public int getPretamoRenovado() {
        return pretamoRenovado;
    }

    public void setPretamoRenovado(int pretamoRenovado) {
        this.pretamoRenovado = pretamoRenovado;
    }

    public int getPretamoNuevo() {
        return pretamoNuevo;
    }

    public void setPretamoNuevo(int pretamoNuevo) {
        this.pretamoNuevo = pretamoNuevo;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pretamoRenovado;
        hash += (int) pretamoNuevo;
        hash += (fechaHora != null ? fechaHora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RenovadosLogPK)) {
            return false;
        }
        RenovadosLogPK other = (RenovadosLogPK) object;
        if (this.pretamoRenovado != other.pretamoRenovado) {
            return false;
        }
        if (this.pretamoNuevo != other.pretamoNuevo) {
            return false;
        }
        if ((this.fechaHora == null && other.fechaHora != null) || (this.fechaHora != null && !this.fechaHora.equals(other.fechaHora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.RenovadosLogPK[ pretamoRenovado=" + pretamoRenovado + ", pretamoNuevo=" + pretamoNuevo + ", fechaHora=" + fechaHora + " ]";
    }
    
}
