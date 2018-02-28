package com.easymoney.models;

/**
 * Created by ulises on 22/01/2018.
 */

public class ModelTotalAPagar {
    private int totalAbonar;
    private int totalMultar;
    private int totalMultarMes;
    private int ajusteDePago;

    public ModelTotalAPagar(int totalAbonar, int totalMultar, int totalMultarMes, int ajusteDePago) {
        this.totalAbonar = totalAbonar;
        this.totalMultar = totalMultar;
        this.totalMultarMes = totalMultarMes;
        this.ajusteDePago = ajusteDePago;
    }

    public int getAjusteDePago() {
        return ajusteDePago;
    }

    public void setAjusteDePago(int ajusteDePago) {
        this.ajusteDePago = ajusteDePago;
    }

    public int getTotalMultarMes() {
        return totalMultarMes;
    }

    public void setTotalMultarMes(int totalMultarMes) {
        this.totalMultarMes = totalMultarMes;
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
        return totalAbonar + totalMultar + totalMultarMes + ajusteDePago;
    }


}
