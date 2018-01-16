package com.easymoney.modules.detallePrestamo;

import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.utils.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ulises on 15/01/2018.
 */
public class DetallePrestamoPresenter implements DetallePrestamoContract.Presenter {

    private final PrestamoRepository repository = PrestamoRepository.getINSTANCE();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ConsultaFragment consultaFragment;
    private Prestamo prestamo;
    private ModelPrestamoTotales prestamoTotales;

    public DetallePrestamoPresenter(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    @Override
    public void cargarTotalesPrestamo() {
        compositeDisposable.add(repository.totalesPrestamo(prestamo.getId())
                .observeOn(SchedulerProvider.uiT())
                .subscribeOn(SchedulerProvider.ioT())
                .subscribe(r -> {
                    consultaFragment.showLoading(false);
                    switch (r.getMeta().getStatus()) {
                        case OK:
                            consultaFragment.setTotales(r.getData());
                            break;
                        case WARNING:
                            consultaFragment.showMessage(r.getMeta().getMessage());
                            break;
                        case ERROR:
                            consultaFragment.showMessage("Existió un error de programación");
                            break;
                    }
                }, ex -> {
                    consultaFragment.showMessage("Existió un error de programación");
                    ex.printStackTrace();
                }));
    }

    @Override
    public void cargarAbonosPrestamo() {

    }

    @Override
    public void subscribe() {
        this.llenarDatosGenerales();
        this.cargarTotalesPrestamo();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void setView(DetallePrestamoContract.View view) {
    }

    public ConsultaFragment getConsultaFragment() {
        return consultaFragment;
    }

    public void setConsultaFragment(ConsultaFragment consultaFragment) {
        this.consultaFragment = consultaFragment;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public void llenarDatosGenerales() {
        this.consultaFragment.llenarDatosGenerales(prestamo);
    }
}
