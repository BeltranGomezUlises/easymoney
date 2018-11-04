package com.easymoney.modules.detallePrestamo;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Abono;
import com.easymoney.entities.Multa;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelAbonarPrestamo;
import com.easymoney.models.ModelDistribucionDeAbono;
import com.easymoney.models.ModelImpresionAbono;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.ModelTotalAPagar;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.activities.Funcion;
import com.easymoney.utils.bluetoothPrinterUtilities.UtilsPrinter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.models.services.Errors.ERROR_COMUNICACION;

/**
 * Created by ulises on 15/01/2018.
 */
public class DetallePrestamoPresenter extends DetallePrestamoContract.Presenter {

    private static final String MULTA_POST_PLAZO = "Multa post-plazo";
    private final PrestamoRepository repository = PrestamoRepository.getInstance();

    private FloatingActionButton fab;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Prestamo prestamo;
    private ModelTotalAPagar modelTotalAPagar;

    DetallePrestamoPresenter(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    ModelTotalAPagar getModelTotalAPagar() {
        return modelTotalAPagar;
    }

    private void setModelTotalAPagar(ModelTotalAPagar modelTotalAPagar) {
        this.modelTotalAPagar = modelTotalAPagar;
    }

    @Override
    public void cargarTotalesPrestamo() {
        compositeDisposable.add(
                repository.totalesPrestamo(prestamo.getId(),
                        new Consumer<Response<ModelPrestamoTotales, Object>>() {
                            @Override
                            public void accept(final Response<ModelPrestamoTotales, Object> r) throws Exception {
                                getFragment().stopShowLoading();
                                evalResponse(r, new Runnable() {
                                    @Override
                                    public void run() {
                                        getFragment().setTotales(r.getData());
                                    }
                                });
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
    public void cargarAbonosPrestamo() {
        compositeDisposable.add(
                repository.abonosPrestamo(prestamo.getId(),
                        new Consumer<Response<List<Abono>, Object>>() {
                            @Override
                            public void accept(final Response<List<Abono>, Object> r) throws Exception {
                                evalResponse(r, new Runnable() {
                                    @Override
                                    public void run() {
                                        List<Abono> abonosDelPrestamo = r.getData();
                                        for (Abono abono : abonosDelPrestamo) {
                                            abono.getAbonoPK().setFecha(UtilsDate.dateWithOffSet(abono.getAbonoPK().getFecha()));
                                        }
                                        proccessAbonos(abonosDelPrestamo);
                                    }
                                });
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getFragment().showERROR(ERROR_COMUNICACION);
                            }
                        })
        );
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

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    private void llenarDatosGenerales() {
        getFragment().llenarDatosGenerales(prestamo);
    }

    /**
     * Construye el modelo de los totales a pagar del prestamo
     *
     * @return modelo con abono a pagar, multa a pagar y la multa del mes en modelo
     */
    ModelTotalAPagar calcularTotalesPagar() {
        int abonoAPagar = 0;
        int multaAPagar = 0;
        int multaAPagarMes = 0;

        Calendar cal = new GregorianCalendar();
        Date fechaActual = new Date();
        int diaActual = cal.get((Calendar.DAY_OF_YEAR));
        int multaDiaria = UtilsPreferences.loadConfig().getCantidadMultaDiaria();

        List<Abono> abonos = new ArrayList<>();
        abonos.addAll(prestamo.getAbonos());
        Collections.sort(abonos, new Comparator<Abono>() {
            @Override
            public int compare(Abono a1, Abono a2) {
                return a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha());
            }
        });

        //considerar si los dias no abonados son todos dias sin multa, si hay un dia no abonado y que no esta en dias sin multa, entonces ignorar dias sin multa
        boolean ignorarMulta = this.ignorarMulta(abonos);

        for (Abono abono : abonos) {
            cal.setTime(abono.getAbonoPK().getFecha());
            //si llegamos al dia de hoy solo sumar lo que corresponde pagar al dia
            if (cal.get(Calendar.DAY_OF_YEAR) < diaActual) { //anteriores al dia de hoy
                if (!abono.isAbonado()) {
                    abonoAPagar += this.prestamo.getCobroDiario();
                    if (!ignorarMulta) {
                        multaAPagar += multaDiaria;
                    }
                } else {
                    //si esta abonado y no abono mas o igual que el cobro diario es multa
                    if (abono.getCantidad() < this.prestamo.getCobroDiario()) {
                        abonoAPagar += (this.prestamo.getCobroDiario() - abono.getCantidad());
                        if (!ignorarMulta) {
                            multaAPagar += multaDiaria;
                        }
                    }
                }
            } else {
                if (cal.get(Calendar.DAY_OF_YEAR) == diaActual) { //al dia actual
                    if (!abono.isAbonado()) {
                        abonoAPagar += this.prestamo.getCobroDiario();
                    } else {
                        abonoAPagar += this.prestamo.getCobroDiario() - abono.getCantidad();
                    }
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
    void abonarAlPrestamo(int abono, String multaDes, final ModelTotalAPagar model) {
        getFragment().showLoading();

        final int abonoTotal = abono;
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int diaActual = cal.get((Calendar.DAY_OF_YEAR));
        int multaDiaria = UtilsPreferences.loadConfig().getCantidadMultaDiaria();
        //distribuir pago con prioridad en multa y abonos anteriores e ir cubriendo la cantidad del abono

        List<Abono> abonos = new ArrayList<>();
        abonos.addAll(prestamo.getAbonos());
        Collections.sort(abonos, new Comparator<Abono>() {
            @Override
            public int compare(Abono a1, Abono a2) {
                return a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha());
            }
        });

        //valores de distribucion de pago
        int totalDeAbono = 0;
        int totalDeMulta = 0;
        int totalDeMultaMes = 0;

        boolean ignorarMulta = this.ignorarMulta(abonos);
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
                    if (!ignorarMulta) {
                        if (abono > multaDiaria) {
                            a.getMulta().setMulta(multaDiaria);
                            a.getMulta().setMultaDes(multaDes);
                            abono -= multaDiaria;
                            totalDeMulta += multaDiaria;
                        } else {
                            totalDeMulta += abono;
                            a.getMulta().setMulta(abono);
                            a.getMulta().setMultaDes(multaDes);
                            abono = 0;
                        }
                    }
                }
                if (abono > this.prestamo.getCobroDiario()) {
                    a.setAbonado(true);
                    abono -= this.prestamo.getCobroDiario();
                    totalDeAbono += this.prestamo.getCobroDiario();
                } else {
                    a.setAbonado(true);
                    a.setCantidad(abono);
                    totalDeAbono += abono;
                    abono = 0;
                }
            } else {
                int difAbonado = (this.prestamo.getCobroDiario() - a.getCantidad());
                cal.setTime(a.getAbonoPK().getFecha());
                if (cal.get(Calendar.DAY_OF_YEAR) >= diaActual) {
                    if (abono > difAbonado) {
                        a.setCantidad(this.prestamo.getCobroDiario());
                        abono -= difAbonado;
                        totalDeAbono += difAbonado;
                    } else {
                        a.setCantidad(a.getCantidad() + abono);
                        totalDeAbono += abono;
                        abono = 0;
                    }
                } else {
                    //si esta abonado y no abono mas o igual que el cobro diario es multa
                    if (a.getCantidad() < this.prestamo.getCobroDiario()) {
                        int difMulta = multaDiaria - a.getMulta().getMulta();
                        if (abono > difMulta) {
                            a.getMulta().setMulta(multaDiaria);
                            a.getMulta().setMultaDes(multaDes);
                            abono -= difMulta;
                            totalDeMulta += difMulta;
                        } else {
                            a.getMulta().setMulta(a.getMulta().getMulta() + abono);
                            a.getMulta().setMultaDes(multaDes);
                            totalDeMulta += abono;
                            abono = 0;
                        }
                        if (abono > difAbonado) {
                            a.setCantidad(this.prestamo.getCobroDiario());
                            abono -= difAbonado;
                            totalDeAbono += difAbonado;
                        } else {
                            a.setCantidad(a.getCantidad() + abono);
                            totalDeAbono += abono;
                            abono = 0;

                        }
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
                        multaPostPlazo.setMultaDes(MULTA_POST_PLAZO);
                        abonoPostPlazo.setMulta(multaPostPlazo);
                        abonoPostPlazo.setAbonado(true);
                        abonos.add(abonoPostPlazo);
                        abono -= cantidadMultaPostPlazo;
                        totalDeMultaMes += cantidadMultaPostPlazo;
                        cal.add(Calendar.DAY_OF_YEAR, 1);
                    }
                }
            }
        }

        final ModelDistribucionDeAbono modelDistribucionDeAbono
                = new ModelDistribucionDeAbono(totalDeAbono, totalDeMulta, totalDeMultaMes);

        prestamo.setAbonos(abonos);
        final ModelAbonarPrestamo modelAbonarPrestamo = new ModelAbonarPrestamo(
                abonoTotal,
                prestamo.getCliente().getId(),
                UtilsPreferences.loadLogedUser().getId(),
                prestamo);

        compositeDisposable.add(
                repository.abonarPrestamo(modelAbonarPrestamo, new Consumer<Response<Prestamo, Object>>() {
                            @Override
                            public void accept(Response<Prestamo, Object> r) {
                                getFragment().stopShowLoading();
                                evalResponse(r, new Runnable() {
                                    @Override
                                    public void run() {
                                        proccessAbonos(prestamo.getAbonos());
                                        cargarTotalesPrestamo();
                                        UtilsPreferences.setPrestamoCobradoHoy(modelAbonarPrestamo.getPrestamo().getId());

                                        ModelImpresionAbono modelImpresion =
                                                crearModelImpresionAbono(prestamo, modelDistribucionDeAbono);

                                        String macAddress = UtilsPreferences.loadMacPrinter();
                                        if (macAddress == null || macAddress.isEmpty()) {
                                            getFragment().showWARNING("No hay impresora configurada");
                                        } else {
                                            UtilsPrinter.imprimirRecibo(
                                                    modelImpresion,
                                                    macAddress,
                                                    getFragment().getContext(),
                                                    new Funcion<Throwable>() {
                                                        @Override
                                                        public void accept(Throwable throwable) {
                                                            getFragment().showERROR("Error de comunicación con impresora");
                                                        }
                                                    });
                                        }
                                    }
                                });
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                getFragment().stopShowLoading();
                                getFragment().showERROR(ERROR_COMUNICACION);
                            }
                        }
                ));
    }

    /**
     * Realiza el comportamiento de inicializar y asignar la actividad con los abonos proporcionados
     *
     * @param abonos abonos del prestamo con los cuales trabajar
     */
    private void proccessAbonos(final List<Abono> abonos) {
        prestamo.setAbonos(abonos);
        this.calcularTotalesPagar();
        getFragment().replaceData(abonos);

        int sumaAbonos = 0;
        for (Abono abono : abonos) {
            if (abono.isAbonado()) {
                sumaAbonos += abono.getCantidad();
            }
        }
        if (sumaAbonos < prestamo.getCantidadPagar()) {
            this.fab.setVisibility(View.VISIBLE);
        } else {
            this.fab.setVisibility(View.GONE);
        }

    }

    /**
     * Decide si una lista de abonos del prestamo deberia de ignorarse la multa o no
     *
     * @param abonos lista de abonos de un prestamo
     * @return true si deberia de ignorarce la multa, false si deberria aplicarse la multa
     */
    private boolean ignorarMulta(List<Abono> abonos) {
        Calendar cal = new GregorianCalendar();
        int diaActual = cal.get((Calendar.DAY_OF_YEAR));
        for (Abono abono : abonos) {
            cal.setTime(abono.getAbonoPK().getFecha());
            if (cal.get(Calendar.DAY_OF_YEAR) < diaActual) { //anteriores al dia de hoy
                if (!abono.isAbonado()) {
                    if (!this.ignorarDiaDeMulta(cal.get(Calendar.DAY_OF_WEEK))) {
                        return false;
                    }

                }
            }
        }
        return true;
    }

    /**
     * Verifica si el dia de la fecha del abono existe en los dias sin multa
     *
     * @param diaSemanaDelABono indice del dia de la semana obtenido por Calendar con la fecha del abono
     * @return true si debe de ser ignorado la multa del abono, false si no debe de ser ignorado
     */
    private boolean ignorarDiaDeMulta(int diaSemanaDelABono) {
        diaSemanaDelABono -= 1;
        boolean res = false;
        if (this.prestamo.getCliente().getDiasSinMulta() != null
                && !this.prestamo.getCliente().getDiasSinMulta().isEmpty()) {
            List<String> numDias = Arrays.asList(this.prestamo.getCliente().getDiasSinMulta().split(","));
            if (numDias.contains(String.valueOf(diaSemanaDelABono))) {
                res = true;
            }
        }
        return res;
    }


    /**
     * Crea el modelo con los datos necesarios para impresion del recibo del abono
     *
     * @param p prestamo abonado
     * @param d datos de distribucion de abono
     * @return modelo con los datos calculados para la impresion del reibo
     */
    private ModelImpresionAbono crearModelImpresionAbono(Prestamo p, ModelDistribucionDeAbono d) {
        int totalAbonado = 0;
        int totalMultado = 0;
        int totalMultadoMes = 0;

        for (Abono abono : p.getAbonos()) {
            if (abono.isAbonado()) {
                totalAbonado += abono.getCantidad();
                if (abono.getMulta().getMultaDes().equals(MULTA_POST_PLAZO)) {
                    totalMultadoMes += abono.getMulta().getMulta();
                } else {
                    totalMultado += abono.getMulta().getMulta();
                }
            }
        }

        float porcentajeFormateado = new BigDecimal((float) totalAbonado / (float) p.getCantidadPagar() * 100f)
                .setScale(2, RoundingMode.HALF_UP)
                .floatValue();

        int totalParaSaldar = p.getCantidadPagar() - totalAbonado;
        if (totalParaSaldar < 0) {
            totalParaSaldar = 0;
        }
        return new ModelImpresionAbono(
                p.getId(),
                p.getCobrador().getNombre(),
                p.getCliente().getNombre(),
                UtilsDate.format_D_MM_YYYY_HH_MM(new Date()),
                UtilsDate.format_D_MM_YYYY_HH_MM(p.getFecha()),
                UtilsDate.format_D_MM_YYYY(p.getFechaLimite()),
                d.getAbonado(),
                d.getMultado(),
                d.getMultadoMes(),
                p.getCantidad(),
                p.getCantidadPagar(),
                totalAbonado,
                totalMultado,
                totalMultadoMes,
                totalParaSaldar,
                porcentajeFormateado
        );
    }

}
