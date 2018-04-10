/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.negocio.DaoCliente;
import com.ub.easymoney.entities.negocio.Cliente;
import com.ub.easymoney.managers.commons.ManagerSQL;
import com.ub.easymoney.models.filtros.FiltroCliente;
import java.util.List;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerCliente extends ManagerSQL<Cliente, Integer> {

    public ManagerCliente() {
        super(new DaoCliente());
    }

    @Override
    public List<Cliente> findAll() throws Exception {
        List<Cliente> clientes = super.findAll();
        clientes.sort((c1, c2) -> c1.getNombre().compareTo(c2.getNombre()));
        return clientes;
    }

    /**
     * consulta los clientes que cumplan con el criterio de las propiedades del objeto filtro
     *
     * @param filtro objeto de propiedades a filtrar
     * @return lista de clientes filtrados
     * @throws Exception si ocurre una excepcion de consulta
     */
    public List<Cliente> findAll(FiltroCliente filtro) throws Exception {
        DaoCliente daoCliente = new DaoCliente();
        return daoCliente.findAll(filtro);
    }

}
