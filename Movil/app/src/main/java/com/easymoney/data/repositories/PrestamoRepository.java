package com.easymoney.data.repositories;

import com.easymoney.entities.Prestamo;
import com.easymoney.models.EnumPrestamos;
import com.easymoney.models.ModelAbonar;
import com.easymoney.models.ModelPrestamoAbonado;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.utils.UtilsPreferences.loadLogedUser;
import static com.easymoney.utils.UtilsPreferences.loadToken;
import static com.easymoney.utils.schedulers.SchedulerProvider.ioT;
import static com.easymoney.utils.schedulers.SchedulerProvider.uiT;
import static com.easymoney.utils.services.UtilsWS.webServices;

/**
 * Created by ulises on 7/01/18.
 */
public class PrestamoRepository {

    private static PrestamoRepository INSTANCE;

    /**
     * singleton de PrestamoRepository
     *
     * @return intancia de PrestamoRepository
     */
    public static PrestamoRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PrestamoRepository();
        }
        return INSTANCE;
    }

    /**
     * devuelve los datos totales de un prestamo
     *
     * @param prestamoId identificador del prestamo
     * @return Response con el modelo de totales de un prestamo
     */
    public Disposable totalesPrestamo(int prestamoId,
                                      Consumer<Response<ModelPrestamoTotales, Object>> onNext,
                                      Consumer<Throwable> onError) {
        return webServices().totalesDelPrestamo(UtilsPreferences.loadToken(), prestamoId)
                .subscribeOn(ioT())
                .observeOn(uiT())
                .subscribe(onNext, onError);
    }

    /**
     * Genera el abono al prestamo y actualiza el prestamo con la distribucion del pago
     *
     * @param modelAbonar modelo contenedor de los valores necesarios asi como el prestamo con la dsitribucion de abonos a actualizar
     * @return prestamo actualizado
     */
    public Disposable abonar(ModelAbonar modelAbonar,
                             Consumer<Response<ModelPrestamoAbonado, Object>> onNext,
                             Consumer<Throwable> onError) {
        return webServices().abonarPrestamo(UtilsPreferences.loadToken(), modelAbonar)
                .observeOn(uiT())
                .subscribeOn(ioT())
                .subscribe(onNext, onError);
    }

    /**
     * Carga un prestamo por su id
     *
     * @param prestamoId identificador del prestamo
     * @param onNext     consumer para continuar
     * @param onError    consumer para error
     * @return response con prestamo
     */
    public Disposable cargarPrestamo(int prestamoId,
                                     Consumer<Response<Prestamo, Object>> onNext,
                                     Consumer<Throwable> onError) {
        return webServices().getPrestamo(UtilsPreferences.loadToken(), prestamoId)
                .observeOn(uiT())
                .subscribeOn(ioT())
                .subscribe(onNext, onError);
    }

    public Disposable renovar(int prestamoId, int cantidadNuevoPrestamo,
                              Consumer<Response<Integer, Object>> onNext,
                              Consumer<Throwable> onError) {
        return webServices().renovarPrestamo(UtilsPreferences.loadToken(),
                prestamoId, cantidadNuevoPrestamo)
                .observeOn(uiT())
                .subscribeOn(ioT())
                .subscribe(onNext, onError);
    }

    public Disposable findAll(EnumPrestamos enumPrestamos,
                              Consumer<Response<List<Prestamo>, Object>> onNext,
                              Consumer<Throwable> onError) {
        if (enumPrestamos == EnumPrestamos.POR_COBRAR || enumPrestamos == EnumPrestamos.POR_COBRAR_HOY) {
            return webServices().prestamosPorCobrar(loadToken(), loadLogedUser().getId())
                    .observeOn(uiT())
                    .subscribeOn(ioT())
                    .subscribe(onNext, onError);
        } else {
            return webServices().prestamosDelCobrador(loadToken(), loadLogedUser().getId())
                    .observeOn(uiT())
                    .subscribeOn(ioT())
                    .subscribe(onNext, onError);
        }
    }
}
