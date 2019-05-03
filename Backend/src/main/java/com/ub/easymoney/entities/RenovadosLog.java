/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities;

import com.ub.easymoney.utils.commons.IEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ulises
 */
@Entity
@Table(name = "renovados_log")
@NamedQueries({
    @NamedQuery(name = "RenovadosLog.findAll", query = "SELECT r FROM RenovadosLog r"),
    @NamedQuery(name = "RenovadosLog.findByPretamoRenovado", query = "SELECT r FROM RenovadosLog r WHERE r.renovadosLogPK.pretamoRenovado = :pretamoRenovado"),
    @NamedQuery(name = "RenovadosLog.findByPretamoNuevo", query = "SELECT r FROM RenovadosLog r WHERE r.renovadosLogPK.pretamoNuevo = :pretamoNuevo"),
    @NamedQuery(name = "RenovadosLog.findByFechaHora", query = "SELECT r FROM RenovadosLog r WHERE r.renovadosLogPK.fechaHora = :fechaHora")})
public class RenovadosLog implements Serializable, IEntity<RenovadosLogPK> {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RenovadosLogPK renovadosLogPK;

    public RenovadosLog() {
    }

    public RenovadosLog(RenovadosLogPK renovadosLogPK) {
        this.renovadosLogPK = renovadosLogPK;
    }

    public RenovadosLog(int pretamoRenovado, int pretamoNuevo, Date fechaHora) {
        this.renovadosLogPK = new RenovadosLogPK(pretamoRenovado, pretamoNuevo, fechaHora);
    }

    public RenovadosLogPK getRenovadosLogPK() {
        return renovadosLogPK;
    }

    public void setRenovadosLogPK(RenovadosLogPK renovadosLogPK) {
        this.renovadosLogPK = renovadosLogPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (renovadosLogPK != null ? renovadosLogPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RenovadosLog)) {
            return false;
        }
        RenovadosLog other = (RenovadosLog) object;
        if ((this.renovadosLogPK == null && other.renovadosLogPK != null) || (this.renovadosLogPK != null && !this.renovadosLogPK.equals(other.renovadosLogPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.RenovadosLog[ renovadosLogPK=" + renovadosLogPK + " ]";
    }

    @Override
    public RenovadosLogPK obtenerIdentificador() {
        return this.renovadosLogPK;
    }

}
