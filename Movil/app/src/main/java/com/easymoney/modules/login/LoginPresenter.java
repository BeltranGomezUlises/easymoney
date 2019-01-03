package com.easymoney.modules.login;

import com.easymoney.data.repositories.LoginRepository;
import com.easymoney.entities.Usuario;
import com.easymoney.models.Config;
import com.easymoney.models.services.Login;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.models.services.Errors.ERROR_COMUNICACION;

/**
 * Created by ulises on 30/12/17.
 */
public class LoginPresenter extends LoginContract.Presenter {

    private LoginRepository repository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    LoginPresenter(LoginContract.Fragment fragment, LoginRepository repository) {
        this.setFragment(fragment);
        this.repository = repository;
        fragment.setPresenter(this);
    }

    @Override
    public void attemptLogin(final String user, final String pass) {
        mCompositeDisposable.clear();

        final Login.Request request = new Login.Request();
        request.setPass(pass);
        request.setUser(user);

        getFragment().showLoading();
        mCompositeDisposable.add(repository.login(request)
                .subscribe(new Consumer<Response<Login.Response, String>>() {
                               @Override
                               public void accept(final Response<Login.Response, String> t) {
                                   getFragment().stopShowLoading();
                                   evalResponse(t, new Runnable() {
                                       @Override
                                       public void run() {
                                           String token = t.getMeta().getMetaData().toString();
                                           Usuario usuario = t.getData().getUsuario();
                                           Config config = t.getData().getConfig();
                                           String tipoUsuario = usuario.getTipo() ? "Administrador" : "Cobrador";

                                           UtilsPreferences.saveToken(token);
                                           UtilsPreferences.saveLogedUser(usuario);
                                           UtilsPreferences.saveConfigs(config);
                                           UtilsPreferences.saveLogin(request);
                                           getFragment().showMain(usuario.getId(),
                                                   usuario.getNombre(),
                                                   tipoUsuario);
                                       }
                                   });

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable err) throws Exception {
                                   getFragment().stopShowLoading();
                                   getFragment().showERROR(ERROR_COMUNICACION);

                               }
                           }
                )
        );
    }

    @Override
    public void subscribe() {
        Login.Request request = UtilsPreferences.loadLogin();
        if (request != null) {
            getFragment().setPreloadedLogin(request);
        }
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }


}
