/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities;

import com.ub.easymoney.utils.commons.IEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
@Entity
@Table(name = "capital")
@NamedQueries({
    @NamedQuery(name = "Capital.findAll", query = "SELECT c FROM Capital c")
    , @NamedQuery(name = "Capital.findById", query = "SELECT c FROM Capital c WHERE c.id = :id")
    , @NamedQuery(name = "Capital.findByCapital", query = "SELECT c FROM Capital c WHERE c.capital = :capital")})
public class Capital implements Serializable, IEntity<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capital")
    private int capital;

    public Capital() {
    }

    public Capital(Integer id) {
        this.id = id;
    }

    public Capital(Integer id, int capital) {
        this.id = id;
        this.capital = capital;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
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
        if (!(object instanceof Capital)) {
            return false;
        }
        Capital other = (Capital) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.negocio.Capital[ id=" + id + " ]";
    }

    @Override
    public Integer obtenerIdentificador() {
        return id;
    }

}
