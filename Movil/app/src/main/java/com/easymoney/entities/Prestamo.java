/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easymoney.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Date fecha;
    private int cantidad;
    private int cantidadPagar;
    private Date fechaLimite;
    private int cobroDiario;
    private Cliente cliente;
    private Usuario cobrador;
    private List<Cobro> cobroList;
    private List<Abono> abonoList;

    public Prestamo() {
    }

    public Prestamo(Integer id) {
        this.id = id;
    }

    public Prestamo(Integer id, Date fecha, int cantidad, int cantidadPagar, Date fechaLimite, int cobroDiario) {
        this.id = id;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.cantidadPagar = cantidadPagar;
        this.fechaLimite = fechaLimite;
        this.cobroDiario = cobroDiario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadPagar() {
        return cantidadPagar;
    }

    public void setCantidadPagar(int cantidadPagar) {
        this.cantidadPagar = cantidadPagar;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public int getCobroDiario() {
        return cobroDiario;
    }

    public void setCobroDiario(int cobroDiario) {
        this.cobroDiario = cobroDiario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getCobrador() {
        return cobrador;
    }

    public void setCobrador(Usuario cobrador) {
        this.cobrador = cobrador;
    }

    public List<Cobro> getCobroList() {
        return cobroList;
    }

    public void setCobroList(List<Cobro> cobroList) {
        this.cobroList = cobroList;
    }

    public List<Abono> getAbonoList() {
        return abonoList;
    }

    public void setAbonoList(List<Abono> abonoList) {
        this.abonoList = abonoList;
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
        if (!(object instanceof Prestamo)) {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
