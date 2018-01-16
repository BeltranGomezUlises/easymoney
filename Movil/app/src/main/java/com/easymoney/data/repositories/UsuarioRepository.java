package com.easymoney.data.repositories;

import com.easymoney.entities.Usuario;
import com.easymoney.models.ModelCambiarContra;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;

import io.reactivex.Flowable;

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
    public Flowable<Response<Usuario, Object>> cambiarContraseña(ModelCambiarContra modelCambiarContra){
        return webServices().cambiarContra(UtilsPreferences.loadToken(), modelCambiarContra);
    }

}
