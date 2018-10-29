package com.easymoney.modules.renovacion;

import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.models.services.Status;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.utils.schedulers.SchedulerProvider.ioT;
import static com.easymoney.utils.schedulers.SchedulerProvider.uiT;


public class RenovacionPresenter implements RenovacionContract.Presenter {

    private PrestamoRepository repository;
    private RenovacionFragment fragment;
    private CompositeDisposable mCompositeDisposable;

    RenovacionPresenter(PrestamoRepository repository, RenovacionFragment fragment) {
        this.repository = repository;
        this.fragment = fragment;
        fragment.setPresenter(this);
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
    public void setView(RenovacionContract.Fragment view) {
        this.fragment = (RenovacionFragment) view;
    }

    @Override
    public void buscarPrestamoId(int prestamoId) {
        fragment.showLoading(true);
        mCompositeDisposable.add(
                repository.cargarPrestamo(prestamoId, new Consumer<Response<Prestamo, Object>>() {
                    @Override
                    public void accept(Response<Prestamo, Object> res) {
                        fragment.showLoading(false);
                        if (res.getData() == null) {
                            fragment.showMessage("Prestamo no encontrado", Status.WARNING);
                        } else {
                            fragment.showDialogRenovar(res.getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        fragment.showLoading(false);
                        fragment.showMessage("Error de comunicación con el servidor", Status.ERROR);
                    }
                }));
    }

    @Override
    public void renovar(int prestamoId, int renovacion) {

    }


    public void totalesPrestamo(int prestamoId, Consumer<Response<ModelPrestamoTotales, Object>> onNext, Consumer<Throwable> onError) {
        mCompositeDisposable.add(
                repository.totalesPrestamo(prestamoId).subscribeOn(ioT()).observeOn(uiT())
                        .subscribe(onNext, onError));
    }

    public void showError(String msg){
        fragment.showMessage(msg, Status.ERROR);
    }

}
