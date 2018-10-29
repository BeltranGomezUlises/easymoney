package com.easymoney.models;

import java.util.Date;

public class ModelFiltroPrestamos {

    private Integer clienteId;
    private Integer cobradorId;
    private Date fechaPrestamoInicial;
    private Date fechaPrestamoFinal;
    private Date fechaLimiteInicial;
    private Date fechaLimiteFinal;
    private boolean acreditados;

    public boolean isAcreditados() {
        return acreditados;
    }

    public void setAcreditados(boolean acreditados) {
        this.acreditados = acreditados;
    }

    public Date getFechaPrestamoInicial() {
        return fechaPrestamoInicial;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getCobradorId() {
        return cobradorId;
    }

    public void setCobradorId(Integer cobradorId) {
        this.cobradorId = cobradorId;
    }

    public void setFechaPrestamoInicial(Date fechaPrestamoInicial) {
        this.fechaPrestamoInicial = fechaPrestamoInicial;
    }

    public Date getFechaPrestamoFinal() {
        return fechaPrestamoFinal;
    }

    public void setFechaPrestamoFinal(Date fechaPrestamoFinal) {
        this.fechaPrestamoFinal = fechaPrestamoFinal;
    }

    public Date getFechaLimiteInicial() {
        return fechaLimiteInicial;
    }

    public void setFechaLimiteInicial(Date fechaLimiteInicial) {
        this.fechaLimiteInicial = fechaLimiteInicial;
    }

    public Date getFechaLimiteFinal() {
        return fechaLimiteFinal;
    }

    public void setFechaLimiteFinal(Date fechaLimiteFinal) {
        this.fechaLimiteFinal = fechaLimiteFinal;
    }

}
