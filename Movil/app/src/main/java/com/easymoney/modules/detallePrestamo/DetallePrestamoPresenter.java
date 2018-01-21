package com.easymoney.modules.detallePrestamo;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Abono;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static java.util.stream.Collectors.toList;

/**
 * Created by ulises on 15/01/2018.
 */
public class DetallePrestamoPresenter implements DetallePrestamoContract.Presenter {


    private final PrestamoRepository repository = PrestamoRepository.getINSTANCE();
    private FloatingActionButton fab;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ConsultaFragment consultaFragment;
    private AbonoFragment abonoFragment;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void cargarAbonosPrestamo() {
        compositeDisposable.add(
                repository.abonosPrestamo(prestamo.getId())
                        .observeOn(SchedulerProvider.uiT())
                        .subscribeOn(SchedulerProvider.ioT())
                        .subscribe(r -> {
                            switch (r.getMeta().getStatus()) {
                                case OK:
                                    prestamo.setAbonos(r.getData());
                                    abonoFragment.replaceData(r.getData().stream().filter(a -> a.isAbonado()).collect(toList()));
                                    this.fab.setVisibility(View.VISIBLE);
                                    break;
                                case WARNING:
                                    break;
                                case ERROR:
                                    break;
                                default:
                            }
                        }, ex -> {
                            ex.printStackTrace();
                        })
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void subscribe() {
        this.llenarDatosGenerales();
        this.cargarTotalesPrestamo();
        this.cargarAbonosPrestamo();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
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

    public AbonoFragment getAbonoFragment() {
        return abonoFragment;
    }

    public void setAbonoFragment(AbonoFragment abonoFragment) {
        this.abonoFragment = abonoFragment;
    }

    public ModelPrestamoTotales getPrestamoTotales() {
        return prestamoTotales;
    }

    public void setPrestamoTotales(ModelPrestamoTotales prestamoTotales) {
        this.prestamoTotales = prestamoTotales;
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

    public int cantidadAAbonar() {
        return prestamo.getCantidadPagar() / prestamo.getAbonos().size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int multaAPagar() {
        int multaAPagar = 0;
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int actualDay = cal.get(Calendar.DAY_OF_YEAR);
        List<Abono> abonos = prestamo.getAbonos().stream().sorted((a1, a2) -> a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha())).collect(toList());
        for (int i = 1; i < abonos.size(); i++) {
            cal.setTime(abonos.get(i).getAbonoPK().getFecha());
            if (cal.get(Calendar.DAY_OF_YEAR) == actualDay) {
                if (!abonos.get(i - 1).isAbonado()){
                    multaAPagar = 20;
                }
                break;
            }
        }
        return multaAPagar;
    }
}
