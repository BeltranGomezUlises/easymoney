package com.easymoney.data.repositories;

import com.easymoney.entities.Movimiento;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.List;

import io.reactivex.Flowable;

import static com.easymoney.utils.services.UtilsWS.webServices;

/**
 * Created by ulises on 03/03/2018.
 */

public class MovimientoRepository {

    /**
     * Consulta todos los movimientos del servidor
     *
     * @return lista de movimientos
     */
    public Flowable<Response<List<Movimiento>, Object>> findAll() {
        return webServices().obtenerMovimientos(UtilsPreferences.loadToken(), UtilsPreferences.loadLogedUser().getId())
                .subscribeOn(SchedulerProvider.ioT())
                .observeOn(SchedulerProvider.uiT());
    }

    /**
     * Da de alta un movimiento
     * @param movimiento movimiento a dar de alta
     * @return movimiento dado de alta
     */
    public Flowable<Response<Movimiento, Object>> altaMovimiento(Movimiento movimiento){
        return webServices().altaMovimientos(UtilsPreferences.loadToken(), movimiento)
                .subscribeOn(SchedulerProvider.ioT())
                .observeOn(SchedulerProvider.uiT());
    }

}
