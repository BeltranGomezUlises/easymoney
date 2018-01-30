package com.easymoney.models;

/**
 * Created by ulises on 25/01/2018.
 */

public class Config {

    private int cantidadMultaMes;
    private int cantidadMultaDiaria;
    private String contraDefault;
    private int diasPrestamo;
    private int porcentajeInteresPrestamo;
    private Integer id;

    public Config() {
    }

    public int getCantidadMultaMes() {
        return cantidadMultaMes;
    }

    public void setCantidadMultaMes(int cantidadMultaMes) {
        this.cantidadMultaMes = cantidadMultaMes;
    }

    public int getCantidadMultaDiaria() {
        return cantidadMultaDiaria;
    }

    public void setCantidadMultaDiaria(int cantidadMultaDiaria) {
        this.cantidadMultaDiaria = cantidadMultaDiaria;
    }

    public String getContraDefault() {
        return contraDefault;
    }

    public void setContraDefault(String contraDefault) {
        this.contraDefault = contraDefault;
    }

    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    public void setDiasPrestamo(int diasPrestamo) {
        this.diasPrestamo = diasPrestamo;
    }

    public int getPorcentajeInteresPrestamo() {
        return porcentajeInteresPrestamo;
    }

    public void setPorcentajeInteresPrestamo(int porcentajeInteresPrestamo) {
        this.porcentajeInteresPrestamo = porcentajeInteresPrestamo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
