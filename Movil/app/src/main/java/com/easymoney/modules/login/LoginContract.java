package com.easymoney.modules.login;

import com.easymoney.utils.baseClases.BasePresenter;
import com.easymoney.utils.baseClases.BaseView;

/**
 * Created by ulises on 30/12/17.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean active);

        void showMain(int userId, String userName, String userType);
    }

    interface Presenter extends BasePresenter<View> {

        void attemptLogin(String user, String pass);

    }

}
