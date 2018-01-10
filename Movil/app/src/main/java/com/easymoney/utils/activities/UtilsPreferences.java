package com.easymoney.utils.activities;

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
}
