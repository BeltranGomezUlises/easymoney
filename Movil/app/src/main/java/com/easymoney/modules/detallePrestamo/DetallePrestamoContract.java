package com.easymoney.modules.detallePrestamo;

import com.easymoney.entities.Abono;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.ModelTotalAPagar;
import com.easymoney.utils.baseClases.BaseFragment;
import com.easymoney.utils.baseClases.BasePresenter;

import java.util.List;

/**
 * Created by ulises on 15/01/2018.
 */
public interface DetallePrestamoContract {

    abstract class Fragment extends BaseFragment<Presenter> {
        abstract void setTotales(ModelPrestamoTotales totales);

        abstract void llenarDatosGenerales(Prestamo prestamo);

        abstract void replaceData(List<Abono> abonos);

        abstract void setTotalParaSaldar(ModelTotalAPagar modelTotalAPagar);

        abstract  void setBtnVisible(int visible);

    }

    abstract class Presenter extends BasePresenter<Fragment> {

        abstract void cargarTotalesPrestamo();

        abstract void cargarAbonosPrestamo();

        abstract ModelTotalAPagar getModelTotalAPagar();

        abstract void abonarAlPrestamo(int abono, String multaDes, ModelTotalAPagar model);

    }

}
