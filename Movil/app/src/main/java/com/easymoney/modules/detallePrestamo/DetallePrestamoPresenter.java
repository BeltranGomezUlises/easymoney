package com.easymoney.modules.detallePrestamo;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Abono;
import com.easymoney.entities.Multa;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelAbonarPrestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.ModelTotalAPagar;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

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
    private ModelTotalAPagar modelTotalAPagar;

    public DetallePrestamoPresenter(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public ModelTotalAPagar getModelTotalAPagar() {
        return modelTotalAPagar;
    }

    public void setModelTotalAPagar(ModelTotalAPagar modelTotalAPagar) {
        this.modelTotalAPagar = modelTotalAPagar;
    }

    @Override
    public void cargarTotalesPrestamo() {
        compositeDisposable.add(repository.totalesPrestamo(prestamo.getId())
                .observeOn(SchedulerProvider.uiT())
                .subscribeOn(SchedulerProvider.ioT())
                .subscribe(new Consumer<Response<ModelPrestamoTotales, Object>>() {
                    @Override
                    public void accept(Response<ModelPrestamoTotales, Object> r) throws Exception {
                        consultaFragment.showLoading(false);
                        switch (r.getMeta().getStatus()) {
                            case OK:
                                consultaFragment.setTotales(r.getData());
                                break;
                            case WARNING:
                                showMessage(r.getMeta().getMessage());
                                break;
                            case ERROR:
                                showMessage("Existió un error de programación");
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showLoading(false);
                        showMessage("Existió un error de comunicación");
                        throwable.printStackTrace();
                    }
                }));
    }

    @Override
    public void cargarAbonosPrestamo() {
        compositeDisposable.add(
                repository.abonosPrestamo(prestamo.getId())
                        .observeOn(SchedulerProvider.uiT())
                        .subscribeOn(SchedulerProvider.ioT())
                        .subscribe(new Consumer<Response<List<Abono>, Object>>() {
                            @Override
                            public void accept(Response<List<Abono>, Object> r) throws Exception {
                                switch (r.getMeta().getStatus()) {
                                    case OK:
                                        proccessAbonos(r.getData());
                                        break;
                                    case WARNING:
                                        showMessage(r.getMeta().getMessage());
                                        break;
                                    case ERROR:
                                        showMessage("Existió un error de programación");
                                        break;
                                    default:
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showMessage("Existió un error de comunicación");
                                showLoading(false);
                                throwable.printStackTrace();
                            }
                        })
        );
    }

    @Override
    public void showLoading(boolean active) {
        this.abonoFragment.showLoading(active);
        this.consultaFragment.showLoading(active);
    }

    public final void showMessage(final String message) {
        this.consultaFragment.showMessage(message);
        this.abonoFragment.showMessage(message);
    }

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

    public ModelTotalAPagar calcularTotalesPagar() {
        int abonoAPagar = 0;
        int multaAPagar = 0;
        int multaAPagarMes = 0;

        Calendar cal = new GregorianCalendar();
        Date fechaActual = new Date();
        cal.setTime(fechaActual);
        int diaActual = cal.get((Calendar.DAY_OF_YEAR));
        int multaDiaria = UtilsPreferences.loadConfig().getCantidadMultaDiaria();

        List<Abono> abonos = new ArrayList<>();
        for (Abono a : prestamo.getAbonos()) {
            abonos.add(a);
        }
        Collections.sort(abonos, new Comparator<Abono>() {
            @Override
            public int compare(Abono a1, Abono a2) {
                return a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha());
            }
        });

        for (Abono abono : abonos) {
            cal.setTime(abono.getAbonoPK().getFecha());
            //si llegamos al dia de hoy solo sumar lo que corresponde pagar al dia
            if (cal.get(Calendar.DAY_OF_YEAR) < diaActual) { //anteriores al dia de hoy
                if (!abono.isAbonado()) {
                    abonoAPagar += this.prestamo.getCobroDiario();
                    multaAPagar += multaDiaria;
                } else {
                    //si esta abonado y no abono mas o igual que el cobro diario es multa
                    if (abono.getCantidad() < this.prestamo.getCobroDiario()) {
                        abonoAPagar += (this.prestamo.getCobroDiario() - abono.getCantidad());
                        multaAPagar += multaDiaria;
                    }
                }
            } else {
                if (cal.get(Calendar.DAY_OF_YEAR) == diaActual && !abono.isAbonado()) { //al dia actual
                    abonoAPagar += this.prestamo.getCobroDiario();
                    break;
                }
            }
        }

        //buscar si tiene dias sin pago despues de la fecha limite de pago y tiene algo por abonar
        if (prestamo.getFechaLimite().compareTo(fechaActual) < 0) {
            int multaMes = UtilsPreferences.loadConfig().getCantidadMultaMes();
            int cantidadDeDiasDespuesDelMes = (int) UtilsDate.diasEntreFechas(prestamo.getFechaLimite(), fechaActual);
            if (cantidadDeDiasDespuesDelMes > 1) {
                multaAPagarMes = multaMes * (cantidadDeDiasDespuesDelMes - 1);
            }
        }

        ModelTotalAPagar modelTotalAPagar = new ModelTotalAPagar(abonoAPagar, multaAPagar, multaAPagarMes);
        this.setModelTotalAPagar(modelTotalAPagar);
        return modelTotalAPagar;
    }

    /**
     * distribuye el abono del cliente en los abonos, tomando en cuenta la prioridad del las multas si le corresponde y la antiguedad del abono
     *
     * @param abono cantidad de dinero a abonar por el cliente, intresado en el dialog
     */
    public void abonarAlPrestamo(int abono, final String multaDes, final ModelTotalAPagar model) {
        final int abonoTotal = abono;
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int diaActual = cal.get((Calendar.DAY_OF_YEAR));
        int multaDiaria = UtilsPreferences.loadConfig().getCantidadMultaDiaria();
        //distribuir pago con prioridad en multa y abonos anteriores e ir cubriendo la cantidad del abono

        List<Abono> abonos = new ArrayList<>();
        for (Abono a : prestamo.getAbonos()) {
            abonos.add(a);
        }
        Collections.sort(abonos, new Comparator<Abono>() {
            @Override
            public int compare(Abono a1, Abono a2) {
                return a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha());
            }
        });

        //comparar si el total abonado es el que deberia de tener abonado hasta hoy
        //si -> esta al corriente y puede pagar sin abonos
        //no -> no esta al corriente y debe de pagas las multas de los dias no pagados hasta hoy
        for (Abono a : abonos) {
            if (abono == 0) {
                break;
            }
            if (!a.isAbonado()) {
                cal.setTime(a.getAbonoPK().getFecha());
                if (cal.get(Calendar.DAY_OF_YEAR) < diaActual) {
                    if (abono > multaDiaria) {
                        a.getMulta().setMulta(multaDiaria);
                        abono -= multaDiaria;
                    } else {
                        a.getMulta().setMulta(abono);
                        abono = 0;
                    }
                }
                if (abono > this.prestamo.getCobroDiario()) {
                    a.setAbonado(true);
                    abono -= this.prestamo.getCobroDiario();
                } else {
                    a.setAbonado(true);
                    a.setCantidad(abono);
                    abono = 0;
                }
            } else {
                //si esta abonado y no abono mas o igual que el cobro diario es multa
                if (a.getCantidad() < this.prestamo.getCobroDiario()) {
                    int difAbonado = (this.prestamo.getCobroDiario() - a.getCantidad());
                    int difMulta = multaDiaria - a.getMulta().getMulta();
                    if (abono > difMulta) {
                        a.getMulta().setMulta(multaDiaria);
                        abono -= difMulta;
                    } else {
                        a.getMulta().setMulta(a.getMulta().getMulta() + abono);
                        abono = 0;
                    }
                    if (abono > difAbonado) {
                        a.setCantidad(this.prestamo.getCobroDiario());
                        abono -= difAbonado;
                    } else {
                        a.setCantidad(a.getCantidad() + abono);
                        abono = 0;
                    }
                }
            }
        }

        //multa postPlazo
        Date fechaActual = new Date();
        if (abono > 0) {
            if (model.getTotalMultarMes() > 0) {
                //generar los dias de multa post-plazo para cada dia de multa
                cal.setTime(abonos.get(abonos.size() - 1).getAbonoPK().getFecha()); //fecha del ultimo abono
                cal.add(Calendar.DAY_OF_YEAR, 1);
                int cantidadMultaPostPlazo = UtilsPreferences.loadConfig().getCantidadMultaMes();
                while (cal.getTime().getTime() <= fechaActual.getTime() && abono > 0) {
                    if (abono - cantidadMultaPostPlazo >= 0) {
                        Abono abonoPostPlazo = new Abono(prestamo.getId(), cal.getTime());
                        Multa multaPostPlazo = new Multa(prestamo.getId(), cal.getTime());
                        multaPostPlazo.setMulta(cantidadMultaPostPlazo);
                        multaPostPlazo.setMultaDes("Multa post-plazo");
                        abonoPostPlazo.setMulta(multaPostPlazo);
                        abonoPostPlazo.setAbonado(true);
                        abonos.add(abonoPostPlazo);
                        abono -= cantidadMultaPostPlazo;
                        cal.add(Calendar.DAY_OF_YEAR, 1);
                    }
                }
            }
        }

        prestamo.setAbonos(abonos);
        this.showLoading(true);

        ModelAbonarPrestamo modelAbonarPrestamo = new ModelAbonarPrestamo(abonoTotal, prestamo.getCliente().getId(), UtilsPreferences.loadLogedUser().getId(), prestamo);

        repository.abonarPrestamo(modelAbonarPrestamo)
                .subscribeOn(SchedulerProvider.ioT())
                .observeOn(SchedulerProvider.uiT())
                .subscribe(new Consumer<Response<Prestamo, Object>>() {
                    @Override
                    public void accept(Response<Prestamo, Object> r) throws Exception {
                        switch (r.getMeta().getStatus()) {
                            case OK:
                                proccessAbonos(prestamo.getAbonos());
                                cargarTotalesPrestamo();
                                break;
                            case ERROR:
                                showMessage("Existió un error de programación del lado del servidor");
                                break;
                        }

                        showLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showLoading(false);
                        showMessage("Existió un error de comunicación");
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * Realiza el comportamiento de inicializar y asignar la actividad con los abonos proporcionados
     *
     * @param abonos abonos del prestamo con los cuales trabajar
     */
    private void proccessAbonos(final List<Abono> abonos) {
        prestamo.setAbonos(abonos);
        this.calcularTotalesPagar();
        abonoFragment.replaceData(abonos);

        int sumaAbonos = 0;
        for (Abono abono : abonos) {
            if (abono.isAbonado()) {
                sumaAbonos += abono.getCantidad();
            }
        }
        Calendar cal = new GregorianCalendar();
        int diaDelAñoAcutal = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(prestamo.getFecha());
        int diaDelAñoFechaPrestamo = cal.get(Calendar.DAY_OF_YEAR);

        if (sumaAbonos < prestamo.getCantidadPagar() && (diaDelAñoAcutal > diaDelAñoFechaPrestamo) && this.getModelTotalAPagar().getTotalPagar() > 0) {
            this.fab.setVisibility(View.VISIBLE);
        } else {
            this.fab.setVisibility(View.GONE);
        }

    }

    /**
     * verifica si el prestamo va al corriente en sus pagos
     *
     * @param prestamo prestamo a validar
     * @return true, si el prestamo va al corriente, false, si el prestamo no va al corriente
     */
    private boolean prestamoAlCorriente(Prestamo prestamo) {
        int totalAbonadoActual = 0;
        for (Abono a : prestamo.getAbonos()) {
            if (a.isAbonado()) totalAbonadoActual += a.getCantidad();
        }
        //sacar el total que deberia de tener abonado, el dia de hoy, restar a la fecha del presamo, los dias de diferencia de hoy a la fecha de prestamos son los dias que van
        int dif = (int) UtilsDate.diasEntreFechas(this.prestamo.getFecha(), new Date());
        int totalDeberiaTener = dif * prestamo.getCobroDiario();
        return totalAbonadoActual >= totalDeberiaTener;
    }

}
