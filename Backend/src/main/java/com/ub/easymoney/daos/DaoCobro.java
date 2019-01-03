/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos;

import com.ub.easymoney.utils.commons.DaoSQLFacade;
import com.ub.easymoney.entities.Cobro;
import com.ub.easymoney.models.ModelReporteCobro;
import com.ub.easymoney.utils.UtilsDB;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class DaoCobro extends DaoSQLFacade<Cobro, Integer> {

    public DaoCobro() {
        super(UtilsDB.getEMFactoryCG(), Cobro.class, Integer.class);
    }

    /**
     * Consulta los cobros realizados por un cobrador con un rango de fechas
     *
     * @param cobradorId id del cobrador
     * @param fechaInicial fecha limite inferior
     * @param fechaFinal fecha limite superior
     * @return
     */
    public List<ModelReporteCobro> cobrosDelCobrador(int cobradorId, Date fechaInicial, Date fechaFinal) {
        //construccion de consulta
        String query = "SELECT NEW com.ub.easymoney.models.ModelReporteCobro("
                + "c.id, "
                + "c.prestamo.cliente.nombre, "
                + "c.cobrador.nombre, "
                + "c.prestamo.id, "
                + "c.cantidad, "
                + "c.fecha) "
                + "From Cobro c "
                + "WHERE c.cobrador.id = :cobradorId";
        //consulta dinamica
        if (fechaInicial != null) {
            query += " AND c.fecha >= :fechaInicial";
        }
        if (fechaFinal != null) {
            query += " AND c.fecha < :fechaFinal";
        }
        //creacion de Query
        Query q = this.getEMInstance().createQuery(query, ModelReporteCobro.class)
                .setParameter("cobradorId", cobradorId);
        //parametros dinamicos
        if (fechaInicial != null) {
            q.setParameter("fechaInicial", fechaInicial);
        }
        if (fechaFinal != null) {
            q.setParameter("fechaFinal", fechaFinal);
        }
        return q.getResultList();
    }

    /**
     * Consulta las cobros realizados a un cliente con un rango de fechas
     *
     * @param clienteId id del cliente
     * @param fechaInicial fecha limite inferior
     * @param fechaFinal fecha limite superior
     * @return
     */
    public List<ModelReporteCobro> cobrosDelCliente(int clienteId, Date fechaInicial, Date fechaFinal) {
        //construccion de consulta
        
        String query = "SELECT NEW com.ub.easymoney.models.ModelReporteCobro(c.id, c.prestamo.cliente.nombre, c.cobrador.nombre, c.prestamo.id, c.cantidad, c.fecha)"
                + " From Cobro c WHERE c.prestamo.cliente.id = :clienteId";
        //consulta dinamica
        if (fechaInicial != null) {
            query += " AND c.fecha >= :fechaInicial";
        }
        if (fechaFinal != null) {
            query += " AND c.fecha < :fechaFinal";
        }
        //creacion de Query
        Query q = this.getEMInstance().createQuery(query, ModelReporteCobro.class)
                .setParameter("clienteId", clienteId);
        //parametros dinamicos
        if (fechaInicial != null) {
            q.setParameter("fechaInicial", fechaInicial);
        }
        if (fechaFinal != null) {
            q.setParameter("fechaFinal", fechaFinal);
        }
        return q.getResultList();
    }

    /**
     * Consulta los cobros de un prestamo con un rango de fechas
     *
     * @param agrupadorId prestamo al cual consultar los cobros
     * @param fechaInicial fecha inicial de consulta
     * @param fechaFinal fecha final de consulta
     * @return lista de cobros encontrados
     */
    public List<Cobro> cobrosDelPrestamo(Integer agrupadorId, Date fechaInicial, Date fechaFinal) {
        //construccion de consulta
        String query = "SELECT NEW com.ub.easymoney.models.ModelReporteCobro("
                + "c.id, "
                + "c.prestamo.cliente.nombre, "
                + "c.cobrador.nombre, "
                + "c.prestamo.id, "
                + "c.cantidad, "
                + "c.fecha) "
                + "From Cobro c WHERE c.prestamo.id = :agrupadorId";
        //consulta dinamica
        if (fechaInicial != null) {
            query += " AND c.fecha >= :fechaInicial";
        }
        if (fechaFinal != null) {
            query += " AND c.fecha < :fechaFinal";
        }
        //creacion de Query
        Query q = this.getEMInstance().createQuery(query, ModelReporteCobro.class)
                .setParameter("agrupadorId", agrupadorId);
        //parametros dinamicos
        if (fechaInicial != null) {
            q.setParameter("fechaInicial", fechaInicial);
        }
        if (fechaFinal != null) {
            q.setParameter("fechaFinal", fechaFinal);
        }
        return q.getResultList();
    }

    /**
     * Consulta los cobros con un rango de fechas
     *
     * @param fechaInicial fecha inicial de consulta
     * @param fechaFinal fecha final de consulta
     * @return lista de cobros encontrados
     */
    public List<Cobro> cobrosDesdeHasta(Date fechaInicial, Date fechaFinal) {
        //construccion de consulta
        String query = "SELECT NEW com.ub.easymoney.models.ModelReporteCobro("
                + "c.id, "
                + "c.prestamo.cliente.nombre, "
                + "c.cobrador.nombre, "
                + "c.prestamo.id, "
                + "c.cantidad, "
                + "c.fecha) "
                + "From Cobro c";
        //consulta dinamica        
        boolean paramInicial = true;
        if (fechaInicial != null) {
            query += " WHERE c.fecha >= :fechaInicial";
            paramInicial = false;
        }
        if (fechaFinal != null) {
            query += paramInicial ? " WHERE c.fecha < :fechaFinal" : " AND c.fecha < :fechaFinal";
        }
        //creacion de Query
        Query q = this.getEMInstance().createQuery(query, ModelReporteCobro.class);
        //parametros dinamicos
        if (fechaInicial != null) {
            q.setParameter("fechaInicial", fechaInicial);
        }
        if (fechaFinal != null) {
            q.setParameter("fechaFinal", fechaFinal);
        }
        return q.getResultList();
    }

    /**
     * Consulta la cantidad total cobrada con un rango de fechas
     *
     * @param fechaInicial fecha inicial de rango
     * @param fechaFinal fecha final del rango
     * @return suma de las cantidades de los cobros encontrados en ese rango de fechas
     */
    public long totalCobradoDesdeHasta(Date fechaInicial, Date fechaFinal) {
        long res = 0;
        try {
            res = this.getEMInstance().createQuery("SELECT SUM(c.cantidad) "
                    + "FROM Cobro c WHERE c.fecha >= :fechaInicial AND c.fecha < :fechaFinal", Long.class)
                    .setParameter("fechaInicial", fechaInicial)
                    .setParameter("fechaFinal", fechaFinal)
                    .getSingleResult();
        } catch (Exception e) {
        }
        return res;
    }

}
