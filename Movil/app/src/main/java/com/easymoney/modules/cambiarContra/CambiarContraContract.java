package com.easymoney.modules.cambiarContra;

import com.easymoney.models.ModelCambiarContra;
import com.easymoney.utils.baseClases.BaseFragment;
import com.easymoney.utils.baseClases.BasePresenter;

/**
 * Created by ulises on 14/01/2018.
 */

public interface CambiarContraContract {

    abstract class Fragment extends BaseFragment<Presenter> {
    }

    abstract class Presenter extends BasePresenter<Fragment> {
        abstract void cambiarContra(ModelCambiarContra modelCambiarContra);
    }
}
