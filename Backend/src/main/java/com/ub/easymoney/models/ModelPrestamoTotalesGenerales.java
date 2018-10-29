/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

/**
 * modelo contenedor de los totales de los prestamos en general
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ModelPrestamoTotalesGenerales {

    public int totalPrestado;
    public int totalAbonado;
    public int totalMultado;
    public int totalRecuperado;
    public int capital;
    public float porcentajeCompletado;

    public ModelPrestamoTotalesGenerales() {
    }

    public ModelPrestamoTotalesGenerales(int totalPestado, int totalAbonado, int totalMultado, int totalRecuperado, int capital, float porcentajeCompletado) {
        this.totalPrestado = totalPestado;
        this.totalAbonado = totalAbonado;
        this.totalMultado = totalMultado;
        this.totalRecuperado = totalRecuperado;
        this.capital = capital;
        this.porcentajeCompletado = porcentajeCompletado;
    }

    public int getTotalPrestado() {
        return totalPrestado;
    }

    public void setTotalPrestado(int totalPrestado) {
        this.totalPrestado = totalPrestado;
    }

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

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public float getPorcentajeCompletado() {
        return porcentajeCompletado;
    }

    public void setPorcentajeCompletado(float porcentajeCompletado) {
        this.porcentajeCompletado = porcentajeCompletado;
    }

}
