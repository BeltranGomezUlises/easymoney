package com.easymoney.utils.services;


import com.easymoney.entities.Abono;
import com.easymoney.entities.Movimiento;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interfaz de definición de endpoint para servicios web REST
 * Created by ulises on 30/12/17.
 */
public interface IService {

    /**
     * endpoint de los servicios
     */
//    String END_POINT = "http://201.165.0.142:8383/em/api/";
    String END_POINT = "http://192.168.1.70:8084/EasyMoney/api/";

    /**
     * Inicio de sesion
     *
     * @param request modelo de inicio de sesion
     * @return usuario logeado
     */
    @POST("accesos/login")
    Flowable<Response<Login.Response, String>> login(@Body final Login.Request request);

    /**
     * Obtiene los prestamos que le corresponden al cobrador y le faltan cobrar
     *
     * @param token      token de sesion
     * @param cobradorId identificador del cobrador
     * @return respuesta con la lista de prestamos
     */
    @GET("prestamos/prestamosPorCobrar/{cobradorId}")
    Flowable<Response<List<Prestamo>, Object>> prestamosPorCobrar(@Header("Authorization") final String token, @Path("cobradorId") final int cobradorId);

    /**
     * Obtiene los totales generales del estado de un prestamo
     *
     * @param token      token de sesion
     * @param prestamoId id de prestamo
     * @return mmodelo de totales de un prestamo en espeficico
     */
    @GET("prestamos/totales/{prestamoId}")
    Flowable<Response<ModelPrestamoTotales, Object>> totalesDelPrestamo(@Header("Authorization") final String token, @Path("prestamoId") final int prestamoId);

    /**
     * Obtiene la lista de abonos de un prestamo en especifico
     *
     * @param token      token de sesion
     * @param prestamoId id del prestamo del cual obtener los abonos
     * @return lista de abonos del prestamo con el id en prestamoId
     */
    @GET("abonos/prestamo/{prestamoId}")
    Flowable<Response<List<Abono>, Object>> abonosDelPrestamo(@Header("Authorization") final String token, @Path("prestamoId") final int prestamoId);

    /**
     * servicio para agregar un abono de ajuste a un prestamo donde las cantidades abonadas no tienen el valor correspondiente
     *
     * @param token token de sesion
     * @param abono abono a agregar, con su respectiva multa y comentario
     * @return respuesta con el abono agregado
     */
    @POST("abonos/agregarAjuste")
    Flowable<Response<Abono, Object>> agregarAbonoAjuste(@Header("Authorization") final String token, @Body final Abono abono);

    /**
     * actualiza un prestamo
     *
     * @param token    token de sesion
     * @param prestamo prestamo a actualizar
     * @return prestamo actualizado
     */
    @PUT("prestamos")
    Flowable<Response<Prestamo, Object>> actualizarPrestamo(@Header("Authorization") final String token, @Body final Prestamo prestamo);

    /**
     * Cambia la contraseña del usuario
     *
     * @param token         token de sesion
     * @param cambiarContra modelo de cambio de contraseña
     * @return respuesta con el usuario actualizado
     */
    @POST("usuarios/cambiarContra")
    Flowable<Response<Usuario, Object>> cambiarContra(@Header("Authorization") final String token, @Body final ModelCambiarContra cambiarContra);

    /**
     * Consulta todos los movimientos
     *
     * @param token token de sesion
     * @return lista de movimientos
     */
    @GET("movimientos")
    Flowable<Response<List<Movimiento>, Object>> obtenerMovimientos(@Header("Authorization") final String token);

    /**
     * Da de alta un nuevo movimiento
     *
     * @param token      token de sesion
     * @param movimiento movimiento a dar de alta
     * @return movimiento dado de alta, con el id generado
     */
    @POST("movimientos")
    Flowable<Response<Movimiento, Object>> altaMovimientos(@Header("Authorization") final String token, final Movimiento movimiento);

}
