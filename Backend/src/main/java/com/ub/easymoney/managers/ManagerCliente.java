/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers;

import com.ub.easymoney.daos.DaoCliente;
import com.ub.easymoney.entities.Cliente;
import com.ub.easymoney.utils.commons.ManagerSQL;
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
