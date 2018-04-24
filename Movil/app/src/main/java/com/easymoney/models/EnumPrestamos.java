package com.easymoney.models;

/**
 * Created by ulises on 29/03/2018.
 */

public enum EnumPrestamos {
    /**
     * identifica que los prestamos no deben de tener un cobro el dia de hoy
     */
    POR_COBRAR_HOY,
    /**
     * identifica que los prestamos deben de ser solo por cobrar
     */
    POR_COBRAR,
    /**
     * identifica que los prestamos deben de ser sin menu_ingresos_egresos
     */
    TODOS
}
