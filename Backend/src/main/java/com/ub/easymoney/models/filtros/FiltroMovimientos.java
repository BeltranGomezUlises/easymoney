/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models.filtros;

import java.util.Date;

/**
 * Modelo de filtro para la consulta de movimientos
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class FiltroMovimientos {

    private String nombre;
    private Date fechaInicial;
    private Date fechaFinal;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

}
