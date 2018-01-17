package com.easymoney.data.repositories;

import com.easymoney.data.dataSources.PrestamoDataSource;
import com.easymoney.entities.Abono;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;

import java.util.List;

import io.reactivex.Flowable;

import static com.easymoney.utils.services.UtilsWS.webServices;

/**
 * Created by ulises on 7/01/18.
 */

public class PrestamoRepository implements PrestamoDataSource {

    private static PrestamoRepository INSTANCE;
    private PrestamoDataSource.PrestamoLocalDataSource localDataSource;
    private PrestamoDataSource.PrestamoRemoteDataSource remoteDataSource;
    private boolean isDirty = true;

    /**
     * prevenir instancias
     */
    private PrestamoRepository() {
        this.localDataSource = new PrestamoLocalDataSource();
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
        return getAndSaveRemotePrestamos();
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
        return null;
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
        localDataSource.deleteAll();
    }

    /**
     * devuelve los datos totales de un prestamo
     * @param prestamoId identificador del prestamo
     * @return Response con el modelo de totales de un prestamo
     */
    public Flowable<Response<ModelPrestamoTotales, Object>> totalesPrestamo(int prestamoId){
        return webServices().totalesDelPrestamo(UtilsPreferences.loadToken(), prestamoId);
    }

    public Flowable<Response<List<Abono>, Object>> abonosPrestamo(int prestamoId){
        return webServices().abonosDelPrestamo(UtilsPreferences.loadToken(), prestamoId);
    }

    private Flowable<List<Prestamo>> getAndSaveRemotePrestamos() {
        localDataSource.deleteAll();
        return remoteDataSource
                .findAll()
                .flatMap(prestamos -> Flowable.fromIterable(prestamos).doOnNext(prestamo -> localDataSource.persist(prestamo)))
                .toList().toFlowable()
                .doOnComplete(() -> {
                    isDirty = false;
                });
    }

    /**
     * forza a la siguiente consulta de los prestamos a ser remota para garantizar el ultimo estado o cambio de usuario
     */
    public void forceRemoteUpdate() {
        this.isDirty = true;
    }

}
