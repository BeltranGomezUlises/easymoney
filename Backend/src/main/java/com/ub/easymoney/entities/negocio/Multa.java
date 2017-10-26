/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities.negocio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ub.easymoney.entities.commons.commons.IEntity;
import com.webcohesion.enunciate.metadata.Ignore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * entidad de una multa aplicada a un abono de un prestamo
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Entity
@Table(name = "multa")
@NamedQueries({
    @NamedQuery(name = "Multa.findAll", query = "SELECT m FROM Multa m")
    , @NamedQuery(name = "Multa.findByPrestamo", query = "SELECT m FROM Multa m WHERE m.multaPK.prestamo = :prestamo")
    , @NamedQuery(name = "Multa.findByFecha", query = "SELECT m FROM Multa m WHERE m.multaPK.fecha = :fecha")
    , @NamedQuery(name = "Multa.findByMulta", query = "SELECT m FROM Multa m WHERE m.multa = :multa")
    , @NamedQuery(name = "Multa.findByMultaDes", query = "SELECT m FROM Multa m WHERE m.multaDes = :multaDes")})
public class Multa implements Serializable, IEntity<MultaPK> {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MultaPK multaPK;
    @Column(name = "multa")
    private Integer multa;
    @Size(max = 2147483647)
    @Column(name = "multa_des")
    private String multaDes;
    @JoinColumns({
        @JoinColumn(name = "prestamo", referencedColumnName = "prestamo", insertable = false, updatable = false)
        , @JoinColumn(name = "fecha", referencedColumnName = "fecha", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    @Ignore
    private Abono abono;

    public Multa() {
    }

    public Multa(MultaPK multaPK) {
        this.multaPK = multaPK;
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

    public Integer getMulta() {
        return multa;
    }

    public void setMulta(Integer multa) {
        this.multa = multa;
    }

    public String getMultaDes() {
        return multaDes;
    }

    public void setMultaDes(String multaDes) {
        this.multaDes = multaDes;
    }

    @JsonIgnore
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
    public MultaPK getId() {
        return multaPK;
    }
    
}
