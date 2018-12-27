/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.entities;

import com.ub.easymoney.utils.commons.IEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
@Entity
@Table(name = "prestamo")
@NamedQueries({
    @NamedQuery(name = "Prestamo.findAll", query = "SELECT p FROM Prestamo p")
    , @NamedQuery(name = "Prestamo.findById", query = "SELECT p FROM Prestamo p WHERE p.id = :id")
    , @NamedQuery(name = "Prestamo.findByFecha", query = "SELECT p FROM Prestamo p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Prestamo.findByCantidad", query = "SELECT p FROM Prestamo p WHERE p.cantidad = :cantidad")
    , @NamedQuery(name = "Prestamo.findByCantidadPagar", query = "SELECT p FROM Prestamo p WHERE p.cantidadPagar = :cantidadPagar")
    , @NamedQuery(name = "Prestamo.findByFechaLimite", query = "SELECT p FROM Prestamo p WHERE p.fechaLimite = :fechaLimite")
    , @NamedQuery(name = "Prestamo.findByCobroDiario", query = "SELECT p FROM Prestamo p WHERE p.cobroDiario = :cobroDiario")})
public class Prestamo implements Serializable, IEntity<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_pagar")
    private int cantidadPagar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_limite")
    @Temporal(TemporalType.DATE)
    private Date fechaLimite;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cobro_diario")
    private int cobroDiario;
    @JoinColumn(name = "cliente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "cobrador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario cobrador;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "prestamo")
    private List<Cobro> cobroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prestamo1")
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

    @Override
    public String toString() {
        return "com.ub.easymoney.entities.negocio.Prestamo[ id=" + id + " ]";
    }

    @Override
    public Integer obtenerIdentificador() {
        return id;
    }

}
