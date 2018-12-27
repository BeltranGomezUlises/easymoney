/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easymoney.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class Abono implements Serializable {

    private static final long serialVersionUID = 1L;
    protected AbonoPK abonoPK;
    private int cantidad;
    private boolean abonado;
    private Integer multa;
    private String multaDes;

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

    public Abono(int prestamo, Date fecha, int cantidad, boolean abonado, Integer multa, String multaDes) {
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


}
