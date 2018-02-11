package com.easymoney.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class MultaPK implements Serializable {

    private int prestamo;
    private Date fecha;

    public MultaPK() {
    }

    public MultaPK(int prestamo, Date fecha) {
        this.prestamo = prestamo;
        this.fecha = fecha;
    }

    public int getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(int prestamo) {
        this.prestamo = prestamo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultaPK multaPK = (MultaPK) o;

        if (prestamo != multaPK.prestamo) return false;
        return fecha.equals(multaPK.fecha);
    }

    @Override
    public int hashCode() {
        int result = prestamo;
        result = 31 * result + fecha.hashCode();
        return result;
    }
}
