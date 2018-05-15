/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos.negocio;

import com.ub.easymoney.daos.commons.DaoSQLFacade;
import com.ub.easymoney.entities.negocio.Abono;
import com.ub.easymoney.entities.negocio.Capital;
import com.ub.easymoney.entities.negocio.Multa;
import com.ub.easymoney.entities.negocio.MultaPK;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.negocio.ManagerAbono;
import com.ub.easymoney.models.filtros.FiltroPrestamo;
import com.ub.easymoney.utils.UtilsConfig;
import com.ub.easymoney.utils.UtilsDB;
import java.util.Date;
import java.util.List;
import org.jinq.jpa.JPAJinqStream;
import static com.ub.easymoney.utils.UtilsValidations.isNotNullOrEmpty;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManager;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class DaoPrestamo extends DaoSQLFacade<Prestamo, Integer> {

    public DaoPrestamo() {
        super(UtilsDB.getEMFactoryCG(), Prestamo.class, Integer.class);
    }

    @Override
    public void persist(Prestamo entity) throws Exception {
        EntityManager em = this.getEMInstance();
        ManagerAbono managerAbono = new ManagerAbono();
        Calendar cal = new GregorianCalendar();

        em.getTransaction().begin();

        em.persist(entity);
        em.flush(); //para obtener el id del prestamo

        List<Abono> listaAbonos = new ArrayList<>();
        Abono abono;

        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 1); //primer dia de abono es el dia siguiente del prestamo
        final int diasPlazo = UtilsConfig.getDiasPlazoPrestamo();
        final int cantidadPagarPorAbono = entity.getCantidadPagar() / diasPlazo;
        for (int i = 0; i < diasPlazo; i++) {
            abono = new Abono(entity.getId(), cal.getTime());
            abono.setCantidad(cantidadPagarPorAbono);
            abono.setAbonado(false);
            abono.setMulta(new Multa(new MultaPK(entity.getId(), cal.getTime()), 0, ""));
            listaAbonos.add(abono);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        entity.setCobroDiario(cantidadPagarPorAbono);
        entity.setAbonos(listaAbonos);
        em.merge(entity);
        //generar la actualización del capital        
        Capital capital = em.createQuery("SELECT c FROM Capital c", Capital.class).getSingleResult();
        capital.setCapital(capital.getCapital() - entity.getCantidad());
        em.merge(capital);

        em.getTransaction().commit();
    }

    /**
     * consulta los prestamos que cumplen con las propiedades del objeto filtro
     *
     * @param filtro objecto con las propiedades a filtrar
     * @return lista de prestamos filtrados
     */
    public List<Prestamo> findAll(final FiltroPrestamo filtro) {

        String nombreCliente = filtro.getNombreCliente().toLowerCase();
        String nombreCobrador = filtro.getNombreCobrador().toLowerCase();

        Date fechaPrestamoInicial = filtro.getFechaPrestamoInicial();
        Date fechaPrestamoFinal = filtro.getFechaPrestamoFinal();

        Date fechaLimiteInicial = filtro.getFechaLimiteInicial();
        Date fechaLimiteFinal = filtro.getFechaLimiteFinal();

        JPAJinqStream<Prestamo> stream = this.stream();

        if (isNotNullOrEmpty(nombreCliente)) {
            stream = stream.where(t -> t.getCliente().getNombre().toLowerCase().contains(nombreCliente));
        }
        if (isNotNullOrEmpty(nombreCobrador)) {
            stream = stream.where(t -> t.getCobrador().getNombre().toLowerCase().contains(nombreCobrador));
        }
        if (fechaPrestamoInicial != null) {
            stream = stream.where(t -> !t.getFecha().before(fechaPrestamoInicial));
        }
        if (fechaPrestamoFinal != null) {
            stream = stream.where(t -> t.getFecha().before(fechaPrestamoFinal));
        }
        if (fechaLimiteInicial != null) {
            stream = stream.where(t -> !t.getFechaLimite().before(fechaLimiteInicial));
        }
        if (fechaLimiteFinal != null) {
            stream = stream.where(t -> t.getFechaLimite().before(fechaLimiteFinal));
        }
        return stream.toList();
    }

    /**
     * retorna los prestamos asignados al cobrador y que aun se pueden cobrar
     *
     * @param cobradorId identificador del cobrador
     * @return lista de prestamos por cobrar
     */
    public List<Prestamo> prestamosPorCobrar(final int cobradorId) {
        return this.stream()
                //buscar los que pertenescan al cobrador
                .where(t -> t.getCobrador().getId().equals(cobradorId))
                //filtrar los que no este 100% abonados
                .filter(t -> {
                    float totalAbonado = t.getAbonos().stream().filter(a -> a.isAbonado()).mapToInt(a -> a.getCantidad()).sum();
                    float porcentajeAbonado = (totalAbonado / (float) t.getCantidadPagar() * 100f);
                    return porcentajeAbonado < 100;
                }).collect(toList());
    }

    /**
     * consulta los prestamos asignado a un cobrador en especifico
     *
     * @param cobradorId identificador del cobrador
     * @return lista de prestamos asignados al cobrador
     */
    public List<Prestamo> prestamosDelCobrador(int cobradorId) {
        return getEMInstance().createQuery("SELECT t FROM Prestamo t WHERE t.cobrador.id = :cobradorId", Prestamo.class)
                .setParameter("cobradorId", cobradorId)
                .getResultList();
    }

    /**
     * persiste la actualizacion del prestamoa a renovar y el nuevo prestamo
     *
     * @param prestamoRenovar
     * @param nuevoPrestamo
     * @return cantidad a entregar de dinero fisico al cliente
     * @throws Exception
     */
    public int renovarPrestamo(Prestamo prestamoRenovar, Prestamo nuevoPrestamo) throws Exception {
        EntityManager em = this.getEMInstance();
        em.getTransaction().begin();

        //obtener lo que falta por abonar para dejar ese saldo 
        int cantidadPorSaldar = 0;
        for (Abono abono : prestamoRenovar.getAbonos()) {
            if (!abono.isAbonado()) {
                cantidadPorSaldar += prestamoRenovar.getCobroDiario();
            } else {
                if (abono.getCantidad() < prestamoRenovar.getCobroDiario()) {
                    cantidadPorSaldar += (prestamoRenovar.getCobroDiario() - abono.getCantidad());
                }
            }
            abono.setAbonado(true);
            abono.setCantidad(prestamoRenovar.getCobroDiario());
        }
        em.merge(prestamoRenovar);

        Calendar cal = new GregorianCalendar();

        em.persist(nuevoPrestamo);
        em.flush(); //para obtener el id del prestamo

        List<Abono> listaAbonos = new ArrayList<>();
        Abono abono;
        cal.setTime(nuevoPrestamo.getFecha());
        cal.add(Calendar.DAY_OF_YEAR, 1); //primer dia de abono es el dia siguiente del prestamo
        final int diasPlazo = UtilsConfig.getDiasPlazoPrestamo();
        final int cantidadPagarPorAbono = nuevoPrestamo.getCantidadPagar() / diasPlazo;
        for (int i = 0; i < diasPlazo; i++) {
            abono = new Abono(nuevoPrestamo.getId(), cal.getTime());
            abono.setCantidad(cantidadPagarPorAbono);
            abono.setAbonado(false);
            abono.setMulta(new Multa(new MultaPK(nuevoPrestamo.getId(), cal.getTime()), 0, ""));
            listaAbonos.add(abono);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        nuevoPrestamo.setCobroDiario(cantidadPagarPorAbono);
        nuevoPrestamo.setAbonos(listaAbonos);
        em.merge(nuevoPrestamo);

        //generar la actualización del capital        
        Capital capital = em.createQuery("SELECT c FROM Capital c", Capital.class).getSingleResult();
        capital.setCapital(capital.getCapital() - (nuevoPrestamo.getCantidad() - cantidadPorSaldar));
        em.merge(capital);

        em.getTransaction().commit();
        return (nuevoPrestamo.getCantidad() - cantidadPorSaldar);
    }
}
