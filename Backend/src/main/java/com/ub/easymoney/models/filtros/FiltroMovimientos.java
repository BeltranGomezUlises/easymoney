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

    /**
     * id del cobrador que generó el prestamo
     */
    private Integer cobradorId;
    /**
     * true si es de tipo ingreso, false si es de tipo egreso
     */
    private Boolean tipoMovimiento;   
    /**
     * fecha inicial del filtro de fechas
     */
    private Date fechaInicial;
    /**
     * fecha final del filtro de fechas
     */
    private Date fechaFinal;

    public Boolean getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(Boolean tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getCobradorId() {
        return cobradorId;
    }

    public void setCobradorId(Integer cobradorId) {
        this.cobradorId = cobradorId;
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
