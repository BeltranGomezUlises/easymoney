package com.easymoney.modules.detallePrestamo;

import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Cobro;
import com.easymoney.entities.DistribucionCobro;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelAbonar;
import com.easymoney.models.ModelImpresionAbono;
import com.easymoney.models.ModelPrestamoAbonado;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.activities.Funcion;
import com.easymoney.utils.bluetoothPrinterUtilities.UtilsPrinter;

import java.util.Date;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.models.services.Errors.ERROR_COMUNICACION;

/**
 * Created by ulises on 15/01/2018.
 */
public class DetallePrestamoPresenter extends DetallePrestamoContract.Presenter {

    private final PrestamoRepository repository = PrestamoRepository.getInstance();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Prestamo prestamo;

    DetallePrestamoPresenter(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    @Override
    public void subscribe() {
        this.llenarDatosGenerales();
        this.prestamoTotales();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
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
     * distribuye el abono del cliente en los abonos, tomando en cuenta la prioridad del las multas si le corresponde y la antiguedad del abono
     *
     * @param abono cantidad de dinero a abonar por el cliente, intresado en el dialog
     */
    void abonar(int abono, String multaDes) {
        getFragment().showLoading();
        ModelAbonar map = new ModelAbonar(prestamo.getId(), abono, multaDes);
        compositeDisposable.add(
                repository.abonar(map, new Consumer<Response<ModelPrestamoAbonado, Object>>() {
                            @Override
                            public void accept(final Response<ModelPrestamoAbonado, Object> r) {
                                getFragment().stopShowLoading();
                                evalResponse(r, new Runnable() {
                                    @Override
                                    public void run() {
                                        UtilsPreferences.setPrestamoCobradoHoy(prestamo.getId());
                                        prestamo = r.getData().getPrestamo();
                                        getFragment().llenarDatosGenerales(prestamo);
                                        getFragment().llenarTotales(r.getData().getDistribucionCobro());
                                        //getFragment().showOK("Abono realizado");

                                        ModelImpresionAbono modelImpresion = crearModelImpresionAbono(r.getData());
                                        UtilsPrinter.imprimirRecibo(modelImpresion, new Funcion<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) {
                                                getFragment().showERROR(throwable.getMessage());
                                            }
                                        });
                                    }
                                }, new Runnable() {
                                    @Override
                                    public void run() {
                                        getFragment().showWARNING(r.getMeta().getMessage());
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

    @Override
    void reimprimirTicket(Cobro c) {
        ModelPrestamoAbonado mpa = new ModelPrestamoAbonado();
        mpa.setPrestamo(this.prestamo);
        mpa.setDistribucionCobro(c.getDistribucionCobro());
        ModelImpresionAbono modelImpresion = crearModelImpresionAbono(mpa);
        UtilsPrinter.imprimirRecibo(modelImpresion, new Funcion<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                getFragment().showERROR(throwable.getMessage());
            }
        });
    }

    private ModelImpresionAbono crearModelImpresionAbono(ModelPrestamoAbonado model) {
        DistribucionCobro dc = model.getDistribucionCobro();
        int totalAbonado = dc.getTotalAbonado();
        int totalMultado = dc.getTotalMultado();
        int totalMultadoMes = dc.getTotalMultarMes();
        int porcentajeAbonado = dc.getPorcentajePagado();
        int totalParaSaldar = dc.getPorPagarLiquidar();

        int abonado = dc.getAbonado();
        int multado = dc.getMultado();
        int multadoPostPlazo = dc.getMultadoPostPlazo();

        Prestamo p = model.getPrestamo();
        String fechaHoraAbono = UtilsDate.format_D_MM_YYYY_HH_MM(new Date());
        String fechaPrestamo = UtilsDate.format_D_MM_YYYY(p.getFecha());
        String fechaLimite = UtilsDate.format_D_MM_YYYY(p.getFechaLimite());

        return new ModelImpresionAbono(
                p.getId(),
                p.getCobrador().getNombre(),
                p.getCliente().getNombre(),
                fechaHoraAbono,
                fechaPrestamo,
                fechaLimite,
                abonado,
                multado,
                multadoPostPlazo,
                p.getCantidad(),
                p.getCantidadPagar(),
                totalAbonado,
                totalMultado,
                totalMultadoMes,
                totalParaSaldar,
                porcentajeAbonado
        );
    }

    @Override
    void prestamoTotales() {
        this.repository.totalesPrestamo(this.prestamo.getId(), new Consumer<Response<ModelPrestamoTotales, Object>>() {
            @Override
            public void accept(final Response<ModelPrestamoTotales, Object> r) throws Exception {
                evalResponse(r, new Runnable() {
                    @Override
                    public void run() {
                        getFragment().llenarTotales(r.getData());
                    }
                });
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getFragment().showERROR(ERROR_COMUNICACION);
            }
        });
    }


}
