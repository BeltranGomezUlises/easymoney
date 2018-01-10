package com.easymoney.data.dataSources;

import com.easymoney.entities.Prestamo;
import com.easymoney.models.services.Response;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ulises on 7/01/18.
 */
public interface PrestamoDataSource extends DataSource<Prestamo, Integer> {

    Flowable<Response<List<Prestamo>, Object>> prestamosPorCobrar(int cobradorId);

    Flowable<Prestamo> actualizarPrestamo(Prestamo p);

    class PrestamoRemoteDataSource implements PrestamoDataSource {

        @Override
        public List<Prestamo> findAll() {
            return null;
        }

        @Override
        public Prestamo findById(Integer id) {
            return null;
        }

        @Override
        public Prestamo persist(Prestamo entity) {
            return null;
        }

        @Override
        public List<Prestamo> persist(Prestamo... entities) {
            return null;
        }

        @Override
        public Prestamo update(Prestamo prestamo) {
            return null;
        }

        @Override
        public List<Prestamo> update(Prestamo... entity) {
            return null;
        }

        @Override
        public void delete(Prestamo entity) {

        }

        @Override
        public void delete(Prestamo... entities) {

        }


        @Override
        public Flowable<Response<List<Prestamo>, Object>> prestamosPorCobrar(int cobradorId) {
            return null;
        }

        @Override
        public Flowable<Prestamo> actualizarPrestamo(Prestamo p) {
            return null;
        }
    }

    class PrestamoLocalDataSource implements PrestamoDataSource{

        @Override
        public List<Prestamo> findAll() {
            return null;
        }

        @Override
        public Prestamo findById(Integer id) {
            return null;
        }

        @Override
        public Prestamo persist(Prestamo entity) {
            return null;
        }

        @Override
        public List<Prestamo> persist(Prestamo... entities) {
            return null;
        }

        @Override
        public Prestamo update(Prestamo prestamo) {
            return null;
        }

        @Override
        public List<Prestamo> update(Prestamo... entity) {
            return null;
        }

        @Override
        public void delete(Prestamo entity) {

        }

        @Override
        public void delete(Prestamo... entities) {

        }


        @Override
        public Flowable<Response<List<Prestamo>, Object>> prestamosPorCobrar(int cobradorId) {
            return null;
        }

        @Override
        public Flowable<Prestamo> actualizarPrestamo(Prestamo p) {
            return null;
        }
    }

}
