package com.easymoney.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class Prestamo{


    private Integer id;
    private Date fecha;
    private int cantidad;
    private int cantidadPagar;
    private Date fechaLimite;
    private List<Abono> abonos;
    private Cliente cliente;
    private Usuario cobrador;

    public Prestamo() {
    }

    public Prestamo(Integer id) {
        this.id = id;
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

    public List<Abono> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<Abono> abonos) {
        this.abonos = abonos;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prestamo prestamo = (Prestamo) o;

        return id != null ? id.equals(prestamo.id) : prestamo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", cantidad=" + cantidad +
                ", cantidadPagar=" + cantidadPagar +
                ", fechaLimite=" + fechaLimite +
                ", abonos=" + abonos +
                ", cliente=" + cliente +
                ", cobrador=" + cobrador +
                '}';
    }
}

