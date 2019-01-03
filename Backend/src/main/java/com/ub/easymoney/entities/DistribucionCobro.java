/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
@Entity
@Table(name = "distribucion_cobro")
@NamedQueries({
    @NamedQuery(name = "DistribucionCobro.findAll", query = "SELECT d FROM DistribucionCobro d")})
public class DistribucionCobro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "abonado")
    private int abonado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "multado")
    private int multado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "multado_post_plazo")
    private int multadoPostPlazo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_abonado")
    private int totalAbonado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_multado")
    private int totalMultado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_recuperado")
    private int totalRecuperado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje_pagado")
    private int porcentajePagado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "por_pagar_ir_al_corriente")
    private int porPagarIrAlCorriente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "por_pagar_liquidar")
    private int porPagarLiquidar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_abonar")
    private int totalAbonar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_multar")
    private int totalMultar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_multar_mes")
    private int totalMultarMes;
    @JsonIgnore
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Cobro cobro;

    public DistribucionCobro() {
    }

    public DistribucionCobro(Integer id) {
        this.id = id;
    }

    public DistribucionCobro(Integer id, int abonado, int multado, int multadoPostPlazo, int totalAbonado, int totalMultado, int totalRecuperado, int porcentajePagado, int porPagarIrAlCorriente, int porPagarLiquidar, int totalAbonar, int totalMultar, int totalMultarMes) {
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

    public Cobro getCobro() {
        return cobro;
    }

    public void setCobro(Cobro cobro) {
        this.cobro = cobro;
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
