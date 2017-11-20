/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.negocio.DaoAbono;
import com.ub.easymoney.daos.negocio.DaoPrestamo;
import com.ub.easymoney.entities.admin.Config;
import com.ub.easymoney.entities.negocio.Abono;
import com.ub.easymoney.entities.negocio.Multa;
import com.ub.easymoney.entities.negocio.MultaPK;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.admin.ManagerConfig;
import com.ub.easymoney.managers.commons.ManagerSQL;
import com.ub.easymoney.utils.UtilsConfig;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
        Date d = cal.getTime(); //guardamos la fecha actual
        
        cal.add(Calendar.DAY_OF_MONTH, UtilsConfig.getDiasPlazoPrestamo());
        entity.setFechaLimite(cal.getTime());               
                       
        Prestamo p = super.persist(entity);
        
        ManagerAbono managerAbono = new ManagerAbono();
        List<Abono> listaAbonos = new ArrayList<>();
        Abono abono;
        
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, 1); //primer dia de abono es el dia siguiente del prestamo
        int diasPlazo = UtilsConfig.getDiasPlazoPrestamo();
        for (int i = 0; i < diasPlazo; i++) {
            abono = new Abono(p.getId(), cal.getTime());
            abono.setCantidad(p.getCantidadPagar() / diasPlazo);
            abono.setAbonado(false);
            abono.setMulta(new Multa(new MultaPK(p.getId(), cal.getTime()), 0, ""));            
            listaAbonos.add(abono);
            cal.add(Calendar.DAY_OF_MONTH, 1); 
        }                        
        entity.setAbonos(listaAbonos);        
        super.update(entity);                
        
        return  p;//To change body of generated methods, choose Tools | Templates.
    }

}
