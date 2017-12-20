/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos.negocio;

import com.ub.easymoney.daos.commons.DaoSQLFacade;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.models.filtros.FiltroPrestamo;
import com.ub.easymoney.utils.UtilsDB;
import java.util.Date;
import java.util.List;
import org.jinq.jpa.JPAJinqStream;
import static com.ub.easymoney.utils.UtilsValidations.isNotNullOrEmpty;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class DaoPrestamo extends DaoSQLFacade<Prestamo, Integer> {
    
    public DaoPrestamo() {
        super(UtilsDB.getEMFactoryCG(), Prestamo.class, Integer.class);
    }

    /**
     * consulta los prestamos que cumplen con las propiedades del objeto filtro
     * @param filtro objecto con las propiedades a filtrar
     * @return  lista de prestamos filtrados
     */
    public List<Prestamo> findAll(FiltroPrestamo filtro) {
        
        String nombreCliente = filtro.getNombreCliente().toLowerCase();
        String nombreCobrador = filtro.getNombreCobrador().toLowerCase();
        
        Date fechaPrestamoInicial = filtro.getFechaPrestamoInicial();
        Date fechaPrestamoFinal = filtro.getFechaPrestamoFinal();
        
        Date fechaLimiteInicial = filtro.getFechaLimiteInicial();
        Date fechaLimiteFinal = filtro.getFechaLimiteFinal();
                
        JPAJinqStream<Prestamo> stream = this.stream();
 
        if (isNotNullOrEmpty(nombreCliente)) {
            stream = stream.where( t -> t.getCliente().getNombre().toLowerCase().contains(nombreCliente));
        }        
        if (isNotNullOrEmpty(nombreCobrador)) {
            stream = stream.where( t -> t.getCobrador().getNombre().toLowerCase().contains(nombreCobrador));
        }
        if (fechaPrestamoInicial != null) {
            stream = stream.where( t -> !t.getFecha().before(fechaPrestamoInicial));
        }
        if (fechaPrestamoFinal != null) {
            stream = stream.where( t -> t.getFecha().before(fechaPrestamoFinal));
        }
        if (fechaLimiteInicial != null) {
            stream = stream.where( t -> !t.getFechaLimite().before(fechaLimiteInicial));
        }
        if (fechaLimiteFinal != null) {
            stream = stream.where( t -> t.getFechaLimite().before(fechaLimiteFinal));
        }
        //filtrar los que esten acreditados en un 100%
        List<Prestamo> prestamosFiltrados = stream.toList();
        if (!filtro.isAcreditados()) {
            prestamosFiltrados = prestamosFiltrados.stream()
                    .filter( t -> {
                        float totalAbonado = t.getAbonos().stream().filter( a -> a.isAbonado()).mapToInt( a -> a.getCantidad()).sum();
                        float porcentajeAbonado = (totalAbonado / (float) t.getCantidadPagar() * 100f);
                        return porcentajeAbonado < 100;                                
                    }).collect(toList());
        }
        
        return prestamosFiltrados;
        
    }
     
}
