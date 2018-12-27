package com.easymoney.entities;

import java.io.Serializable;

public class DistribucionCobro implements Serializable {

    private Integer id;
    private int abonado;
    private int multado;
    private int multadoPostPlazo;
    private int totalAbonado;
    private int totalMultado;
    private int totalRecuperado;
    private int porcentajePagado;
    private int porPagarIrAlCorriente;
    private int porPagarLiquidar;
    private int totalAbonar;
    private int totalMultar;
    private int totalMultarMes;

    public DistribucionCobro() {
    }

    public DistribucionCobro(Integer id) {
        this.id = id;
    }

    public DistribucionCobro(Integer id, int abonado, int multado, int multadoPostPlazo,
                             int totalAbonado, int totalMultado, int totalRecuperado,
                             int porcentajePagado, int porPagarIrAlCorriente, int porPagarLiquidar,
                             int totalAbonar, int totalMultar, int totalMultarMes) {
        this.id = id;
        this.abonado = abonado;
        this.multado = multado;
        this.multadoPostPlazo = multadoPostPlazo;
        this.totalAbonado = totalAbonado;
        this.totalMultado = totalMultado;
        this.totalRecuperado = totalRecuperado;
        this.porcentajePagado = porcentajePagado;
        this.porPagarIrAlCorriente = porPagarIrAlCorriente;
        this.porPagarLiquidar = porPagarLiquidar;
        this.totalAbonar = totalAbonar;
        this.totalMultar = totalMultar;
        this.totalMultarMes = totalMultarMes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAbonado() {
        return abonado;
    }

    public void setAbonado(int abonado) {
        this.abonado = abonado;
    }

    public int getMultado() {
        return multado;
    }

    public void setMultado(int multado) {
        this.multado = multado;
    }

    public int getMultadoPostPlazo() {
        return multadoPostPlazo;
    }

    public void setMultadoPostPlazo(int multadoPostPlazo) {
        this.multadoPostPlazo = multadoPostPlazo;
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

    public int getPorcentajePagado() {
        return porcentajePagado;
    }

    public void setPorcentajePagado(int porcentajePagado) {
        this.porcentajePagado = porcentajePagado;
    }

    public int getPorPagarIrAlCorriente() {
        return porPagarIrAlCorriente;
    }

    public void setPorPagarIrAlCorriente(int porPagarIrAlCorriente) {
        this.porPagarIrAlCorriente = porPagarIrAlCorriente;
    }

    public int getPorPagarLiquidar() {
        return porPagarLiquidar;
    }

    public void setPorPagarLiquidar(int porPagarLiquidar) {
        this.porPagarLiquidar = porPagarLiquidar;
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

    public int getTotalMultarMes() {
        return totalMultarMes;
    }

    public void setTotalMultarMes(int totalMultarMes) {
        this.totalMultarMes = totalMultarMes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistribucionCobro)) {
            return false;
        }
        DistribucionCobro other = (DistribucionCobro) object;
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.DistribucionCobro[ id=" + id + " ]";
    }

}
