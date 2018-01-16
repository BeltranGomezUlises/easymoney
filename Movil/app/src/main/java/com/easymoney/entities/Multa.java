package com.easymoney.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class Multa implements Serializable {


    protected MultaPK multaPK;
    private int multa;
    private String multaDes;
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

}
