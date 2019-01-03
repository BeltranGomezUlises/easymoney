/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos;

import com.ub.easymoney.utils.commons.DaoSQLFacade;
import com.ub.easymoney.entities.Cliente;
import com.ub.easymoney.entities.Prestamo;
import com.ub.easymoney.models.ModelSaldoPrestamos;
import com.ub.easymoney.models.filtros.FiltroCliente;
import com.ub.easymoney.utils.UtilsDB;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.jinq.jpa.JPAJinqStream;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class DaoCliente extends DaoSQLFacade<Cliente, Integer> {

    public DaoCliente() {
        super(UtilsDB.getEMFactoryCG(), Cliente.class, Integer.class);
    }

    /**
     * consulta los clientes que cumplen con el criterio del filtro proporcionado
     *
     * @param filtro filtro con las propiedades a filtrar
     * @return lista de cliente filtrados
     */
    public List<Cliente> findAll(FiltroCliente filtro) {
        String nombreCliente = filtro.getNombre().toLowerCase();
        String apodoCliente = filtro.getApodo().toLowerCase();

        JPAJinqStream<Cliente> stream = this.stream();
        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            stream = stream.where(t -> t.getNombre().toLowerCase().contains(nombreCliente));
        }
        if (apodoCliente != null && !apodoCliente.isEmpty()) {
            stream = stream.where(t -> t.getApodo().toLowerCase().contains(apodoCliente));
        }

        return stream
                .sortedBy(u -> u.getNombre())
                .collect(toList());
    }
    
    public List<ModelSaldoPrestamos> saldoPrestamosCliente(int clienteId) {
        List<Prestamo> prestamosDelCliente = this.<Prestamo>createQuery("SELECT p FROM Prestamo p WHERE p.cliente.id = :clienteId", Prestamo.class)
                .setParameter("clienteId", clienteId)
                .getResultList();
        List<ModelSaldoPrestamos> modelos = new ArrayList<>();
        
        prestamosDelCliente.forEach((p) -> {
            int abonado = p.getAbonoList().stream()
                    .filter(a -> a.getAbonado())
                    .mapToInt(a -> a.getCantidad())
                    .sum();
            modelos.add(new ModelSaldoPrestamos(p.getId(), p.getCantidadPagar(), abonado));
        });
        
        return modelos;
    }

}
