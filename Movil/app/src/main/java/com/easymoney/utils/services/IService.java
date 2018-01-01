package com.easymoney.utils.services;


import com.easymoney.models.services.Login;
import com.easymoney.models.services.Response;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Interfaz de definición de endpoint para servicios web REST
 * Created by ulises on 30/12/17.
 */
public interface IService {


//    String END_POINT = "http://201.165.0.142:8383/EasyMoney/api/";
        String END_POINT = "http://192.168.1.69:8084/EasyMoney/api/";

    @POST("accesos/login")
    Flowable<Response<Login.Response>> login(@Body Login.Request request);

}
