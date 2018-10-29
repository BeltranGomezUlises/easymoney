package com.easymoney.utils.services;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Clase Singleton para obtencion de instancia retrofit
 * Created by ulises on 22/02/17.
 */

public class SingletonRetrofit {

    private SingletonRetrofit(){}

    private static Retrofit instancia;

    public static Retrofit getIntance(){
        if (instancia == null){
            instancia = new Retrofit.Builder()
                    .baseUrl(IService.END_POINT)
                    .client(
                            new OkHttpClient().newBuilder()
                                    .connectTimeout(15, TimeUnit.SECONDS)
                                    .writeTimeout(15, TimeUnit.SECONDS)
                                    .readTimeout(15, TimeUnit.SECONDS)
                                    .build()
                    )
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instancia;
    }

}
