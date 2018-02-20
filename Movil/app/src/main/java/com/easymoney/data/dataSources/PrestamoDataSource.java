package com.easymoney.data.dataSources;

import com.easymoney.entities.Prestamo;
import com.easymoney.utils.UtilsPreferences;

import java.util.List;

import io.reactivex.Flowable;

import static com.easymoney.utils.UtilsPreferences.loadLogedUser;
import static com.easymoney.utils.UtilsPreferences.loadToken;
import static com.easymoney.utils.services.UtilsWS.webServices;

/**
 * Created by ulises on 7/01/18.
 */
public interface PrestamoDataSource extends DataSource<Prestamo, Integer> {

    class PrestamoRemoteDataSource implements PrestamoDataSource {

        @Override
        public Flowable<List<Prestamo>> findAll() {
            return webServices().prestamosPorCobrar(loadToken(), loadLogedUser().getId())
                    .flatMap( r -> Flowable.fromIterable(r.getData()))
                    .toList().toFlowable();
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
            return webServices().actualizarPrestamo(UtilsPreferences.loadToken(), prestamo)
                    .map( r -> r.getData());
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

        @Override
        public void deleteAll() {

        }

    }

    class PrestamoLocalDataSource implements PrestamoDataSource {

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

        @Override
        public void deleteAll() {

        }

    }

}