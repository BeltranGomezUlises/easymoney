package com.easymoney.utils.services;


import com.easymoney.entities.Abono;
import com.easymoney.entities.Cliente;
import com.easymoney.entities.Movimiento;
import com.easymoney.entities.Prestamo;
import com.easymoney.entities.Usuario;
import com.easymoney.models.ModelAbonar;
import com.easymoney.models.ModelCambiarContra;
import com.easymoney.models.ModelFiltroMovimiento;
import com.easymoney.models.ModelPrestamoAbonado;
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
//    String END_POINT = "http://192.168.15.5:8080/EasyMoney/api/";
    String END_POINT = "http://74.208.178.83:8080/EasyMoney/api/";
    //String END_POINT = "http://74.208.178.83:8080/EasyMoneyPruebas/api/";

    /**
     * Inicio de sesion
     *
     * @param request modelo de inicio de sesion
     * @return usuario logeado
     */
    @POST("accesos/login")
    Flowable<Response<Login.Response, String>> login(@Body Login.Request request);

    /**
     * Obtiene los prestamos que le corresponden al cobrador y le faltan cobrar
     *
     * @param token      token de sesion
     * @param cobradorId identificador del cobrador
     * @return respuesta con la lista de prestamos
     */
    @GET("prestamos/prestamosPorCobrar/{cobradorId}")
    Flowable<Response<List<Prestamo>, Object>> prestamosPorCobrar(
            @Header("Authorization") String token,
            @Path("cobradorId") int cobradorId);

    /**
     * Obtiene los prestamos que le corresponden al cobrador
     *
     * @param token      token de sesion
     * @param cobradorId identificador del cobrador
     * @return respuesta con la lista de prestamos
     */
    @GET("prestamos/prestamosDelCobrador/{cobradorId}")
    Flowable<Response<List<Prestamo>, Object>> prestamosDelCobrador(
            @Header("Authorization") String token, @Path("cobradorId") int cobradorId);

    /**
     * Obtiene los totales generales del estado de un prestamo
     *
     * @param token      token de sesion
     * @param prestamoId id de prestamo
     * @return mmodelo de totales de un prestamo en espeficico
     */
    @GET("prestamos/totales/{prestamoId}")
    Flowable<Response<ModelPrestamoTotales, Object>> totalesDelPrestamo(
            @Header("Authorization") String token, @Path("prestamoId") int prestamoId);

    /**
     * Obtiene la lista de abonos de un prestamo en especifico
     *
     * @param token      token de sesion
     * @param prestamoId id del prestamo del cual obtener los abonos
     * @return lista de abonos del prestamo con el id en prestamoId
     */
    @GET("abonos/prestamo/{prestamoId}")
    Flowable<Response<List<Abono>, Object>> abonosDelPrestamo(
            @Header("Authorization") String token, @Path("prestamoId") int prestamoId);

    /**
     * Cambia la contraseña del usuario
     *
     * @param token         token de sesion
     * @param cambiarContra modelo de cambio de contraseña
     * @return respuesta con el usuario actualizado
     */
    @POST("usuarios/cambiarContra")
    Flowable<Response<Usuario, Object>> cambiarContra(
            @Header("Authorization") String token, @Body ModelCambiarContra cambiarContra);

    /**
     * Consulta todos los movimientos con un menu_ingresos_egresos proporcionado
     *
     * @param token  token de sesion
     * @param filtro modelo con el menu_ingresos_egresos a consultar los movimientos
     * @return lista de movimientos
     */
    @POST("movimientos/filtro")
    Flowable<Response<List<Movimiento>, Object>> obtenerMovimientos(
            @Header("Authorization") String token, @Body ModelFiltroMovimiento filtro);

    /**
     * Da de alta un nuevo movimiento
     *
     * @param token      token de sesion
     * @param movimiento movimiento a dar de alta
     * @return movimiento dado de alta, con el id generado
     */
    @POST("movimientos")
    Flowable<Response<Movimiento, Object>> altaMovimientos(@Header("Authorization") String token,
                                                           @Body Movimiento movimiento);

    /**
     * Genera el abono del prestamo y actualiza el estado del prestamo con la distribucion de su pago
     *
     * @param token token de sesion
     * @param model modelo contenedor de los datos para generar el abono
     * @return prestamo actualizado
     */
    @POST("prestamos/abonar")
    Flowable<Response<ModelPrestamoAbonado, Object>> abonarPrestamo(@Header("Authorization") String token,
                                                                    @Body ModelAbonar model);

    @GET("prestamos/renovar/{prestamoId}/{cantidadNuevoPrestamo}")
    Flowable<Response<Integer, Object>> renovarPrestamo(
            @Header("Authorization") String token,
            @Path("prestamoId") int prestamoId,
            @Path("cantidadNuevoPrestamo") int cantidadNuevoPrestamo);

    /**
     * Busca todos los clientes del sistema
     *
     * @param token token de sesion
     * @return lista de clientes
     */
    @GET("prestamos/{prestamoId}")
    Flowable<Response<Prestamo, Object>> getPrestamo(@Header("Authorization") String token,
                                                     @Path("prestamoId") int prestamoId);

    /**
     * obtiene los prestamos del cliente sin saldar
     *
     * @param token token de sesion
     * @return lista de prestamos del cliente sin saldar
     */
    @GET("prestamos/cliente/{id}")
    Flowable<Response<List<Prestamo>, Object>> prestamosDelCliente(@Header("Authorization") String token,
                                                                   @Path("id") int clienteId);

    @GET("clientes")
    Flowable<Response<List<Cliente>, Object>> cargarClientes(@Header("Authorization") String token);
}
