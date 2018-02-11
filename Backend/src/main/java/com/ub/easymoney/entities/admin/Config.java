/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities.admin;

import com.ub.easymoney.entities.commons.commons.IEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Entity
@Table(name = "config")
public class Config implements Serializable, IEntity {

    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_multa_mes")
    private int cantidadMultaMes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_multa_diaria")
    private int cantidadMultaDiaria;

    @Size(max = 2147483647)
    @Column(name = "contra_default")
    private String contraDefault;

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dias_prestamo")
    private int diasPrestamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje_interes_prestamo")
    private int porcentajeInteresPrestamo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Config() {
    }

    public Config(Integer id) {
        this.id = id;
    }

    public Config(Integer id, int diasPrestamo, int porcentajeInteresPrestamo) {
        this.id = id;
        this.diasPrestamo = diasPrestamo;
        this.porcentajeInteresPrestamo = porcentajeInteresPrestamo;
    }

    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    public void setDiasPrestamo(int diasPrestamo) {
        this.diasPrestamo = diasPrestamo;
    }

    public int getPorcentajeInteresPrestamo() {
        return porcentajeInteresPrestamo;
    }

    public void setPorcentajeInteresPrestamo(int porcentajeInteresPrestamo) {
        this.porcentajeInteresPrestamo = porcentajeInteresPrestamo;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Config)) {
            return false;
        }
        Config other = (Config) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.admin.Config[ id=" + id + " ]";
    }

    @Override
    public Object obtenerIdentificador() {
        return id;
    }

    public String getContraDefault() {
        return contraDefault;
    }

    public void setContraDefault(String contraDefault) {
        this.contraDefault = contraDefault;
    }

    public int getCantidadMultaMes() {
        return cantidadMultaMes;
    }

    public void setCantidadMultaMes(int cantidadMultaMes) {
        this.cantidadMultaMes = cantidadMultaMes;
    }

    public int getCantidadMultaDiaria() {
        return cantidadMultaDiaria;
    }

    public void setCantidadMultaDiaria(int cantidadMultaDiaria) {
        this.cantidadMultaDiaria = cantidadMultaDiaria;
    }
    
}
