package com.easymoney.modules.login;

import com.easymoney.data.repositories.LoginRepository;
import com.easymoney.models.services.Login;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.bluetoothPrinterUtilities.UtilsPrinter;
import com.easymoney.utils.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by ulises on 30/12/17.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginFragment fragment;
    private LoginRepository repository;
    private CompositeDisposable mCompositeDisposable;

    public LoginPresenter(LoginFragment fragment, LoginRepository repository) {
        this.fragment = fragment;
        this.repository = repository;
        fragment.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attemptLogin(final String user, final String pass) {
        mCompositeDisposable.clear();

        final Login.Request request = new Login.Request();
        request.setPass(pass);
        request.setUser(user);

        mCompositeDisposable.add(repository.login(request)
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribeOn(SchedulerProvider.getInstance().io())
                .subscribe(new Consumer<Response<Login.Response, String>>() {
                               @Override
                               public void accept(Response<Login.Response, String> t) throws Exception {
                                   fragment.showLoading(false);
                                   switch (t.getMeta().getStatus()) {
                                       case OK:
                                           UtilsPreferences.saveToken(t.getMeta().getMetaData().toString());
                                           UtilsPreferences.saveLogedUser(t.getData().getUsuario());
                                           UtilsPreferences.saveConfigs(t.getData().getConfig());
                                           UtilsPreferences.saveLogin(request);
                                           fragment.showMain(t.getData().getUsuario().getId(), t.getData().getUsuario().getNombre(), t.getData().getUsuario().isTipo() ? "Administrador" : "Cobrador");
                                           break;
                                       case WARNING:
                                           fragment.showMessage(t.getMeta().getMessage());
                                           break;
                                       case ERROR:
                                       default:
                                   }
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable err) throws Exception {
                                   fragment.showLoading(false);
                                   fragment.showMessage("Existió un error de comunicación");
                                   err.printStackTrace();
                               }
                           }
                )
        );
    }

    @Override
    public void subscribe() {
        Login.Request request = UtilsPreferences.loadLogin();
        if (request != null){
            fragment.setPreloadedLogin(request);
        }
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setView(LoginContract.View view) {
        this.fragment = (LoginFragment) view;
    }

}
