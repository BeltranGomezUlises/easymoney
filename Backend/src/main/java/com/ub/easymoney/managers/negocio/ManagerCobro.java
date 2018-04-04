/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.negocio.DaoCobro;
import com.ub.easymoney.entities.negocio.Cobro;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.commons.ManagerSQL;
import com.ub.easymoney.models.ModelAbonarPrestamo;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ManagerCobro extends ManagerSQL<Cobro, Integer> {

    public ManagerCobro() {
        super(new DaoCobro());
    }

    /**
     * Genera en base de datos el cobro y actualiza la distribucion de pagos del prestamo
     *
     * @param model modelo contenedor de los datos necesarios
     * @return prestamo actualizado
     * @throws Exception si existe un error de persitencia
     */
    public Prestamo generarAbonoPrestamo(ModelAbonarPrestamo model) throws Exception {
        return new DaoCobro().generarAbonoPrestamo(model);
    }

}
