/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos.negocio;

import com.ub.easymoney.daos.commons.DaoSQLFacade;
import com.ub.easymoney.entities.negocio.Movimiento;
import com.ub.easymoney.models.filtros.FiltroMovimientos;
import com.ub.easymoney.utils.UtilsDB;
import java.util.Date;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.jinq.jpa.JPAJinqStream;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class DaoMovimiento extends DaoSQLFacade<Movimiento, Integer> {

    public DaoMovimiento() {
        super(UtilsDB.getEMFactoryCG(), Movimiento.class, Integer.class);
    }

    /**
     * filtra los movimientos de un cobrador en especifico
     *
     * @param cobradorId cobrador del cual obtener sus movimientos
     * @return lista de movimientos del cobrador
     */
    public List<Movimiento> movimientosDelCobrador(final int cobradorId) {
        return this.stream().where(mov -> mov.getUsuarioCreador().getId() == cobradorId).collect(toList());
    }

    /**
     * Consulta a base de datos los movimientos que cumplan con los campos del filtro
     *
     * @param filtro modelo con las propiedades a filtrar
     * @return lista de movimientos que cumplen con el friltro
     */
    public List<Movimiento> movimientos(FiltroMovimientos filtro) {
        JPAJinqStream<Movimiento> stream = this.stream();
        if (filtro.getNombre() != null) {
            String nombre = filtro.getNombre().toLowerCase();
            stream = stream.where(m -> m.getUsuarioCreador().getNombre().toLowerCase().contains(nombre));
        }
        if (filtro.getTipoMovimiento() != null) {
            boolean tipoMovimiento = filtro.getTipoMovimiento();
            if (tipoMovimiento) {
                stream = stream.where(m -> m.getCantidad() > 0);
            } else {
                stream = stream.where(m -> m.getCantidad() < 0);
            }
        }
        if (filtro.getCobradorId() != null) {
            Integer idCobrador = filtro.getCobradorId();
            stream = stream.where(m -> m.getUsuarioCreador().getId().equals(idCobrador));
        }
        if (filtro.getFechaFinal() != null) {
            Date fechaFinal = filtro.getFechaFinal();
            stream = stream.where(m -> m.getFecha().before(fechaFinal));
        }
        if (filtro.getFechaInicial() != null) {
            Date fechaInicial = filtro.getFechaInicial();
            stream = stream.where(m -> !m.getFecha().before(fechaInicial));
        }
        return stream.collect(toList());
    }

    /**
     * Consulta las cantidades de los movimientos con un rango de fechas
     * @param fechaInicial fecha inicial del rango
     * @param fechaFinal fecha final del rango
     * @return lista de cantidades de movimientos dentro del rango
     */
    public List<Double> cantidadesDesdeHasta(final Date fechaInicial, final Date fechaFinal) {
        return this.getEMInstance().createQuery("SELECT t.cantidad FROM Movimiento t WHERE t.fecha >= :fechaInicial AND t.fecha < :fechaFinal", Double.class)
                .setParameter("fechaInicial", fechaInicial)
                .setParameter("fechaFinal", fechaFinal)
                .getResultList();

    }

}
