package com.easymoney.modules.ingresosEgresos;

import com.easymoney.entities.Movimiento;
import com.easymoney.models.EnumRangoFecha;
import com.easymoney.models.EnumTipoMovimiento;
import com.easymoney.utils.baseClases.BasePresenter;
import com.easymoney.utils.baseClases.BaseView;

/**
 * Created by ulises on 03/03/2018.
 */

public interface IngresosEgresosContract {

    interface View extends BaseView<Presenter> {
        void showLoading(boolean active);
        void showMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void showLoading(boolean active);
        void showMessage(String message);

        void agregarIngresoEgreso(Movimiento movimiento);
        void cargarMovimientos(EnumTipoMovimiento tipoMovimiento, EnumRangoFecha enumRangoFecha);

    }
}
