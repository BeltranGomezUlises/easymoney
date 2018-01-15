package com.easymoney.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ulises on 9/01/18.
 */

public class UtilsPreferences {

    private static Context mContext;

    public static void setContext(final Context context){
        mContext = context;
    }

    private static final String TOKEN = "token";
    private static final String LOGED_ID = "loged_id";

    public static void saveToken(final String token){
        SharedPreferences settings = mContext.getSharedPreferences(TOKEN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public static String loadToken(){
        SharedPreferences settings = mContext.getSharedPreferences(TOKEN, 0);
        return settings.getString(TOKEN, "");
    }

    public static void saveLogedUserId(final int id){
        SharedPreferences settings = mContext.getSharedPreferences(LOGED_ID, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(LOGED_ID, id);
        editor.commit();
    }

    public static int loadLogedUserId(){
        SharedPreferences settings = mContext.getSharedPreferences(LOGED_ID, 0);
        return settings.getInt(LOGED_ID, 0);
    }

}
