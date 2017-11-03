/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities.negocio;

import com.ub.easymoney.entities.commons.commons.IEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Entity
@Table(name = "multa")
public class Multa implements Serializable, IEntity<MultaPK> {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MultaPK multaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "multa")
    private int multa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "multa_des")
    private String multaDes;
    @JoinColumns({
        @JoinColumn(name = "prestamo", referencedColumnName = "prestamo", insertable = false, updatable = false)
        , @JoinColumn(name = "fecha", referencedColumnName = "fecha", insertable = false, updatable = false)})
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Abono abono;

    public Multa() {
    }

    public Multa(MultaPK multaPK) {
        this.multaPK = multaPK;
    }

    public Multa(MultaPK multaPK, int multa, String multaDes) {
        this.multaPK = multaPK;
        this.multa = multa;
        this.multaDes = multaDes;
    }

    public Multa(int prestamo, Date fecha) {
        this.multaPK = new MultaPK(prestamo, fecha);
    }

    public MultaPK getMultaPK() {
        return multaPK;
    }

    public void setMultaPK(MultaPK multaPK) {
        this.multaPK = multaPK;
    }

    public int getMulta() {
        return multa;
    }

    public void setMulta(int multa) {
        this.multa = multa;
    }

    public String getMultaDes() {
        return multaDes;
    }

    public void setMultaDes(String multaDes) {
        this.multaDes = multaDes;
    }

    public Abono getAbono() {
        return abono;
    }

    public void setAbono(Abono abono) {
        this.abono = abono;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (multaPK != null ? multaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Multa)) {
            return false;
        }
        Multa other = (Multa) object;
        if ((this.multaPK == null && other.multaPK != null) || (this.multaPK != null && !this.multaPK.equals(other.multaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.negocio.Multa[ multaPK=" + multaPK + " ]";
    }   

    @Override
    public MultaPK obtenerIdentificador() {
        return multaPK;
    }
    
}
