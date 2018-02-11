package com.easymoney.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easymoney.entities.Usuario;
import com.easymoney.models.Config;
import com.easymoney.models.services.Login;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by ulises on 9/01/18.
 */

public class UtilsPreferences {

    private static final String TOKEN = "token";
    private static final String USUARIO = "usuario";
    private static final String CONFIG = "config";


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



}
