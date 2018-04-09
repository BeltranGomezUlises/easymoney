package com.easymoney.data.repositories;

import com.easymoney.data.dataSources.PrestamoDataSource;
import com.easymoney.entities.Abono;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.EnumPrestamos;
import com.easymoney.models.ModelAbonarPrestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.List;

import io.reactivex.Flowable;

import static com.easymoney.utils.services.UtilsWS.webServices;

/**
 * Created by ulises on 7/01/18.
 */

public class PrestamoRepository implements PrestamoDataSource {

    private static PrestamoRepository INSTANCE;
    private PrestamoDataSource.PrestamoRemoteDataSource remoteDataSource;

    /**
     * prevenir instancias
     */
    private PrestamoRepository() {
        this.remoteDataSource = new PrestamoRemoteDataSource();
    }

    /**
     * singleton de PrestamoRepository
     *
     * @return intancia de PrestamoRepository
     */
    public static PrestamoRepository getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PrestamoRepository();
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Prestamo>> findAll() {
        return null;
    }

    @Override
    public Flowable<Prestamo> findById(Integer id) {
        return null;
    }

    @Override
    public Flowable<Prestamo> persist(Prestamo entity) {
        return null;
    }

    @Override
    public Flowable<List<Prestamo>> persist(List<Prestamo> entities) {
        return null;
    }

    @Override
    public Flowable<Prestamo> update(Prestamo prestamo) {
        return remoteDataSource.update(prestamo);
    }

    @Override
    public Flowable<List<Prestamo>> update(List<Prestamo> entity) {
        return null;
    }

    @Override
    public void delete(Prestamo entity) {

    }

    @Override
    public void delete(List<Prestamo> entities) {

    }

    /**
     * elimina los registros locales de prestamos
     */
    @Override
    public void deleteAll() {
    }

    /**
     * devuelve los datos totales de un prestamo
     *
     * @param prestamoId identificador del prestamo
     * @return Response con el modelo de totales de un prestamo
     */
    public Flowable<Response<ModelPrestamoTotales, Object>> totalesPrestamo(int prestamoId) {
        return webServices().totalesDelPrestamo(UtilsPreferences.loadToken(), prestamoId);
    }

    public Flowable<Response<List<Abono>, Object>> abonosPrestamo(int prestamoId) {
        return webServices().abonosDelPrestamo(UtilsPreferences.loadToken(), prestamoId);
    }

    /**
     * consulta los prestamos del cobrador, segun menu_ingresos_egresos del enumarados, todos o solo los que se les puede cobrar
     *
     * @param enumPrestamos TODOS, para consultar todos los prestamos del cobrador y POR_COBRAR para consultar aquellos que se les puede cobrar
     * @return lista de prestamos
     */
    public Flowable<Response<List<Prestamo>, Object>> findAll(EnumPrestamos enumPrestamos) {
        return remoteDataSource.findAll(enumPrestamos);
    }

    /**
     * agregar un abono de ajuste
     *
     * @param abono abono a agregar
     * @return response con el abono agregado
     */
    public Flowable<Response<Abono, Object>> agregarAbonoAjuste(Abono abono) {
        return webServices().agregarAbonoAjuste(UtilsPreferences.loadToken(), abono)
                .observeOn(SchedulerProvider.uiT())
                .subscribeOn(SchedulerProvider.ioT());
    }

    /**
     * Genera el abono al prestamo y actualiza el prestamo con la distribucion del pago
     *
     * @param modelAbonarPrestamo modelo contenedor de los valores necesarios asi como el prestamo con la dsitribucion de abonos a actualizar
     * @return prestamo actualizado
     */
    public Flowable<Response<Prestamo, Object>> abonarPrestamo(ModelAbonarPrestamo modelAbonarPrestamo) {
        return webServices().abonarPrestamo(UtilsPreferences.loadToken(), modelAbonarPrestamo)
                .observeOn(SchedulerProvider.uiT())
                .subscribeOn(SchedulerProvider.ioT());
    }
}
