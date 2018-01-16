package com.easymoney.modules.cambiarContra;

import com.easymoney.entities.Usuario;
import com.easymoney.models.ModelCambiarContra;
import com.easymoney.utils.baseClases.BasePresenter;
import com.easymoney.utils.baseClases.BaseView;

/**
 * Created by ulises on 14/01/2018.
 */

public interface CambiarContraContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean active);

        void showMain();
    }

    interface Presenter extends BasePresenter<View> {

        void cambiarContra(ModelCambiarContra modelCambiarContra);

    }
}
