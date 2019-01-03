/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easymoney.entities;

import java.io.Serializable;

public class Config implements Serializable{

    private static final long serialVersionUID = 1L;
    private int diasPrestamo;
    private int porcentajeInteresPrestamo;
    private Integer id;
    private String contraDefault;
    private int cantidadMultaMes;
    private int cantidadMultaDiaria;

    public Config() {
    }

    public Config(Integer id) {
        this.id = id;
    }

    public Config(Integer id, int diasPrestamo, int porcentajeInteresPrestamo, int cantidadMultaMes, int cantidadMultaDiaria) {
        this.id = id;
        this.diasPrestamo = diasPrestamo;
        this.porcentajeInteresPrestamo = porcentajeInteresPrestamo;
        this.cantidadMultaMes = cantidadMultaMes;
        this.cantidadMultaDiaria = cantidadMultaDiaria;
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

}
