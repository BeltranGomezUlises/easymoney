package com.easymoney.modules.ingresosEgresos;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;


import com.easymoney.data.repositories.MovimientoRepository;
import com.easymoney.entities.Movimiento;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ulises on 03/03/2018.
 */

@SuppressLint("ValidFragment")
public class IngresosEgresosPresenter extends Fragment implements IngresosEgresosContract.Presenter {

    private MovimientoRepository repository;
    private CompositeDisposable compositeDisposable;
    private IngresosEgresosFragment fragment;

    public IngresosEgresosPresenter(MovimientoRepository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void showLoading(boolean active) {
        fragment.showLoading(active);
    }

    @Override
    public void showMessage(String message) {
        fragment.showMessage(message);
    }

    @Override
    public void agregarIngresoEgreso(Movimiento movimiento) {

    }

    @Override
    public void cargarMovimientos() {
        showLoading(true);
        compositeDisposable.add(
                repository.findAll()
                        .subscribe(r -> {
                                    showLoading(false);
                                    switch (r.getMeta().getStatus()) {
                                        case OK:
                                            fragment.replaceMovimientoList(r.getData());
                                            break;
                                        case WARNING:
                                            showMessage("Atención:" + r.getMeta().getMessage());
                                            break;
                                        case ERROR:
                                            showMessage("Error de programación");
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                , ex -> {
                                    showLoading(false);
                                    showMessage("Existió un error de comunicación");
                                })
        );
    }

    @Override
    public void subscribe() {
        this.cargarMovimientos();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void setView(IngresosEgresosContract.View view) {
        this.fragment = (IngresosEgresosFragment) view;
    }
}
