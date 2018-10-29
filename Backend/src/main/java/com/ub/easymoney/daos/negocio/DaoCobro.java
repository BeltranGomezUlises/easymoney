/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos.negocio;

import com.ub.easymoney.daos.commons.DaoSQLFacade;
import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.entities.negocio.Capital;
import com.ub.easymoney.entities.negocio.Cliente;
import com.ub.easymoney.entities.negocio.Cobro;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.models.ModelAbonarPrestamo;
import com.ub.easymoney.models.ModelReporteCobro;
import com.ub.easymoney.utils.UtilsDB;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
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
     * Construye la entidad del cobro, persite el cobro y actualiza el prestamo a la distrubucion del pago que se le ralizó
     *
     * @param model modelo contenedor de los valores necesarios
     * @return prestamo actualizado
     * @throws Exception si existe un error de I/O
     */
    public Prestamo generarAbonoPrestamo(ModelAbonarPrestamo model) throws Exception {
        EntityManager em = this.getEMInstance();
        //construir el cobro del abono
        Cobro cobro = new Cobro();
        cobro.setCantidad(model.getCantidadAbono());
        cobro.setFecha(new Date());
        cobro.setCliente(em.find(Cliente.class, model.getClienteId()));
        cobro.setCobrador(em.find(Usuario.class, model.getCobradorId()));
        cobro.setPrestamo(em.find(Prestamo.class, model.getPrestamo().getId()));

        em.getTransaction().begin();
        //persistir el cobro y actualizare el prestamo
        em.persist(cobro);
        em.merge(model.getPrestamo());
        
        Capital capital = em.createQuery("SELECT c FROM Capital c", Capital.class).getSingleResult();
        capital.setCapital(capital.getCapital() + model.getCantidadAbono());
        em.merge(capital);
        
        em.getTransaction().commit();
        em.close();
        return model.getPrestamo();
    }

    /**
     * Consulta los cobros realizados por un cobrador con un rango de fechas
     *
     * @param cobradorId id del cobrador
     * @param fechaInicial fecha limite inferior
     * @param fechaFinal fecha limite superior
     * @return
     */
    public List<Cobro> cobrosDelCobrador(int cobradorId, Date fechaInicial, Date fechaFinal) {
        //construccion de consulta
        String query = "SELECT NEW com.ub.easymoney.models.ModelReporteCobro("
                + "c.id, "
                + "c.cliente.nombre, "
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
        String query = "SELECT NEW com.ub.easymoney.models.ModelReporteCobro(c.id, c.cliente.nombre, c.cobrador.nombre, c.prestamo.id, c.cantidad, c.fecha) From Cobro c WHERE c.cliente.id = :clienteId";
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
                + "c.cliente.nombre, "
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
                + "c.cliente.nombre, "
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
