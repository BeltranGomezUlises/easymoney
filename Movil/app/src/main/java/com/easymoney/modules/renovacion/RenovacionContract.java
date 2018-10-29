package com.easymoney.modules.renovacion;

import com.easymoney.models.services.Status;
import com.easymoney.utils.baseClases.BasePresenter;
import com.easymoney.utils.baseClases.BaseView;

public interface RenovacionContract {

    interface Fragment extends BaseView<Presenter> {

        void showLoading(boolean value);
        void showMessage(String message, Status status);
        void showDialogRenovar();
    }

    interface Presenter extends BasePresenter<Fragment> {

        void buscarPrestamoId(int prestamoId);
    }

}
