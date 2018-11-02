package com.easymoney.modules.cambiarContra;

import com.easymoney.data.repositories.UsuarioRepository;
import com.easymoney.entities.Usuario;
import com.easymoney.models.ModelCambiarContra;
import com.easymoney.models.services.Response;
import com.easymoney.utils.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.models.services.Errors.ERROR_COMUNICACION;

/**
 * Created by ulises on 15/01/2018.
 */

public class CambiarContraPresenter extends CambiarContraContract.Presenter {

    private UsuarioRepository usuarioRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    CambiarContraPresenter(CambiarContraFragment fragment, UsuarioRepository usuarioRepository) {
        this.setFragment(fragment);
        fragment.setPresenter(this);
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void cambiarContra(ModelCambiarContra modelCambiarContra) {
        compositeDisposable.clear();
        getFragment().showLoading();
        compositeDisposable.add(this.usuarioRepository.cambiarContraseña(modelCambiarContra)
                .observeOn(SchedulerProvider.uiT())
                .subscribeOn(SchedulerProvider.ioT())
                .subscribe(new Consumer<Response<Usuario, Object>>() {
                    @Override
                    public void accept(Response<Usuario, Object> r) throws Exception {
                        getFragment().stopShowLoading();
                        evalResponse(r, new Runnable() {
                            @Override
                            public void run() {
                                getFragment().showOK("Contraseña actualizada");
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getFragment().stopShowLoading();
                        getFragment().showERROR(ERROR_COMUNICACION);
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

}
