package com.easymoney.models;

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
}
