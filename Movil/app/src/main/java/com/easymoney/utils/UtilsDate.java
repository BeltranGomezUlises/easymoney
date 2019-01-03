package com.easymoney.utils;

/**
 * Created by ulises on 11/01/18.
 */


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * utileria de conversion de fechas, herramienta de manejo de fechas donde incluye suma de tiempo, días y conversiones diferentes formatos de presentación
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class UtilsDate {

    private static final SimpleDateFormat SDF_D_MM_YYYY;
    private static final SimpleDateFormat SDF_D_MM_YYYY_HH_MM;

    static {
        SDF_D_MM_YYYY = new SimpleDateFormat("d/MM/yyyy");
        SDF_D_MM_YYYY.setTimeZone(TimeZone.getTimeZone("UTC"));
        SDF_D_MM_YYYY_HH_MM = new SimpleDateFormat("d/MM/yyyy HH:mm");
    }

    /**
     * pone los atributos de hora, minuto, segundo y milisegundo en ceros
     *
     * @param cal calendario con la fecha a modificar
     * @return fecha con las propiedades de tiempo en ceros
     */
    public static Date setTimeToZero(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * convierte date en su representacion texto en formato d/MM/yyyy
     *
     * @param date fecha a convertir
     * @return texto de la fecha
     */
    public static String format_D_MM_YYYY(Date date) {
        return SDF_D_MM_YYYY.format(date);
    }

    /**
     * convierte date en su representacion texto en formato d/MM/yyyy HH:mm
     *
     * @param date fecha a convertir
     * @return
     */
    public static String format_D_MM_YYYY_HH_MM(Date date) {
        return SDF_D_MM_YYYY_HH_MM.format(date);
    }

}
