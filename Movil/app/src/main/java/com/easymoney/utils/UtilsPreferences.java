package com.easymoney.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easymoney.entities.Usuario;
import com.easymoney.models.services.Login;

/**
 * Created by ulises on 9/01/18.
 */

public class UtilsPreferences {

    private static final String TOKEN = "token";
    private static final String USUARIO = "usuario";
    private static final String USUARIO_ID = "usuario_id";
    private static final String USUARIO_NOMBRE = "usuario_nombre";
    private static final String USUARIO_NOMBRE_COMPLETO = "usuario_nombre_completo";
    private static final String USUARIO_TIPO = "usuario_tipo";
    private static final String CONFIG = "config";
    private static final String CONFIG_DIAS_PRESTAMO = "configDiasPrestamo";
    private static final String CONFIG_PORCENTAJE_MULTA_DIARIA = "configPorcentajeMultaDiaria";
    private static final String CONFIG_ID = "configId";


    private static Context mContext;

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
        editor.putInt(USUARIO_ID, usuario.getId());
        editor.putString(USUARIO_NOMBRE, usuario.getNombre());
        editor.putString(USUARIO_NOMBRE_COMPLETO, usuario.getNombreCompleto());
        editor.putBoolean(USUARIO_TIPO, usuario.isTipo());
        editor.commit();
    }

    public static Usuario loadLogedUser() {
        SharedPreferences settings = mContext.getSharedPreferences(USUARIO, 0);
        int id = settings.getInt(USUARIO_ID, 0);
        if (id != 0) {
            Usuario u = new Usuario(id);
            u.setNombre(settings.getString(USUARIO_NOMBRE, ""));
            u.setNombreCompleto(settings.getString(USUARIO_NOMBRE_COMPLETO, ""));
            u.setTipo(settings.getBoolean(USUARIO_TIPO, false));
            return u;
        }
        return null;
    }


    public static void saveConfigs(Login.Response.Config config) {
        SharedPreferences settings = mContext.getSharedPreferences(CONFIG, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(CONFIG_ID, config.getId());
        editor.putInt(CONFIG_DIAS_PRESTAMO, config.getDiasPrestamo());
        editor.putInt(CONFIG_PORCENTAJE_MULTA_DIARIA, config.getPorcentajeInteresPrestamo());
        editor.commit();
    }

    public static Login.Response.Config loadConfig(){
        SharedPreferences settings = mContext.getSharedPreferences(CONFIG, 0);
        int id = settings.getInt(CONFIG_ID, 0);
        if (id != 0) {
            Login.Response.Config config = new Login.Response.Config();
            config.setId(id);
            config.setDiasPrestamo(settings.getInt(CONFIG_DIAS_PRESTAMO, 30));
            config.setPorcentajeInteresPrestamo(settings.getInt(CONFIG_PORCENTAJE_MULTA_DIARIA, 20));
            return config;
        }
        return null;
    }

}
