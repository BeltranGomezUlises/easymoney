/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelCargarPrestamos {

    private int id;
    private String cliente;
    private String cobrador;
    private int cantidad;
    private int cantidadAPagar;
    private Date fecha;
    private Date fechaLimite;
    private EstadoPrestamo estado;

    public ModelCargarPrestamos() {
    }

    public ModelCargarPrestamos(int id, String cliente, String cobrador, int cantidad, int cantidadAPagar, Date fechaPrestamo, Date fecchaLimite) {
        this.id = id;
        this.cliente = cliente;
        this.cobrador = cobrador;
        this.cantidad = cantidad;
        this.cantidadAPagar = cantidadAPagar;
        this.fecha = fechaPrestamo;
        this.fechaLimite = fecchaLimite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCobrador() {
        return cobrador;
    }

    public void setCobrador(String cobrador) {
        this.cobrador = cobrador;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadAPagar() {
        return cantidadAPagar;
    }

    public void setCantidadAPagar(int cantidadAPagar) {
        this.cantidadAPagar = cantidadAPagar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fecchaLimite) {
        this.fechaLimite = fecchaLimite;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public static enum EstadoPrestamo {
        VENCIDO, ACREDITADO, OK
    }
}
