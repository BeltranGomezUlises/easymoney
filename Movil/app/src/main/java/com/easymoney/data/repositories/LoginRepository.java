package com.easymoney.data.repositories;

import com.easymoney.models.services.Login;
import com.easymoney.models.services.Response;
import com.easymoney.utils.services.UtilsWS;

import io.reactivex.Flowable;

import static com.easymoney.utils.services.UtilsWS.webServices;

/**
 * Created by ulises on 30/12/17.
 */

public class LoginRepository {

    public Flowable<Response<Login.Response>> login(String user, String pass){
        Login.Request request = new Login.Request();
        request.setPass(pass);
        request.setUser(user);

        return webServices().login(request);
    }

}
