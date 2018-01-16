package com.easymoney.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class Abono implements Serializable {

    protected AbonoPK abonoPK;
    private int cantidad;
    private boolean abonado;
    private Multa multa;
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

}

