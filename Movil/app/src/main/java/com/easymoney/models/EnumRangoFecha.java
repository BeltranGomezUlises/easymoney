package com.easymoney.models;

/**
 * Enumarador para identificar las acciones de menu_ingresos_egresos por fecha
 * Created by ulises on 27/03/2018.
 */
public enum EnumRangoFecha {
    /**
     * identifíca del dia actual a 7 dias hacia el pasado
     */
    DIAS7,
    /**
     * identifíca como los últimos 15 dias que han pasado
     */
    DIAS15,
    /**
     * identifica como el ultimo mes apartir de hoy
     */
    DIAS30,
    /**
     * sin menu_ingresos_egresos de fecha
     */
    TODOS
}
