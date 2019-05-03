/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos;

import com.ub.easymoney.utils.commons.DaoSQLFacade;
import com.ub.easymoney.entities.Abono;
import com.ub.easymoney.entities.Capital;
import com.ub.easymoney.entities.Prestamo;
import com.ub.easymoney.entities.RenovadosLog;
import com.ub.easymoney.models.filtros.FiltroPrestamo;
import com.ub.easymoney.utils.UtilsDB;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManager;
import org.jinq.jpa.JPAJinqStream;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class DaoPrestamo extends DaoSQLFacade<Prestamo, Integer> {

    public DaoPrestamo() {
        super(UtilsDB.getEMFactoryCG(), Prestamo.class, Integer.class);
    }

    @Override
    public void persist(Prestamo entity) throws InvalidParameterException, Exception {
        EntityManager em = this.getEMInstance();
        Calendar cal = new GregorianCalendar();
        Capital capital = em.createQuery("SELECT c FROM Capital c", Capital.class).getSingleResult();
        if (capital.getCapital() < entity.getCantidad()) {
            throw new InvalidParameterException("Capital insuficiente");
        }
        capital.setCapital(capital.getCapital() - entity.getCantidad());
        em.getTransaction().begin();
        em.persist(entity);
        em.flush(); //para obtener el id del prestamo

        List<Abono> listaAbonos = new ArrayList<>();

        cal.setTime(entity.getFecha());
        cal.add(Calendar.DAY_OF_YEAR, 1); //primer dia de abono es el dia siguiente del prestamo
        final int diasPlazo = new DaoConfig().findFirst().getDiasPrestamo();
        final int cantidadPagarPorAbono = entity.getCantidadPagar() / diasPlazo;
        for (int i = 0; i < diasPlazo; i++) {
            Abono abono = new Abono(entity.getId(), cal.getTime());
            abono.setCantidad(cantidadPagarPorAbono);
            abono.setAbonado(false);
            abono.setMulta(0);
            abono.setMultaDes("");
            listaAbonos.add(abono);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        entity.setCobroDiario(cantidadPagarPorAbono);
        entity.setAbonoList(listaAbonos);
        entity.setMontoRedondeo(entity.getCantidadPagar() - diasPlazo * entity.getCobroDiario());
        em.merge(entity);
        em.merge(capital); //generar la actualización del capital                
        em.getTransaction().commit();
        em.close();
    }

    /**
     * consulta los prestamos que cumplen con las propiedades del objeto filtro
     *
     * @param filtro objecto con las propiedades a filtrar
     * @return lista de prestamos filtrados
     */
    public List<Prestamo> findAll(final FiltroPrestamo filtro) {
        Integer clienteId = filtro.getClienteId();
        Integer cobradorId = filtro.getCobradorId();

        Date fechaPrestamoInicial = filtro.getFechaPrestamoInicial();
        Date fechaPrestamoFinal = filtro.getFechaPrestamoFinal();

        Date fechaLimiteInicial = filtro.getFechaLimiteInicial();
        Date fechaLimiteFinal = filtro.getFechaLimiteFinal();

        JPAJinqStream<Prestamo> stream = this.stream();
        if (clienteId != null) {
            stream = stream.where(t -> t.getCliente().getId().equals(clienteId));
        }
        if (cobradorId != null) {
            stream = stream.where(t -> t.getCobrador().getId().equals(cobradorId));
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
                .where(t -> t.getCobrador().getId().equals(cobradorId))
                .filter(t -> !t.saldado())
                .collect(toList());
    }

    /**
     * retorna los prestamos asignados al cliente y que aun se pueden cobrar
     *
     * @param clienteId identificador del cliente
     * @return lista de prestamos por cobrar
     */
    public List<Prestamo> prestamosDelCliente(final int clienteId) {
        return this.stream()
                .where(t -> t.getCliente().getId().equals(clienteId))
                .filter(t -> !t.saldado())
                .collect(toList());
    }

    /**
     * consulta los prestamos asignado a un cobrador en especifico
     *
     * @param cobradorId identificador del cobrador
     * @return lista de prestamos asignados al cobrador
     */
    public List<Prestamo> prestamosDelCobrador(int cobradorId) {
        EntityManager em = this.getEMInstance();
        List<Prestamo> prestamosDelCobrador = em
                .createQuery("SELECT t FROM Prestamo t WHERE t.cobrador.id = :cobradorId", Prestamo.class)
                .setParameter("cobradorId", cobradorId)
                .getResultList();
        em.close();
        return prestamosDelCobrador;
    }

    /**
     * persiste la actualizacion del prestamoa a renovar y el nuevo prestamo
     *
     * @param prestamoRenovar
     * @param nuevoPrestamo
     * @return cantidad a entregar de dinero fisico al cliente
     * @throws Exception
     */
    public int renovarPrestamo(Prestamo prestamoRenovar, Prestamo nuevoPrestamo, int cantidadPorSaldar) throws InvalidParameterException, Exception {
        EntityManager em = this.getEMInstance();
        Capital capital = em.createQuery("SELECT c FROM Capital c", Capital.class).getSingleResult();

        for (Abono abono : prestamoRenovar.getAbonoList()) {            
            abono.setAbonado(true);
            abono.setCantidad(prestamoRenovar.getCobroDiario());
        }
        if (!(capital.getCapital() >= nuevoPrestamo.getCantidad() - cantidadPorSaldar)) {
            throw new InvalidParameterException("Capital insuficiente");
        }
        em.getTransaction().begin();
        em.merge(prestamoRenovar);

        Calendar cal = new GregorianCalendar();
        em.persist(nuevoPrestamo);
        em.flush(); //para obtener el id del prestamo
        List<Abono> listaAbonos = new ArrayList<>();
        cal.setTime(nuevoPrestamo.getFecha());
        cal.add(Calendar.DAY_OF_YEAR, 1); //primer dia de abono es el dia siguiente del prestamo
        final int diasPlazo = new DaoConfig().findFirst().getDiasPrestamo();
        final int cantidadPagarPorAbono = nuevoPrestamo.getCantidadPagar() / diasPlazo;
        for (int i = 0; i < diasPlazo; i++) {
            Abono abono = new Abono(nuevoPrestamo.getId(), cal.getTime(), cantidadPagarPorAbono, false, 0, "");
            listaAbonos.add(abono);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        nuevoPrestamo.setCobroDiario(cantidadPagarPorAbono);
        nuevoPrestamo.setAbonoList(listaAbonos);
        em.merge(nuevoPrestamo);

        //generar la actualización del capital        
        capital.setCapital(capital.getCapital() - (nuevoPrestamo.getCantidad() - cantidadPorSaldar));
        em.merge(capital);

        RenovadosLog rl = new RenovadosLog(prestamoRenovar.getId(), nuevoPrestamo.getId(), new Date());
        em.persist(rl);
        
        em.getTransaction().commit();
        em.close();
        return (nuevoPrestamo.getCantidad() - cantidadPorSaldar);
    }
}
