/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models.filtros;

import java.util.Date;

/**
 * objecto de filtro para consulta de prestamos
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class FiltroPrestamo {

    private String nombreCliente;
    private String nombreCobrador;
    private Date fechaPrestamoInicial;
    private Date fechaPrestamoFinal;
    private Date fechaLimiteInicial;
    private Date fechaLimiteFinal;
    private boolean acreditados;

    public boolean isAcreditados() {
        return acreditados;
    }

    public void setAcreditados(boolean acreditados) {
        this.acreditados = acreditados;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreCobrador() {
        return nombreCobrador;
    }

    public void setNombreCobrador(String nombreCobrador) {
        this.nombreCobrador = nombreCobrador;
    }

    public Date getFechaPrestamoInicial() {
        return fechaPrestamoInicial;
    }

    public void setFechaPrestamoInicial(Date fechaPrestamoInicial) {
        this.fechaPrestamoInicial = fechaPrestamoInicial;
    }

    public Date getFechaPrestamoFinal() {
        return fechaPrestamoFinal;
    }

    public void setFechaPrestamoFinal(Date fechaPrestamoFinal) {
        this.fechaPrestamoFinal = fechaPrestamoFinal;
    }

    public Date getFechaLimiteInicial() {
        return fechaLimiteInicial;
    }

    public void setFechaLimiteInicial(Date fechaLimiteInicial) {
        this.fechaLimiteInicial = fechaLimiteInicial;
    }

    public Date getFechaLimiteFinal() {
        return fechaLimiteFinal;
    }

    public void setFechaLimiteFinal(Date fechaLimiteFinal) {
        this.fechaLimiteFinal = fechaLimiteFinal;
    }

}
