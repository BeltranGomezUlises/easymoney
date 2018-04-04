/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos.negocio;

import com.ub.easymoney.daos.commons.DaoSQLFacade;
import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.entities.negocio.Cliente;
import com.ub.easymoney.entities.negocio.Cobro;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.models.ModelAbonarPrestamo;
import com.ub.easymoney.utils.UtilsDB;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;

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

        em.getTransaction().commit();

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
        JPAJinqStream<Cobro> stream = this.stream();
        stream = stream.where(c -> c.getCobrador().getId().equals(cobradorId));
        if (fechaInicial != null) {
            stream = stream.where(c -> !c.getFecha().before(fechaInicial));
        }
        if (fechaFinal != null) {
            stream = stream.where(c -> !c.getFecha().after(fechaFinal));
        }
        return stream.toList();
    }

    /**
     * Consulta las cobros realizados a un cliente con un rango de fechas
     *
     * @param clienteId id del cliente
     * @param fechaInicial fecha limite inferior
     * @param fechaFinal fecha limite superior
     * @return
     */
    public List<Cobro> cobrosDelCliente(int clienteId, Date fechaInicial, Date fechaFinal) {
        JPAJinqStream<Cobro> stream = this.stream();
        stream = stream.where(c -> c.getCliente().getId().equals(clienteId));
        if (fechaInicial != null) {
            stream = stream.where(c -> !c.getFecha().before(fechaInicial));
        }
        if (fechaFinal != null) {
            stream = stream.where(c -> !c.getFecha().after(fechaFinal));
        }
        return stream.toList();
    }

}
