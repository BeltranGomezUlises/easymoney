/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities.negocio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ub.easymoney.entities.commons.commons.IEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Entity
@Table(name = "abono")
public class Abono implements Serializable, IEntity<AbonoPK> {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AbonoPK abonoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "abonado")
    private boolean abonado;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "abono", fetch = FetchType.EAGER)
    private Multa multa;
    @JoinColumn(name = "prestamo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Prestamo prestamo1;

    public Abono() {
    }

    public Abono(AbonoPK abonoPK) {
        this.abonoPK = abonoPK;
    }

    public Abono(AbonoPK abonoPK, int cantidad, boolean abonado) {
        this.abonoPK = abonoPK;
        this.cantidad = cantidad;
        this.abonado = abonado;
    }

    public Abono(int prestamo, Date fecha) {
        this.abonoPK = new AbonoPK(prestamo, fecha);
    }

    public AbonoPK getAbonoPK() {
        return abonoPK;
    }

    public void setAbonoPK(AbonoPK abonoPK) {
        this.abonoPK = abonoPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isAbonado() {
        return abonado;
    }

    public void setAbonado(boolean abonado) {
        this.abonado = abonado;
    }

    public Multa getMulta() {
        return multa;
    }

    public void setMulta(Multa multa) {
        this.multa = multa;
    }

    @JsonIgnore
    public Prestamo getPrestamo1() {
        return prestamo1;
    }

    public void setPrestamo1(Prestamo prestamo1) {
        this.prestamo1 = prestamo1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (abonoPK != null ? abonoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abono)) {
            return false;
        }
        Abono other = (Abono) object;
        if ((this.abonoPK == null && other.abonoPK != null) || (this.abonoPK != null && !this.abonoPK.equals(other.abonoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.negocio.Abono[ abonoPK=" + abonoPK + " ]";
    }

    @Override
    public AbonoPK obtenerIdentificador() {
        return abonoPK;
    }

}
