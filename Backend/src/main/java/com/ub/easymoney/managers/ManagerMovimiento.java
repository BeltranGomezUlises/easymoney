/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers;

import com.ub.easymoney.daos.DaoMovimiento;
import com.ub.easymoney.entities.Movimiento;
import com.ub.easymoney.utils.commons.ManagerSQL;
import com.ub.easymoney.models.filtros.FiltroMovimientos;
import java.util.List;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerMovimiento extends ManagerSQL<Movimiento, Integer> {

    public ManagerMovimiento() {
        super(new DaoMovimiento());
    }

    /**
     * Obtiene los movimientos de un cobrador
     *
     * @param cobradorId identificador del cobrador a obtener sus movimientos
     * @return lista de movimientos que pertenecen al cobrador
     */
    public List<Movimiento> movimientosDelCobrador(final int cobradorId) {
        return new DaoMovimiento().movimientosDelCobrador(cobradorId);
    }

    /**
     * Consulta al repositorios los movimientos que complen con el filtro proporsionado
     *
     * @param filtro modelo con las propiedades a filtrar
     * @return lista de movimientos que cumplen con el filtro
     */
    public List<Movimiento> movimientos(FiltroMovimientos filtro) {
        return new DaoMovimiento().movimientos(filtro);
    }
}
