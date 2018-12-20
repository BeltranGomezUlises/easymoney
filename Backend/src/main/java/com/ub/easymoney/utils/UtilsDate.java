package com.ub.easymoney.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * utileria de conversion de fechas, herramienta de manejo de fechas donde incluye suma de tiempo, días y conversiones diferentes formatos de presentación
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class UtilsDate {

    public static Date dateWithoutTime(){
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);        
        return cal.getTime();
    }
    
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
