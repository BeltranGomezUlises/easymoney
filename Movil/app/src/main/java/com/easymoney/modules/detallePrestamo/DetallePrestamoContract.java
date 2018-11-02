package com.easymoney.modules.detallePrestamo;

import com.easymoney.utils.baseClases.BaseFragment;
import com.easymoney.utils.baseClases.BasePresenter;

/**
 * Created by ulises on 15/01/2018.
 */
public interface DetallePrestamoContract {

    abstract class Fragment extends BaseFragment<Presenter> {
    }

    abstract class Presenter extends BasePresenter<Fragment> {

        abstract void cargarTotalesPrestamo();

        abstract void cargarAbonosPrestamo();

    }

}
