package com.easymoney.data.repositories;

import com.easymoney.entities.Usuario;
import com.easymoney.models.services.Login;
import com.easymoney.models.services.Response;
import com.easymoney.utils.services.UtilsWS;

import io.reactivex.Flowable;

import static com.easymoney.utils.services.UtilsWS.webServices;

/**
 * Created by ulises on 30/12/17.
 */

public class LoginRepository {

    public Flowable<Response<Login.Response, String>> login(Login.Request request){
        return webServices().login(request);
    }

}
