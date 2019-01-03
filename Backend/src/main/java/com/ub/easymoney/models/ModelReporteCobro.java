/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

import java.util.Date;

/**
 * Modelo para generar reporte de cobros
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ModelReporteCobro {

    private int cobroid;
    private String cliente;
    private String usuario;
    private int prestamo;
    private int cantidad;
    private Date fecha;

    public ModelReporteCobro() {
    }

    public ModelReporteCobro(int cobroid, String cliente, String usuario, int prestamo, int cantidad, Date fecha) {
        this.cobroid = cobroid;
        this.cliente = cliente;
        this.usuario = usuario;
        this.prestamo = prestamo;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getCobroid() {
        return cobroid;
    }

    public void setCobroid(int cobroid) {
        this.cobroid = cobroid;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(int prestamo) {
        this.prestamo = prestamo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
