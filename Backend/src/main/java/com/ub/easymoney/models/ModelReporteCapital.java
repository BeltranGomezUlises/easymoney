/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

/**
 * Modelo de respuesta para el reporte de capital fisico en una fecha
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ModelReporteCapital {

    private long totalRecuperado;
    private long totalMovsIngreso;
    private long totalMovsEgreso;

    public ModelReporteCapital() {
    }

    public ModelReporteCapital(long totalRecuperado, long totalMovsIngreso, long totalMovsEgreso) {
        this.totalRecuperado = totalRecuperado;
        this.totalMovsIngreso = totalMovsIngreso;
        this.totalMovsEgreso = totalMovsEgreso;
    }

    public long getTotalRecuperado() {
        return totalRecuperado;
    }

    public void setTotalRecuperado(long totalRecuperado) {
        this.totalRecuperado = totalRecuperado;
    }

    public long getTotalMovsIngreso() {
        return totalMovsIngreso;
    }

    public void setTotalMovsIngreso(long totalMovsIngreso) {
        this.totalMovsIngreso = totalMovsIngreso;
    }

    public long getTotalMovsEgreso() {
        return totalMovsEgreso;
    }

    public void setTotalMovsEgreso(long totalMovsEgreso) {
        this.totalMovsEgreso = totalMovsEgreso;
    }

}
