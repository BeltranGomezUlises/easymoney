package com.easymoney.modules.ingresosEgresos;

import com.easymoney.entities.Movimiento;
import com.easymoney.models.EnumRangoFecha;
import com.easymoney.models.EnumTipoMovimiento;
import com.easymoney.utils.baseClases.BaseFragment;
import com.easymoney.utils.baseClases.BasePresenter;

import java.util.List;

/**
 * Created by ulises on 03/03/2018.
 */

public interface IngresosEgresosContract {

    abstract class Fragment extends BaseFragment<Presenter> {
        abstract void replaceMovimientoList(List<Movimiento> movimientos);
        abstract void addMovimientotList(Movimiento movimiento);
    }

    abstract class Presenter extends BasePresenter<Fragment> {
        abstract void agregarIngresoEgreso(Movimiento movimiento);
        abstract void cargarMovimientos(EnumTipoMovimiento tipoMovimiento, EnumRangoFecha enumRangoFecha);
    }
}
