package com.easymoney.data.repositories;

import com.easymoney.data.dataSources.PrestamoDataSource;
import com.easymoney.entities.Prestamo;

import java.util.List;

import io.reactivex.Flowable;

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
     * @return intancia de PrestamoRepository
     */
    public static PrestamoRepository getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new PrestamoRepository();
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Prestamo>> findAll() {
        if (isDirty){
            return getAndSaveRemotePrestamos();
        }else{
            return localDataSource.findAll();
        }
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

}
