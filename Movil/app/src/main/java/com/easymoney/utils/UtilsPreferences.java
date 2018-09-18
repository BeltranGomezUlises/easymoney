package com.easymoney.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easymoney.entities.Usuario;
import com.easymoney.models.Config;
import com.easymoney.models.services.Login;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ulises on 9/01/18.
 */

public class UtilsPreferences {

    private static final String TOKEN = "token";
    private static final String USUARIO = "usuario";
    private static final String LOGIN = "login";
    private static final String LOGIN_TIME = "loginTime";
    private static final String CONFIG = "config";
    private static final String COBRADO = "cobrado";
    private static final String MACIMPRESORA = "macImpresora";

    private static Calendar cal;

    private static Context mContext;

    private static Calendar getCalendar() {
        if (cal == null) {
            cal = new GregorianCalendar();
        }
        return cal;
    }

    public static void setContext(final Context context) {
        mContext = context;
    }

    public static void saveToken(final String token) {
        SharedPreferences settings = mContext.getSharedPreferences(TOKEN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public static String loadToken() {
        SharedPreferences settings = mContext.getSharedPreferences(TOKEN, 0);
        return settings.getString(TOKEN, "");
    }

    public static void saveLogedUser(Usuario usuario) {
        SharedPreferences settings = mContext.getSharedPreferences(USUARIO, 0);
        SharedPreferences.Editor editor = settings.edit();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            editor.putString(USUARIO, objectMapper.writeValueAsString(usuario));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public static Usuario loadLogedUser() {
        SharedPreferences settings = mContext.getSharedPreferences(USUARIO, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(settings.getString(USUARIO, ""), Usuario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveLogin(Login.Request request) {
        SharedPreferences settings = mContext.getSharedPreferences(LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            editor.putString(LOGIN, objectMapper.writeValueAsString(request));
            editor.putLong(LOGIN_TIME, System.currentTimeMillis());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public static Login.Request loadLogin() {
        SharedPreferences settings = mContext.getSharedPreferences(LOGIN, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Login.Request req = objectMapper.readValue(settings.getString(LOGIN, ""), Login.Request.class);
            long lastLoginTime = settings.getLong(LOGIN_TIME, 0);
            long actualTime = System.currentTimeMillis();
            if ((actualTime - lastLoginTime) > (1000 * 60 * 60)){ // si ya paso una hora, regresar null
                return null;
            }else{
                return req;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveConfigs(Config config) {
        SharedPreferences settings = mContext.getSharedPreferences(CONFIG, 0);
        SharedPreferences.Editor editor = settings.edit();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            editor.putString(CONFIG, objectMapper.writeValueAsString(config));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public static Config loadConfig() {
        SharedPreferences settings = mContext.getSharedPreferences(CONFIG, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(settings.getString(CONFIG, ""), Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * almacena un prestamo como cobrado el dia actual
     *
     * @param id identificador del prestamo cobrado
     */
    public static void setPrestamoCobradoHoy(int id) {
        //cobrado.{dia del año}
        String key = COBRADO + "." + getCalendar().get(Calendar.DAY_OF_YEAR);
        SharedPreferences settings = mContext.getSharedPreferences(key, 0);

        String prestamosCobrados = settings.getString(key, "");
        if (prestamosCobrados == "") {
            prestamosCobrados += String.valueOf(id);
        } else {
            prestamosCobrados += "," + id;
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, prestamosCobrados);
        editor.commit();
    }

    /**
     * consulta si un prestmo fue cobrado ya el dia de hoy o no
     *
     * @param id identificador del prestamo
     * @return true si el prestamo fue cobrado, false si el prestamo fue cobrado
     */
    public static boolean prestamoCobradoHoy(Integer id) {
        //cobrado.{dia del año}
        String key = COBRADO + "." + getCalendar().get(Calendar.DAY_OF_YEAR);
        SharedPreferences settings = mContext.getSharedPreferences(key, 0);
        String prestamosCobrados = settings.getString(key, "");
        return prestamosCobrados.contains(String.valueOf(id));
    }

    /**
     * consulta los prestmos que fueron cobrados hoy, separados por comas
     *
     * @return string con los ids de los prestmaos separados por comas ej: "1,65,35,15"
     */
    public static String prestmosCobradosHoy() {
        String key = COBRADO + "." + getCalendar().get(Calendar.DAY_OF_YEAR);
        SharedPreferences settings = mContext.getSharedPreferences(key, 0);
        return settings.getString(key, "");
    }


    /**
     * Metodo para guardar en preferencias la mac address de la impresora seleccionada para poder
     * imprimir los tickets.
     *
     * @param macAddress
     */
    public static void saveMacPrinter(String macAddress) {
        SharedPreferences settings = mContext.getSharedPreferences(MACIMPRESORA, 0);
        SharedPreferences.Editor editor = settings.edit();
        try {
            editor.putString(MACIMPRESORA, macAddress);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para consultar la mac address guardada anteriormente para la impresion del ticket.
     *
     * @return
     */
    public static String loadMacPrinter() {
        SharedPreferences settings = mContext.getSharedPreferences(MACIMPRESORA, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return settings.getString(MACIMPRESORA, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
