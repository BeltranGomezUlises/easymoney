package com.easymoney.utils.services;


import com.easymoney.entities.Abono;
import com.easymoney.entities.Prestamo;
import com.easymoney.entities.Usuario;
import com.easymoney.models.ModelCambiarContra;
import com.easymoney.models.ModelPrestamoTotales;
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

    /**
     * endpoint de los servicios
     */
    String END_POINT = "http://201.165.0.142:8383/em/api/";
//    String END_POINT = "http://10.243.93.229:8084/EasyMoney/api/";

    /**
     * Inicio de sesion
     * @param request modelo de inicio de sesion
     * @return usuario logeado
     */
    @POST("accesos/login")
    Flowable<Response<Usuario, String>> login(@Body Login.Request request);

    /**
     * Obtiene los prestamos que le corresponden al cobrador y le faltan cobrar
     * @param token token de sesion
     * @param cobradorId identificador del cobrador
     * @return respuesta con la lista de prestamos
     */
    @GET("prestamos/prestamosPorCobrar/{cobradorId}")
    Flowable<Response<List<Prestamo>, Object>> prestamosPorCobrar(@Header("Authorization")String token, @Path("cobradorId") int cobradorId);

    @GET("prestamos/totales/{prestamoId}")
    Flowable<Response<ModelPrestamoTotales, Object>> totalesDelPrestamo(@Header("Authorization")String token, @Path("prestamoId") int prestamoId);

    @GET("abonos/prestamo/{prestamoId}")
    Flowable<Response<List<Abono>, Object>> abonosDelPrestamo(@Header("Authorization")String token, @Path("prestamoId") int prestamoId);

    /**
     * Cambia la contraseña del usuario
     * @param token token de sesion
     * @param cambiarContra modelo de cambio de contraseña
     * @return respuesta con el usuario actualizado
     */
    @POST("usuarios/cambiarContra")
    Flowable<Response<Usuario, Object>> cambiarContra(@Header("Authorization") String token, @Body ModelCambiarContra cambiarContra);
}
