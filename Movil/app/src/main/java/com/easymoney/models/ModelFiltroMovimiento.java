package com.easymoney.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by ulises on 27/03/2018.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelFiltroMovimiento {

    /**
     * id del cobrador que generó el prestamo
     */
    private Integer cobradorId;
    /**
     * true si es de tipo ingreso, false si es de tipo egreso
     */
    private Boolean tipoMovimiento;
    /**
     * nombre del cobrador que genero el movimiento
     */
    private String nombre;
    /**
     * fecha inicial del menu_ingresos_egresos de fechas
     */
    private Date fechaInicial;
    /**
     * fecha final del menu_ingresos_egresos de fechas
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
