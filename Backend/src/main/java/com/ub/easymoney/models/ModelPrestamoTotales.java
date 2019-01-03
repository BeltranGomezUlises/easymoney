/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

/**
 * modelo para obtener en el detalle del prestamo, cuando queremos sacar los totales de un prestamo en particular
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ModelPrestamoTotales {

    /*totales informativos*/
    private int totalAbonado;
    private int totalMultado;
    private int totalRecuperado;
    private int porcentajePagado;
    private int porPagarIrAlCorriente;
    private int porPagarLiquidar;

    /*Totales a cobrar*/
    private int totalAbonar;
    private int totalMultar;
    private int totalMultarMes;

    public int getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(int totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    public int getTotalMultado() {
        return totalMultado;
    }

    public void setTotalMultado(int totalMultado) {
        this.totalMultado = totalMultado;
    }

    public int getTotalRecuperado() {
        return totalRecuperado;
    }

    public void setTotalRecuperado(int totalRecuperado) {
        this.totalRecuperado = totalRecuperado;
    }

    public int getPorcentajePagado() {
        return porcentajePagado;
    }

    public void setPorcentajePagado(int porcentajePagado) {
        this.porcentajePagado = porcentajePagado;
    }

    public int getTotalAbonar() {
        return totalAbonar;
    }

    public void setTotalAbonar(int totalAbonar) {
        this.totalAbonar = totalAbonar;
    }

    public int getTotalMultar() {
        return totalMultar;
    }

    public void setTotalMultar(int totalMultar) {
        this.totalMultar = totalMultar;
    }

    public int getTotalMultarMes() {
        return totalMultarMes;
    }

    public void setTotalMultarMes(int totalMultarMes) {
        this.totalMultarMes = totalMultarMes;
    }

    public int getPorPagarIrAlCorriente() {
        return porPagarIrAlCorriente;
    }

    public void setPorPagarIrAlCorriente(int porPagarIrAlCorriente) {
        this.porPagarIrAlCorriente = porPagarIrAlCorriente;
    }

    public int getPorPagarLiquidar() {
        return porPagarLiquidar;
    }

    public void setPorPagarLiquidar(int porPagarLiquidar) {
        this.porPagarLiquidar = porPagarLiquidar;
    }

}
