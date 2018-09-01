package com.easymoney.models;

public class ModelDistribucionDeAbono {
    final private int abonado;
    final private int multado;
    final private int multadoMes;

    public ModelDistribucionDeAbono(int abonado, int multado, int multadoMes) {
        this.abonado = abonado;
        this.multado = multado;
        this.multadoMes = multadoMes;
    }

    public int getAbonado() {
        return abonado;
    }

    public int getMultado() {
        return multado;
    }

    public int getMultadoMes() {
        return multadoMes;
    }
}
