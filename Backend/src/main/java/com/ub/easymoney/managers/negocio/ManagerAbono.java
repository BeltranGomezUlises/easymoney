/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.negocio.DaoAbono;
import com.ub.easymoney.entities.negocio.Abono;
import com.ub.easymoney.entities.negocio.AbonoPK;
import com.ub.easymoney.managers.commons.ManagerSQL;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerAbono extends ManagerSQL<Abono, AbonoPK> {

    public ManagerAbono() {
        super(new DaoAbono());
    }

    public List<Abono> abonosDelPrestamo(int prestamoId) {
        return this.stream()
                .where(a -> a.getAbonoPK().getPrestamo() == prestamoId)
                .sorted((a1, a2) -> a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha()))
                .collect(toList());
    }
}
