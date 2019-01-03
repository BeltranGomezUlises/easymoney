package com.easymoney.modules.ingresosEgresos;


import com.easymoney.data.repositories.MovimientoRepository;
import com.easymoney.entities.Movimiento;
import com.easymoney.models.EnumRangoFecha;
import com.easymoney.models.EnumTipoMovimiento;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.models.services.Errors.ERROR_COMUNICACION;

/**
 * Created by ulises on 03/03/2018.
 */

public class IngresosEgresosPresenter extends IngresosEgresosContract.Presenter {

    private MovimientoRepository repository;
    private CompositeDisposable compositeDisposable;

    IngresosEgresosPresenter(MovimientoRepository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void agregarIngresoEgreso(Movimiento movimiento) {
        getFragment().showLoading();
        compositeDisposable.add(
                repository.altaMovimiento(movimiento)
                        .subscribe(new Consumer<Response<Movimiento, Object>>() {
                            @Override
                            public void accept(Response<Movimiento, Object> r) {
                                getFragment().stopShowLoading();
                                agregarMovimientoLista(r.getData());
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
    public void cargarMovimientos(EnumTipoMovimiento tipoMovimiento, EnumRangoFecha enumRangoFecha) {
        getFragment().showLoading();
        compositeDisposable.add(
                repository.findAll(tipoMovimiento, enumRangoFecha)
                        .subscribe(new Consumer<Response<List<Movimiento>, Object>>() {
                                       @Override
                                       public void accept(final Response<List<Movimiento>, Object> r) throws Exception {
                                           getFragment().stopShowLoading();
                                           evalResponse(r, new Runnable() {
                                               @Override
                                               public void run() {
                                                   List<Movimiento> movimientos = r.getData();
                                                   getFragment().replaceMovimientoList(movimientos);
                                               }
                                           });
                                       }
                                   }
                                , new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        getFragment().stopShowLoading();
                                        getFragment().showERROR(ERROR_COMUNICACION);
                                    }
                                })
        );
    }

    private void agregarMovimientoLista(Movimiento movimiento) {
        getFragment().addMovimientotList(movimiento);
    }

    @Override
    public void subscribe() {
        this.cargarMovimientos(EnumTipoMovimiento.TODOS, EnumRangoFecha.DIAS7);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

}
