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
import com.ub.easymoney.models.filtros.FiltroPrestamo;
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

    /**
     * Persiste el prestamo asegurando la generacion de los abonos del prestamo
     *
     * @param entity entidad prestamo a persistir
     * @return retorna el prestamo persistido con sus abonos asegurados
     * @throws Exception si existe un error de I/O
     */
    @Override
    public Prestamo persist(Prestamo entity) throws Exception {
        int cantImpuesto = entity.getCantidad();
        cantImpuesto *= ((float) UtilsConfig.getPorcentajeComisionPrestamo() / 100f);

        entity.setCantidadPagar(entity.getCantidad() + cantImpuesto);
        entity.setFecha(new Date());

        GregorianCalendar cal = new GregorianCalendar();
        Date d = cal.getTime(); //guardamos la fecha actual

        cal.add(Calendar.DAY_OF_YEAR, UtilsConfig.getDiasPlazoPrestamo());
        entity.setFechaLimite(cal.getTime());

        Prestamo p = super.persist(entity);

        ManagerAbono managerAbono = new ManagerAbono();
        List<Abono> listaAbonos = new ArrayList<>();
        Abono abono;

        cal.setTime(d);
        cal.add(Calendar.DAY_OF_YEAR, 1); //primer dia de abono es el dia siguiente del prestamo
        int diasPlazo = UtilsConfig.getDiasPlazoPrestamo();
        for (int i = 0; i < diasPlazo; i++) {
            abono = new Abono(p.getId(), cal.getTime());
            abono.setCantidad(p.getCantidadPagar() / diasPlazo);
            abono.setAbonado(false);
            abono.setMulta(new Multa(new MultaPK(p.getId(), cal.getTime()), 0, ""));
            listaAbonos.add(abono);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        entity.setAbonos(listaAbonos);
        super.update(entity);

        return p;//To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Obtiene los totales generales de un prestamo en particular
     *
     * @param prestamoId identificador del prestamo
     * @return modelo con los totales generales de un prestamo
     * @throws Exception si existe un error de I/O
     */
    public ModeloPrestamoTotales totalesPrestamo(int prestamoId) throws Exception {
        ModeloPrestamoTotales mPrestamoTotales = new ModeloPrestamoTotales();

        Prestamo prestamo = this.findOne(prestamoId);
        List<Abono> abonos = prestamo.getAbonos();
        mPrestamoTotales.setTotalAbonado(abonos.stream().filter(t -> t.isAbonado()).mapToInt(t -> t.getCantidad()).sum());
        mPrestamoTotales.setTotalMultado(abonos.stream().filter(t -> t.isAbonado()).map(t -> t.getMulta()).mapToInt(t -> t.getMulta()).sum());
        mPrestamoTotales.setTotalRecuperado(mPrestamoTotales.getTotalAbonado() + mPrestamoTotales.getTotalMultado());
        mPrestamoTotales.setPorcentajePagado((int) ((float) mPrestamoTotales.getTotalAbonado() / (float) prestamo.getCantidadPagar() * 100f));
        mPrestamoTotales.setPorPagar(prestamo.getCantidadPagar() - mPrestamoTotales.getTotalAbonado());
        return mPrestamoTotales;
    }

    /**
     * Obtiene los totales generales de un conjunto de abonos, pertenecientes a un conjunto de prestamos
     *
     * @param abonos lista de abonos a obtener sus totales generales
     * @return modelo de totales generales con los valores generados con la lista proporcionada
     * @throws Exception si existe un error de I/O
     */
    public ModeloPrestamoTotalesGenerales totalesPrestamosGenerales(final List<Abono> abonos) throws Exception {
        List<Pair<Integer, Integer>> abonosMultas = abonos.stream()
                .filter(a -> a.isAbonado())
                .map(e -> new Pair<>(e.getCantidad(), e.getMulta().getMulta()))
                .collect(toList());

        final int totalAbonado = abonosMultas.stream().mapToInt(e -> e.getOne()).sum();
        final int totalMultado = abonosMultas.stream().mapToInt(e -> e.getTwo()).sum();
        final int totalPrestamo = this.stream().mapToInt(p -> p.getCantidad()).sum();
        final int totalAPagar = this.stream().mapToInt(p -> p.getCantidadPagar()).sum();
        final int totalRecuperado = totalAbonado + totalMultado;
        final int capital = totalRecuperado - totalPrestamo;
        final float porcentajeAbonado = (((float) totalAbonado / (float) totalAPagar) * 100f);

        return new ModeloPrestamoTotalesGenerales(totalPrestamo, totalAbonado, totalMultado, totalRecuperado, capital, porcentajeAbonado);
    }

    /**
     * Obtiene los totales generales de todos los prestamos
     *
     * @return modelo con los totales generales
     * @throws Exception si existe un error de I/O
     */
    public ModeloPrestamoTotalesGenerales totalesPrestamosGenerales() throws Exception {

        List<Pair<Integer, Integer>> abonosMultas = new DaoAbono().stream()
                .filter(a -> a.isAbonado())
                .map(e -> new Pair<>(e.getCantidad(), e.getMulta().getMulta()))
                .collect(toList());

        final int totalAbonado = abonosMultas.stream().mapToInt(e -> e.getOne()).sum();
        final int totalMultado = abonosMultas.stream().mapToInt(e -> e.getTwo()).sum();
        final int totalPrestamo = this.stream().mapToInt(p -> p.getCantidad()).sum();
        final int totalAPagar = this.stream().mapToInt(p -> p.getCantidadPagar()).sum();
        final int totalRecuperado = totalAbonado + totalMultado;
        final int capital = totalRecuperado - totalPrestamo;
        final float porcentajeAbonado = (((float) totalAbonado / (float) totalAPagar) * 100f);

        return new ModeloPrestamoTotalesGenerales(totalPrestamo, totalAbonado, totalMultado, totalRecuperado, capital, porcentajeAbonado);
    }

    /**
     * consulta todos los prestamos que cumplen con las propiedades del objeto filtro
     *
     * @param filtro objecto con las propiedaes a filtrar
     * @return lista de prestamos filtrados
     */
    public List<Prestamo> findAll(FiltroPrestamo filtro) {        
        return new DaoPrestamo().findAll(filtro);
    }

    public List<Prestamo> prestamosDelCobrador(final int cobradorId) {
        return new DaoPrestamo().prestamosDelCobrador(cobradorId);
    }

    /**
     * Genera el resultado de los totales generales de los prestamos segun un filtrado
     *
     * @param filtro objeto con las propiedades a filtrar en los prestamos
     * @return modelo del resultado de los totales generales del prestamo
     */
    public ModeloPrestamoTotalesGenerales totalesPrestamosGenerales(FiltroPrestamo filtro) throws Exception {
        List<Prestamo> prestamos = this.findAll(filtro);
        List<Abono> abonos = new ArrayList<>();
        prestamos.forEach(p -> abonos.addAll(p.getAbonos()));
        return totalesPrestamosGenerales(abonos);
    }

    public Prestamo persistPruebaDentroMes(Prestamo entity) throws Exception {
        int cantImpuesto = entity.getCantidad();
        cantImpuesto *= ((float) UtilsConfig.getPorcentajeComisionPrestamo() / 100f);
        entity.setCantidadPagar(entity.getCantidad() + cantImpuesto);
        
        Date fechaPrueba = new Date( System.currentTimeMillis() - ((24*60*60*1000)*2)); //hace 2 dias
        
        entity.setFecha(fechaPrueba);
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fechaPrueba);
        
        cal.add(Calendar.DAY_OF_YEAR, UtilsConfig.getDiasPlazoPrestamo());
        entity.setFechaLimite(cal.getTime());
        Prestamo p = super.persist(entity);
        ManagerAbono managerAbono = new ManagerAbono();
        List<Abono> listaAbonos = new ArrayList<>();
        Abono abono;
        
        cal.setTime(fechaPrueba);
        cal.add(Calendar.DAY_OF_YEAR, 1); //primer dia de abono es el dia siguiente del prestamo
        int diasPlazo = UtilsConfig.getDiasPlazoPrestamo();
        for (int i = 0; i < diasPlazo; i++) {
            abono = new Abono(p.getId(), cal.getTime());
            abono.setCantidad(p.getCantidadPagar() / diasPlazo);
            abono.setAbonado(false);
            abono.setMulta(new Multa(new MultaPK(p.getId(), cal.getTime()), 0, ""));
            listaAbonos.add(abono);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        entity.setAbonos(listaAbonos);
        super.update(entity);

        return p;//To change body of generated methods, choose Tools | Templates.
    }
    
    public Prestamo persistPruebaPorCerrarMes(Prestamo entity) throws Exception {
        int cantImpuesto = entity.getCantidad();
        cantImpuesto *= ((float) UtilsConfig.getPorcentajeComisionPrestamo() / 100f);
        entity.setCantidadPagar(entity.getCantidad() + cantImpuesto);
        
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, UtilsConfig.getDiasPlazoPrestamo() * -1);
        entity.setFecha(cal.getTime());
        
        cal.add(Calendar.DAY_OF_YEAR, UtilsConfig.getDiasPlazoPrestamo());
        entity.setFechaLimite(cal.getTime());
        
        Prestamo p = super.persist(entity);
        ManagerAbono managerAbono = new ManagerAbono();
        List<Abono> listaAbonos = new ArrayList<>();
        Abono abono;
        
        cal.setTime(entity.getFecha());
        cal.add(Calendar.DAY_OF_YEAR, 1); //primer dia de abono es el dia siguiente del prestamo
        int diasPlazo = UtilsConfig.getDiasPlazoPrestamo();
        for (int i = 0; i < diasPlazo; i++) {
            abono = new Abono(p.getId(), cal.getTime());
            abono.setCantidad(p.getCantidadPagar() / diasPlazo);
            abono.setAbonado(false);
            abono.setMulta(new Multa(new MultaPK(p.getId(), cal.getTime()), 0, ""));
            listaAbonos.add(abono);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        entity.setAbonos(listaAbonos);
        super.update(entity);

        return p;//To change body of generated methods, choose Tools | Templates.
    }
    
    public Prestamo persistPruebaCerrardoMes(Prestamo entity) throws Exception {
        int cantImpuesto = entity.getCantidad();
        cantImpuesto *= ((float) UtilsConfig.getPorcentajeComisionPrestamo() / 100f);
        entity.setCantidadPagar(entity.getCantidad() + cantImpuesto);
        
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, UtilsConfig.getDiasPlazoPrestamo() * -1);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        entity.setFecha(cal.getTime());
        
        cal.add(Calendar.DAY_OF_YEAR, UtilsConfig.getDiasPlazoPrestamo());
        entity.setFechaLimite(cal.getTime());
        
        Prestamo p = super.persist(entity);
        ManagerAbono managerAbono = new ManagerAbono();
        List<Abono> listaAbonos = new ArrayList<>();
        Abono abono;
        
        cal.setTime(entity.getFecha());
        cal.add(Calendar.DAY_OF_YEAR, 1); //primer dia de abono es el dia siguiente del prestamo
        int diasPlazo = UtilsConfig.getDiasPlazoPrestamo();
        for (int i = 0; i < diasPlazo; i++) {
            abono = new Abono(p.getId(), cal.getTime());
            abono.setCantidad(p.getCantidadPagar() / diasPlazo);
            abono.setAbonado(false);
            abono.setMulta(new Multa(new MultaPK(p.getId(), cal.getTime()), 0, ""));
            listaAbonos.add(abono);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        entity.setAbonos(listaAbonos);
        super.update(entity);

        return p;//To change body of generated methods, choose Tools | Templates.
    }
    
}
