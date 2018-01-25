package com.easymoney.models;

/**
 * Created by ulises on 22/01/2018.
 */

public class ModelTotalAPagar {
    private int totalAbonar;
    private int totalMultar;

    public ModelTotalAPagar(int totalAbonar, int totalMultar) {
        this.totalAbonar = totalAbonar;
        this.totalMultar = totalMultar;
    }

    public int getTotalAbonar() {
        return totalAbonar;
    }

    public void setTotalAbonar(int totalAbonar) {
        this.totalAbonar = totalAbonar;
    }

    public int getTotalMultar() {
        return totalMultar;
    }

    public void setTotalMultar(int totalMultar) {
        this.totalMultar = totalMultar;
    }

    public int getTotalPagar() {
        return totalAbonar + totalMultar;
    }


}
