/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ModelDistribucionAbono {

    private int abonado;
    private int multado;
    private int multadoPostPlazo;

    public ModelDistribucionAbono() {
    }
    
    public ModelDistribucionAbono(int abonado, int multado, int multadoPostPlazo) {
        this.abonado = abonado;
        this.multado = multado;
        this.multadoPostPlazo = multadoPostPlazo;
    }

    public int getAbonado() {
        return abonado;
    }

    public int getMultado() {
        return multado;
    }

    public int getMultadoPostPlazo() {
        return multadoPostPlazo;
    }

    public void setAbonado(int abonado) {
        this.abonado = abonado;
    }

    public void setMultado(int multado) {
        this.multado = multado;
    }

    public void setMultadoPostPlazo(int multadoPostPlazo) {
        this.multadoPostPlazo = multadoPostPlazo;
    }
    
    public void addAbono(int cantidad){
        this.abonado += cantidad;
    }
    public void addMulta(int cantidad){
        this.multado += cantidad;
    }
    public void addMultaPostPlazo(int cantidad){
        this.multadoPostPlazo += cantidad;
    }

}
