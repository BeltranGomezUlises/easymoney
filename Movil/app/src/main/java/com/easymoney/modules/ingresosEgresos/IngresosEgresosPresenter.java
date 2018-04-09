package com.easymoney.modules.ingresosEgresos;

import android.annotation.SuppressLint;

import com.easymoney.data.repositories.MovimientoRepository;
import com.easymoney.entities.Movimiento;
import com.easymoney.models.EnumRangoFecha;
import com.easymoney.models.EnumTipoMovimiento;
import com.easymoney.models.services.Response;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

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
                        .subscribe(new Consumer<Response<Movimiento, Object>>() {
                            @Override
                            public void accept(Response<Movimiento, Object> r) throws Exception {
                                showLoading(false);
                                agregarMovimientoLista(r.getData());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showLoading(false);
                                showMessage("Error de comunicación");
                                throwable.printStackTrace();
                            }
                        })
        );
    }

    @Override
    public void cargarMovimientos(EnumTipoMovimiento tipoMovimiento, EnumRangoFecha enumRangoFecha) {
        showLoading(true);
        compositeDisposable.add(
                repository.findAll(tipoMovimiento, enumRangoFecha)
                        .subscribeOn(SchedulerProvider.ioT())
                        .observeOn(SchedulerProvider.uiT())
                        .subscribe(new Consumer<Response<List<Movimiento>, Object>>() {
                                       @Override
                                       public void accept(Response<List<Movimiento>, Object> r) throws Exception {
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
                                   }
                                , new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        showLoading(false);
                                        showMessage("Existió un error de comunicación");
                                        throwable.printStackTrace();
                                    }
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
