package com.easymoney.modules.cambiarContra;

import com.easymoney.data.repositories.UsuarioRepository;
import com.easymoney.entities.Usuario;
import com.easymoney.models.ModelCambiarContra;
import com.easymoney.models.services.Response;
import com.easymoney.utils.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by ulises on 15/01/2018.
 */

public class CambiarContraPresenter implements CambiarContraContract.Presenter {

    private CambiarContraFragment fragment;
    private UsuarioRepository usuarioRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CambiarContraPresenter(CambiarContraFragment fragment, UsuarioRepository usuarioRepository) {
        this.fragment = fragment;
        fragment.setPresenter(this);
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void cambiarContra(ModelCambiarContra modelCambiarContra) {
        compositeDisposable.clear();
        compositeDisposable.add(this.usuarioRepository.cambiarContraseña(modelCambiarContra)
                .observeOn(SchedulerProvider.uiT())
                .subscribeOn(SchedulerProvider.ioT())
                .subscribe(new Consumer<Response<Usuario, Object>>() {
                    @Override
                    public void accept(Response<Usuario, Object> r) throws Exception {
                        fragment.showLoading(false);
                        switch (r.getMeta().getStatus()) {
                            case OK:
                                fragment.showMessage("Contraseña actualizada");
                                break;
                            case WARNING:
                                fragment.showMessage(r.getMeta().getMessage());
                                break;
                            case ERROR:
                                fragment.showMessage("Existió un error de programación");
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        fragment.showMessage("Existió un error de programación");
                    }
                })
        );
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void setView(CambiarContraContract.View view) {
        this.fragment = (CambiarContraFragment) view;
    }
}
