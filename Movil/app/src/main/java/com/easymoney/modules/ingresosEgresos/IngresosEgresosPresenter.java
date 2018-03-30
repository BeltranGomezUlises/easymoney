package com.easymoney.modules.ingresosEgresos;

import android.annotation.SuppressLint;

import com.easymoney.data.repositories.MovimientoRepository;
import com.easymoney.entities.Movimiento;
import com.easymoney.models.EnumRangoFecha;
import com.easymoney.models.EnumTipoMovimiento;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ulises on 03/03/2018.
 */

@SuppressLint("ValidFragment")
public class IngresosEgresosPresenter implements IngresosEgresosContract.Presenter {

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
        showLoading(true);
        compositeDisposable.add(
                repository.altaMovimiento(movimiento)
                        .subscribe(r -> {
                            showLoading(false);
                            agregarMovimientoLista(r.getData());
                        }, ex -> {
                            showLoading(false);
                            showMessage("Error de comunicación");
                        })
        );
    }

    @Override
    public void cargarMovimientos(EnumTipoMovimiento tipoMovimiento, EnumRangoFecha enumRangoFecha) {
        showLoading(true);
        compositeDisposable.add(
                repository.findAll(tipoMovimiento, enumRangoFecha)
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
                                    ex.printStackTrace();
                                    showLoading(false);
                                    showMessage("Existió un error de comunicación");
                                })
        );
    }

    private void agregarMovimientoLista(Movimiento movimiento) {
        fragment.addMovimientotList(movimiento);
    }

    @Override
    public void subscribe() {
        this.cargarMovimientos(EnumTipoMovimiento.TODOS, EnumRangoFecha.DIAS7);
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
