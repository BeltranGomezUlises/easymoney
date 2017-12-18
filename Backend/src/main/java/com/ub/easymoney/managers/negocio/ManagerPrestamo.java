/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.negocio.DaoAbono;
import com.ub.easymoney.daos.negocio.DaoMulta;
import com.ub.easymoney.daos.negocio.DaoPrestamo;
import com.ub.easymoney.entities.negocio.Abono;
import com.ub.easymoney.entities.negocio.Multa;
import com.ub.easymoney.entities.negocio.MultaPK;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.commons.ManagerSQL;
import com.ub.easymoney.models.ModeloPrestamoTotales;
import com.ub.easymoney.models.ModeloPrestamoTotalesGenerales;
import com.ub.easymoney.utils.UtilsConfig;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.jinq.tuples.Pair;

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

    public ModeloPrestamoTotales totalesPrestamo(int prestamoId) throws Exception{
        ModeloPrestamoTotales mPrestamoTotales = new ModeloPrestamoTotales();        
        
        Prestamo prestamo = this.findOne(prestamoId);
        List<Abono> abonos = prestamo.getAbonos();
        mPrestamoTotales.setTotalAbonado(abonos.stream().filter( t -> t.getAbonado()).mapToInt(t -> t.getCantidad()).sum());
        mPrestamoTotales.setTotalMultado(abonos.stream().filter( t -> t.getAbonado()).map( t -> t.getMulta()).mapToInt( t -> t.getMulta()).sum());
        mPrestamoTotales.setTotalRecuperado(mPrestamoTotales.getTotalAbonado() + mPrestamoTotales.getTotalMultado());
        mPrestamoTotales.setPorcentajePagado((int)((float)mPrestamoTotales.getTotalAbonado() / (float)prestamo.getCantidadPagar() * 100f));
        
        return mPrestamoTotales;
    }
 
    public ModeloPrestamoTotalesGenerales totalesPrestamosGenerales() throws Exception{                
        DaoAbono daoAbono = new DaoAbono();
        DaoMulta daoMulta = new DaoMulta();
        
        List<Pair<Integer, Integer>> abonosMultas = daoAbono.stream()
                .filter(a -> a.getAbonado())
                .map(e -> new Pair<>(e.getCantidad(), e.getMulta().getMulta()))
                .collect(toList());
                          
        int totalAbonado = abonosMultas.stream().mapToInt(e -> e.getOne()).sum();
        int totalMultado = abonosMultas.stream().mapToInt(e -> e.getTwo()).sum();                
        int totalPrestamo = this.stream().mapToInt( p -> p.getCantidad()).sum();
        int totalAPagar = this.stream().mapToInt(  p -> p.getCantidadPagar()).sum();
        int totalRecuperado = totalAbonado + totalMultado;
        int capital = totalRecuperado - totalPrestamo;
        float porcentajeAbonado = (((float) totalAbonado / (float) totalAPagar ) * 100f);

        return new ModeloPrestamoTotalesGenerales(totalPrestamo, totalAbonado, totalMultado, totalRecuperado, capital, porcentajeAbonado);
    }
    
}
