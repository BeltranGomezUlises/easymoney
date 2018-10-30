package com.easymoney.data.repositories;

import com.easymoney.data.dataSources.PrestamoDataSource;
import com.easymoney.entities.Abono;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.EnumPrestamos;
import com.easymoney.models.ModelAbonarPrestamo;
import com.easymoney.models.ModelFiltroPrestamos;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.utils.schedulers.SchedulerProvider.ioT;
import static com.easymoney.utils.schedulers.SchedulerProvider.uiT;
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
    public static PrestamoRepository getInstance() {
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
     * Genera el abono al prestamo y actualiza el prestamo con la distribucion del pago
     *
     * @param modelAbonarPrestamo modelo contenedor de los valores necesarios asi como el prestamo con la dsitribucion de abonos a actualizar
     * @return prestamo actualizado
     */
    public Flowable<Response<Prestamo, Object>> abonarPrestamo(ModelAbonarPrestamo modelAbonarPrestamo) {
        return webServices().abonarPrestamo(UtilsPreferences.loadToken(), modelAbonarPrestamo)
                .observeOn(uiT())
                .subscribeOn(ioT());
    }

    /**
     * Carga los prestamos correpondientes al filtro aplicado
     *
     * @param model   modelo contenedor de filtros
     * @param onNext  on next consumer
     * @param onError on error consumer
     * @return disposable del recurso
     */
    public Disposable cargarPrestamos(ModelFiltroPrestamos model,
                                      Consumer<Response<List<Prestamo>, Object>> onNext,
                                      Consumer<Throwable> onError) {
        return webServices().buscarPrestamos(UtilsPreferences.loadToken(), model)
                .observeOn(uiT())
                .subscribeOn(ioT())
                .subscribe(onNext, onError);
    }


    /**
     * Carga un prestamo por su id
     *
     * @param prestamoId identificador del prestamo
     * @param onNext     consumer para continuar
     * @param onError    consumer para error
     * @return response con prestamo
     */
    public Disposable cargarPrestamo(int prestamoId,
                                     Consumer<Response<Prestamo, Object>> onNext,
                                     Consumer<Throwable> onError) {
        return webServices().getPrestamo(UtilsPreferences.loadToken(), prestamoId)
                .observeOn(uiT())
                .subscribeOn(ioT())
                .subscribe(onNext, onError);
    }

    public Disposable renovar(int prestamoId, int cantidadNuevoPrestamo,
                              Consumer<Response<Integer, Object>> onNext,
                              Consumer<Throwable> onError) {
        return webServices().renovarPrestamo(UtilsPreferences.loadToken(), prestamoId, cantidadNuevoPrestamo)
                .observeOn(uiT())
                .subscribeOn(ioT())
                .subscribe(onNext, onError);
    }
}
