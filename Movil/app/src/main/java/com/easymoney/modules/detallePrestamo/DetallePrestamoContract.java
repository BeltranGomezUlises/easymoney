package com.easymoney.modules.detallePrestamo;

import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.utils.baseClases.BaseFragment;
import com.easymoney.utils.baseClases.BasePresenter;

/**
 * Created by ulises on 15/01/2018.
 */
public interface DetallePrestamoContract {

    abstract class Fragment extends BaseFragment<Presenter> {
        abstract void llenarDatosGenerales(Prestamo prestamo);
        abstract void llenarTotales(ModelPrestamoTotales ModelPrestamoTotales);
    }

    abstract class Presenter extends BasePresenter<Fragment> {
        abstract void prestamoTotales();
        abstract void abonar(int abono, String multaDes);

    }

}
