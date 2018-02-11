package com.easymoney.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class AbonoPK implements Serializable {

    private int prestamo;
    private Date fecha;

    public AbonoPK() {
    }

    public AbonoPK(int prestamo, Date fecha) {
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

        AbonoPK abonoPK = (AbonoPK) o;

        if (prestamo != abonoPK.prestamo) return false;
        return fecha.equals(abonoPK.fecha);
    }

    @Override
    public int hashCode() {
        int result = prestamo;
        result = 31 * result + fecha.hashCode();
        return result;
    }
}
