package com.easymoney.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easymoney.entities.Usuario;

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
        SharedPreferences.Editor editor = settings.edit();
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


}
