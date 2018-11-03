package com.easymoney.modules.renovacion;

import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.utils.baseClases.BaseFragment;
import com.easymoney.utils.baseClases.BasePresenter;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface RenovacionContract {

    abstract class Fragment extends BaseFragment<Presenter> {

        abstract void showDialogRenovar(Prestamo prestamo);
    }

    abstract class Presenter extends BasePresenter<Fragment> {

        abstract void buscarPrestamoId(int prestamoId);

        abstract void renovar(int prestamoId, int renovacion);

        abstract Disposable totalesPrestamo(int prestamoId,
                                            Consumer<Response<ModelPrestamoTotales, Object>> onNext,
                                            Consumer<Throwable> onError);
        abstract void showError(String msg);

    }

}
