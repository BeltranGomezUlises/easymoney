package com.easymoney.modules.renovacion;

import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Cliente;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelFiltroPrestamos;
import com.easymoney.models.services.Response;
import com.easymoney.models.services.Status;
import com.easymoney.utils.UtilsPreferences;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.utils.schedulers.SchedulerProvider.ioT;
import static com.easymoney.utils.schedulers.SchedulerProvider.uiT;
import static com.easymoney.utils.services.UtilsWS.webServices;


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
        repository.cargarPrestamo(prestamoId, new Consumer<Response<Prestamo, Object>>() {
            @Override
            public void accept(Response<Prestamo, Object> res) throws Exception {
                if (res.getData() == null) {
                    fragment.showMessage("Prestamo no encontrado", Status.WARNING);
                } else {
                    fragment.showDialogRenovar();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                fragment.showMessage("Error de comunicación con el servidor", Status.ERROR);
            }
        });
    }

}
