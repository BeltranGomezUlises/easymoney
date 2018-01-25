package com.easymoney.modules.detallePrestamo;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Abono;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.ModelTotalAPagar;
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
                                    proccessAbonos(r.getData());
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ModelTotalAPagar calcularTotalesPagar() {
        int abonoAPagar = 0;
        int multaAPagar = 0;
        Calendar cal = new GregorianCalendar();
        Date fechaActual = new Date();
        cal.setTime(fechaActual);
        int diaActual = cal.get((Calendar.DAY_OF_YEAR));
        List<Abono> abonos = prestamo.getAbonos().stream()
                .filter(a -> !a.isAbonado() && a.getAbonoPK().getFecha().compareTo(fechaActual) <= 0)
                .sorted((a1, a2) -> a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha()))
                .collect(toList());
        for (Abono abono : abonos) {
            cal.setTime(abono.getAbonoPK().getFecha());
            //si llegamos al dia de hoy solo sumar lo que corresponde pagar al dia
            if (cal.get(Calendar.DAY_OF_YEAR) == diaActual) {
                abonoAPagar += abono.getCantidad();
                break;
            } else {
                abonoAPagar += abono.getCantidad();
                multaAPagar += 20;
            }
        }
        return new ModelTotalAPagar(abonoAPagar, multaAPagar);
    }


    /**
     * distribuye el abono del cliente en los abonos, tomando en cuenta la prioridad del las multas si le corresponde y la antiguedad del abono
     *
     * @param abono cantidad de dinero a abonar por el cliente, intresado en el dialog
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void abonarAlPrestamo(int abono) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int diaActual = cal.get((Calendar.DAY_OF_YEAR));
        //distribui pago con prioridad en multa y abonos anteriores e ir cubirendo la cantidad del abono
        List<Abono> abonos = prestamo.getAbonos().stream()
                .filter(a -> !a.isAbonado())
                .sorted((a1, a2) -> a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha()))
                .collect(toList());
        for (Abono abonoActual : abonos) {
            cal.setTime(abonoActual.getAbonoPK().getFecha());
            if (cal.get(Calendar.DAY_OF_YEAR) < diaActual){
                if (abono > 20){
                    abonoActual.getMulta().setMulta(20);
                    abono -= 20;
                }else{
                    abonoActual.getMulta().setMulta(abono);
                    break; //se acabo el abono
                }
                if (abono > abonoActual.getCantidad()){
                    abonoActual.setAbonado(true);
                }else{

                }


            }
            abonoActual.getAbonoPK().getFecha();
        }
        //mandar a actualizar

        //cerrar la pantalla de detalle
    }

    /**
     * Realiza el comportamiento de inicializar y asignar la actividad con los abonos proporcionados
     * @param abonos abonos del prestamo con los cuales trabajar
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void proccessAbonos(List<Abono> abonos) {
        prestamo.setAbonos(abonos);
        abonoFragment.replaceData(abonos.stream().filter(a -> a.isAbonado()).collect(toList()));
        Date fechaActual = new Date();
        if (abonos.stream().anyMatch(a -> a.isAbonado() == false && a.getAbonoPK().getFecha().compareTo(fechaActual) <= 0)) {
            this.fab.setVisibility(View.VISIBLE);
        }
    }
}
