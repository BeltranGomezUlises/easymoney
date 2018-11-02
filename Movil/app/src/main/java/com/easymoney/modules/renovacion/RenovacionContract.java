package com.easymoney.modules.renovacion;

import com.easymoney.entities.Prestamo;
import com.easymoney.models.services.Status;

public interface RenovacionContract {

    interface Fragment extends BaseView<Presenter> {

        void showLoading(boolean value);
        void showMessage(String message, Status status);
        void showDialogRenovar(Prestamo prestamo);
    }

    interface Presenter extends BasePresenter<Fragment> {

        void buscarPrestamoId(int prestamoId);
        void renovar(int prestamoId, int renovacion);
    }

}
