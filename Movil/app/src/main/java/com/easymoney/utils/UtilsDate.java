package com.easymoney.utils;

/**
 * Created by ulises on 11/01/18.
 */


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * utileria de conversion de fechas, herramienta de manejo de fechas donde incluye suma de tiempo, días y conversiones diferentes formatos de presentación
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class UtilsDate {

    private static final SimpleDateFormat SDF_D_MM_YYYY = new SimpleDateFormat("d/MM/yyyy");
    private static final SimpleDateFormat SDF_D_MM_YYYY_HH_MM = new SimpleDateFormat("d/MM/yyyy HH:mm");
    private static final SimpleDateFormat SDF_HM = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat SDF_NDOW = new SimpleDateFormat("EEEE");
    private static final SimpleDateFormat SDF_UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

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
     * pone los atributos de hora, minuto, segundo y milisegundo en ceros
     *
     * @param date fecha a modificar sus atributos de tiempo
     * @return fecha con los atributos de tiempo en ceros
     */
    public static Date setTimeToZero(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
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

    /**
     * convierte date en su representacion texto en formato HH:mm
     *
     * @param date fecha a convertir
     * @return
     */
    public static String format_HH_MM(Date date) {
        return SDF_HM.format(date);
    }

    /**
     * convierte date en su representacion texto en formato yyyy-MM-dd'T'HH:mm:ss.SSSZ
     *
     * @param date fecha a convertir
     * @return
     */
    public static String sdfUTC(Date date) {
        return SDF_UTC.format(date);
    }

    /**
     * obtiene el nombre del dia de la semana de la fecha otorgada en el parametro "date" en formato texto "EEEE"
     *
     * @param date       fecha de la cuel obtener el día
     * @param DateFormat formato de texto en el que se encuentra la fecha
     * @return texto con el nombre del dáa de la semana
     * @throws ParseException
     */
    public static String nameDayOfWeek(String date, String DateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
        return nameDayOfWeek(sdf.parse(date));
    }

    /**
     * obtiene el nombre del dia de la semana de la fecha otorgada en el parametro date en formato texto "EEEE"
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String nameDayOfWeek(String date) throws ParseException {
        return nameDayOfWeek(SDF_NDOW.parse(date));
    }

    /**
     * obtiene el nombre del dia de la semana de la fecha otorgada en el parametro date en formato texto "EEEE"
     *
     * @param date
     * @return
     */
    public static String nameDayOfWeek(Date date) {
        return SDF_NDOW.format(date);
    }

    /**
     * Obtiene la cantidad de dias que existen de diferencia entre una fecha y otra
     *
     * @param fechaInicial fecha menor desde la cual contar los dias
     * @param fechaFinal   fecha mayor hasta la cual contar los dias
     * @return numero de dias
     */
    public static long diasEntreFechas(final Date fechaInicial, final Date fechaFinal) {
        long diffMillis = fechaFinal.getTime() - fechaInicial.getTime();
        return TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
    }

}
