package com.easymoney.modules.renovacion;

import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.models.services.Status;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.utils.schedulers.SchedulerProvider.ioT;
import static com.easymoney.utils.schedulers.SchedulerProvider.uiT;


public class RenovacionPresenter extends RenovacionContract.Presenter {

    private PrestamoRepository repository;
    private CompositeDisposable mCompositeDisposable;

    RenovacionPresenter(PrestamoRepository repository) {
        this.repository = repository;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void buscarPrestamoId(int prestamoId) {
        getFragment().showLoading();
        mCompositeDisposable.add(
                repository.cargarPrestamo(prestamoId, new Consumer<Response<Prestamo, Object>>() {
                    @Override
                    public void accept(Response<Prestamo, Object> res) {
                       getFragment().stopShowLoading();
                        if (res.getData() == null) {
                            getFragment().showWARNING("Prestamo no encontrado");
                        } else {
                            getFragment().showDialogRenovar(res.getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getFragment().stopShowLoading();
                        getFragment().showERROR("Error de comunicación con el servidor");
                    }
                }));
    }

    @Override
    public void renovar(int prestamoId, int renovacion) {
        getFragment().showLoading();
        mCompositeDisposable.add(
                repository.renovar(prestamoId, renovacion, new Consumer<Response<Integer, Object>>() {
                    @Override
                    public void accept(final Response<Integer, Object> res) {
                        getFragment().stopShowLoading();
                        evalResponse(res, new Runnable() {
                            @Override
                            public void run() {
                                getFragment().showMessage("Entregar: $ " + res.getData());
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getFragment().stopShowLoading();
                        getFragment().showERROR("Error de comunicación con el servidor");
                    }
                }));

    }

    public Disposable totalesPrestamo(int prestamoId, Consumer<Response<ModelPrestamoTotales, Object>> onNext, Consumer<Throwable> onError) {
        Disposable dis = repository.totalesPrestamo(prestamoId, onNext, onError);
        mCompositeDisposable.add(dis);
        return dis;
    }

    @Override
    void showError(String msg) {
        getFragment().showERROR(msg);
    }
}
