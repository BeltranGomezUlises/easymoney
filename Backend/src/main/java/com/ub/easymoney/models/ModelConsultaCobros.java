/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

import java.util.Date;

/**
 * Modelo generico para consultar cobros con un rango de fechas y un agrupador de filtro, como el clienteId, prestamoId, o cobradorId
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ModelConsultaCobros {

    private Integer agrupadorId;
    private Date fechaInicial;
    private Date fechaFinal;

    public ModelConsultaCobros() {
    }

    public Integer getAgrupadorId() {
        return agrupadorId;
    }

    public void setAgrupadorId(Integer agrupadorId) {
        this.agrupadorId = agrupadorId;
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
