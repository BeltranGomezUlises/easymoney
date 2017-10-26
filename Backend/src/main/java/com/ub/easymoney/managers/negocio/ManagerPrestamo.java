/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.negocio.DaoPrestamo;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.commons.ManagerSQL;
import com.ub.easymoney.utils.UtilsConfig;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerPrestamo extends ManagerSQL<Prestamo, Integer> {

    public ManagerPrestamo() {
        super(new DaoPrestamo());
    }

    @Override
    public Prestamo persist(Prestamo entity) throws Exception {
        int cantImpuesto = entity.getCantidad();
        cantImpuesto *= ((float) UtilsConfig.getPorcentajeComisionPrestamo() / 100f);

        entity.setCantidadPagar(entity.getCantidad() + cantImpuesto);
        entity.setFecha(new Date());

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, UtilsConfig.getDiasPlazoPrestamo());
        entity.setFechaLimite(cal.getTime());

        return super.persist(entity); //To change body of generated methods, choose Tools | Templates.
    }

}
