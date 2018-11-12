package com.ub.easymoney.utils;

import java.util.Date;

/**
 * utileria de conversion de fechas, herramienta de manejo de fechas donde incluye suma de tiempo, días y conversiones diferentes formatos de presentación
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class UtilsDate {

    /**
     * modelo contenedor de una fecha
     */
    public static class DateClass {

        private final Date date;

        public Date getDate() {
            return date;
        }

        public DateClass() {
            this.date = new Date();
        }

        public DateClass(Date date) {
            this.date = date;
        }
    }

}
