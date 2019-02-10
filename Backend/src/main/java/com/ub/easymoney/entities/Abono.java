/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ub.easymoney.utils.commons.IEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
@Entity
@Table(name = "abono")
@NamedQueries({
    @NamedQuery(name = "Abono.findAll", query = "SELECT a FROM Abono a")
    , @NamedQuery(name = "Abono.findByPrestamo", query = "SELECT a FROM Abono a WHERE a.abonoPK.prestamo = :prestamo")
    , @NamedQuery(name = "Abono.findByFecha", query = "SELECT a FROM Abono a WHERE a.abonoPK.fecha = :fecha")
    , @NamedQuery(name = "Abono.findByCantidad", query = "SELECT a FROM Abono a WHERE a.cantidad = :cantidad")
    , @NamedQuery(name = "Abono.findByAbonado", query = "SELECT a FROM Abono a WHERE a.abonado = :abonado")
    , @NamedQuery(name = "Abono.findByMulta", query = "SELECT a FROM Abono a WHERE a.multa = :multa")
    , @NamedQuery(name = "Abono.findByMultaDes", query = "SELECT a FROM Abono a WHERE a.multaDes = :multaDes")})
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
    @Column(name = "multa")
    @NotNull
    private int multa;
    @Size(max = 255)
    @Column(name = "multa_des")
    private String multaDes;
    @JsonIgnore
    @JoinColumn(name = "prestamo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
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

    public Abono(int prestamo, Date fecha, int cantidad, boolean abonado, int multa, String multaDes) {
        this.abonoPK = new AbonoPK(prestamo, fecha);
        this.cantidad = cantidad;
        this.abonado = abonado;
        this.multa = multa;
        this.multaDes = multaDes;
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

    public boolean getAbonado() {
        return abonado;
    }

    public void setAbonado(boolean abonado) {
        this.abonado = abonado;
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
        return this.abonoPK;
    }

}
