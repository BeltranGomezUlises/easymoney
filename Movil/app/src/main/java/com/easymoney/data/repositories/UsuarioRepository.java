package com.easymoney.data.repositories;

import com.easymoney.entities.Usuario;
import com.easymoney.models.ModelCambiarContra;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.schedulers.SchedulerProvider;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.models.services.Errors.ERROR_COMUNICACION;
import static com.easymoney.utils.services.UtilsWS.webServices;

/**
 * Created by ulises on 15/01/2018.
 */
public class UsuarioRepository {

    /**
     * obtiene el flujo del consumo del servicio para cambiar la contraseña del usuario
     * @param modelCambiarContra modelo para solicitar cambio de contraseña
     * @return flujo con el usuario actualizado
     */
    public Disposable cambiarContraseña(ModelCambiarContra modelCambiarContra, Consumer<Response<Usuario, Object>> onNext, Consumer<Throwable> onError){
        return webServices().cambiarContra(UtilsPreferences.loadToken(), modelCambiarContra).observeOn(SchedulerProvider.uiT())
                .subscribeOn(SchedulerProvider.ioT())
                .subscribe(onNext, onError);
    }

}
