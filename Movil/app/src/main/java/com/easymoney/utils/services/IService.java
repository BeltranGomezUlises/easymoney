package com.easymoney.utils.services;


import com.easymoney.entities.Prestamo;
import com.easymoney.models.services.Login;
import com.easymoney.models.services.Response;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interfaz de definición de endpoint para servicios web REST
 * Created by ulises on 30/12/17.
 */
public interface IService {


//    String END_POINT = "http://201.165.0.142:8383/EasyMoney/api/";
        String END_POINT = "http://192.168.1.70:8084/EasyMoney/api/";

    @POST("accesos/login")
    Flowable<Response<Login.Response, String>> login(@Body Login.Request request);

    @GET("prestamos/prestamosPorCobrar/{cobradorId}")
    Flowable<Response<List<Prestamo>, Object>> prestamosPorCobrar(@Header("Authorization")String token, @Path("cobradorId") int cobradorId);


}
