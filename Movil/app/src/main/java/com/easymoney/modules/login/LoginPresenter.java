package com.easymoney.modules.login;

import com.easymoney.data.repositories.LoginRepository;
import com.easymoney.utils.schedulers.SchedulerProvider;


import io.reactivex.disposables.CompositeDisposable;

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
        //fragment.showLoading(false);
        mCompositeDisposable.add(repository.login(user, pass)
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribeOn(SchedulerProvider.getInstance().io())
                .subscribe( (t) -> {
                    System.out.println(t);
                    switch (t.getMeta().getStatus()){
                        case OK:
                            fragment.showMain(t.getData().getNombre(), t.getData().isTipo() ? "Administrador" : "Cobrador");
                            break;
                        case WARNING:
                            fragment.showMessage(t.getMeta().getMessage());
                            break;
                        case ERROR:
                            default:
                    }
                    fragment.showLoading(false);
                }, (err) -> {
                    fragment.showLoading(false);
                    fragment.showMessage("Existió un error de comunicación");
                    err.printStackTrace();
                }));
    }

    @Override
    public void subscribe() {

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
