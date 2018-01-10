package com.easymoney.data.repositories;

import android.app.Application;

import com.easymoney.data.dataSources.PrestamoDataSource;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.services.Response;
import com.easymoney.utils.activities.UtilsPreferences;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.List;

import io.reactivex.Flowable;

import static com.easymoney.utils.services.UtilsWS.webServices;

/**
 * Created by ulises on 7/01/18.
 */

public class PrestamoRepository implements PrestamoDataSource {
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
        return webServices().prestamosPorCobrar(UtilsPreferences.loadToken(), cobradorId);
    }

    @Override
    public Flowable<Prestamo> actualizarPrestamo(Prestamo p) {
        return null;
    }
}
