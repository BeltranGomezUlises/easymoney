package com.easymoney.modules.detallePrestamo;

import com.easymoney.utils.baseClases.BasePresenter;
import com.easymoney.utils.baseClases.BaseView;

/**
 * Created by ulises on 15/01/2018.
 */
public interface DetallePrestamoContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean val);

    }

    interface Presenter extends BasePresenter<View> {

        void cargarTotalesPrestamo();

        void cargarAbonosPrestamo();

    }

}
