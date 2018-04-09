package com.easymoney.data.repositories;

import com.easymoney.entities.Movimiento;
import com.easymoney.models.EnumRangoFecha;
import com.easymoney.models.EnumTipoMovimiento;
import com.easymoney.models.ModelFiltroMovimiento;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    public Flowable<Response<List<Movimiento>, Object>> findAll(EnumTipoMovimiento tipoMovimiento, EnumRangoFecha rangoFecha) {
        ModelFiltroMovimiento modelFiltroMovimiento = new ModelFiltroMovimiento();
        modelFiltroMovimiento.setCobradorId(UtilsPreferences.loadLogedUser().getId());
        switch (tipoMovimiento){
            case TODOS:
                //no filtrar por tipo
                break;
            case EGRESOS:
                modelFiltroMovimiento.setTipoMovimiento(false);
                break;
            case INGRESOS:
                modelFiltroMovimiento.setTipoMovimiento(true);
                break;
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        switch (rangoFecha){
            case DIAS7:
                modelFiltroMovimiento.setFechaFinal(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -7);
                modelFiltroMovimiento.setFechaInicial(UtilsDate.setTimeToZero(cal));
                break;
            case DIAS15:
                modelFiltroMovimiento.setFechaFinal(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -15);
                modelFiltroMovimiento.setFechaInicial(UtilsDate.setTimeToZero(cal));
                break;
            case DIAS30:
                modelFiltroMovimiento.setFechaFinal(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -30);
                modelFiltroMovimiento.setFechaInicial(UtilsDate.setTimeToZero(cal));
                break;
            case TODOS:
                //no filtrar por fechas
                break;
        }
        return webServices().obtenerMovimientos(UtilsPreferences.loadToken(), modelFiltroMovimiento);
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
