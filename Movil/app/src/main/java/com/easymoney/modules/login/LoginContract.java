package com.easymoney.modules.login;

import com.easymoney.models.services.Login;
import com.easymoney.utils.baseClases.BaseFragment;
import com.easymoney.utils.baseClases.BasePresenter;

/**
 * Created by ulises on 30/12/17.
 */

public interface LoginContract {

    abstract class Fragment extends BaseFragment<Presenter> {
        abstract void showMain(int userId, String userName, String userType);
        abstract void setPreloadedLogin(Login.Request request);
    }

    abstract class Presenter extends BasePresenter<Fragment> {
        abstract void attemptLogin(String user, String pass);
    }

}
