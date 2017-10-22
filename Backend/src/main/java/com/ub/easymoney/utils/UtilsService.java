/*
 * Copyright (C) 2017 Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ub.easymoney.utils;

import com.ub.easymoney.models.commons.commons.enums.Status;
import com.ub.easymoney.models.commons.exceptions.AccesoDenegadoException;
import com.ub.easymoney.models.commons.exceptions.ElementosSinAccesoException;
import com.ub.easymoney.models.commons.exceptions.ParametroInvalidoException;
import com.ub.easymoney.models.commons.reponses.Response;
import static com.ub.easymoney.utils.UtilsService.SistemaOperativo.ANDROID;
import static com.ub.easymoney.utils.UtilsService.SistemaOperativo.DESCONOCIDO;
import static com.ub.easymoney.utils.UtilsService.SistemaOperativo.IOS;
import static com.ub.easymoney.utils.UtilsService.SistemaOperativo.LINUX;
import static com.ub.easymoney.utils.UtilsService.SistemaOperativo.MAC;
import static com.ub.easymoney.utils.UtilsService.SistemaOperativo.WIN;
import java.util.Arrays;
import java.util.logging.Level;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class UtilsService {

    /**
     * asigna al modelo response, la pila de causas del error de la exception e
     *
     * @param response response a asignar la pila de causas
     * @param e la exception lanzada
     */
    public static final void setCauseMessage(Response response, Throwable e) {
        String anterior = response.getMeta().getDevMessage();
        if (anterior == null) {
            response.setDevMessage("ERROR: "+ e.toString() + " CAUSE: " + e.getMessage());
        } else {
            response.setDevMessage(response.getMeta().getDevMessage() + " CAUSE: " + e.getMessage());
        }
        if (e.getCause() != null) {
            setCauseMessage(response, e.getCause());
        }else{
            response.setDevMessage(response.getMeta().getDevMessage() + " STACK: " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * asigna a response el estatus y el mensaje de un token invalido, se
     * utiliza cuando se lanzá una exception de tipo TokenInvalidoException
     *
     * @param response res
     */
    public static final void setInvalidTokenResponse(Response response) {
        response.setStatus(Status.WARNING);
        response.setDevMessage("Token inválido");
    }

    /**
     * asigna a response el status y el mensaje de un parametro invalido, se
     * utiliza cuando se lanzá una exception de tipo ParametroInvalidoException
     *
     * @param response respuesta a asignar valores
     * @param e exception lanzada de tipo ParametroInvalidoException
     */
    public static final void setParametroInvalidoResponse(Response response, ParametroInvalidoException e) {
        response.setStatus(Status.INVALID_PARAM);
        response.setMessage(e.getMessage());
        response.setDevMessage(e.toString());
    }

    /**
     * asigna a response el status y el mensaje de acceso denegado, se utiliza
     * cuando se lanza una exception de tipo AccesoDenegado
     *
     * @param response respuesta a asignar los valores
     * @param ex exception de la cual obtener los mensajes
     */
    public static final void setAccesDeniedResponse(Response response, AccesoDenegadoException ex) {
        response.setStatus(Status.ACCES_DENIED);
        response.setDevMessage(ex.toString());
        response.setMessage(ex.getMessage());
    }

    /**
     * asigna a response el status y el mensaje de acceso denegado, se utiliza
     * cuando se lanza una exception de tipo AccessDenied
     *
     * @param response respuesta a asignar los valores
     * @param mensaje mensaje a asignar para el usuario
     */
    public static final void setAccesDeniedResponse(Response response, String mensaje) {
        response.setStatus(Status.ACCES_DENIED);
        response.setMessage(mensaje);
    }

    /**
     * asignar a response el estatus WARNING y los mensajes proporcionados
     *
     * @param res modelo response generico a asignar valores
     * @param message mensaje para el usuario
     * @param devMessage mensaje para el desarrollador
     */
    public static final void setWarningResponse(Response res, String message, String devMessage) {
        res.setStatus(Status.WARNING);
        res.setMessage(message);
        res.setDevMessage(devMessage);
    }

    /**
     * asignar a response el estatus WARNING y el mensaje proporsionado
     *
     * @param res modelo response generico a asignar valores
     * @param devMessage mensaje para el desarrollador
     */
    public static final void setWarningResponse(Response res, String devMessage) {
        res.setStatus(Status.WARNING);
        res.setDevMessage(devMessage);
    }

    /**
     * asignar a response el estatus ERROR y el mensaje proporsionado para el
     * usuario
     *
     * @param res modelo response generico a asignar valores
     * @param err exception lanzada
     * @param message mensaje para el usuario
     */
    public static final void setErrorResponse(Response res, Throwable err, String message) {
        res.setStatus(Status.ERROR);
        setCauseMessage(res, err);
        res.setMessage(message);
        java.util.logging.Logger.getLogger("ERROR").log(Level.SEVERE, null, err);
    }

    /**
     * asignar a response el estatus ERROR asi como un mensaje generico
     *
     * @param res modelo response generico a asignar valores
     * @param err exception lanzada
     */
    public static final void setErrorResponse(Response res, Throwable err) {
        res.setStatus(Status.ERROR);
        res.setMessage("Existió un error de programación, consultar con el administrador del sistema");
        setCauseMessage(res, err);
        java.util.logging.Logger.getLogger("ERROR").log(Level.SEVERE, null, err);
    }

    /**
     * asigna a response el estatus OK y los mensajes proporcionados
     *
     * @param res modelo response generico
     * @param message
     * @param devMessage
     */
    public static final void setOkResponse(Response res, String message, String devMessage) {
        res.setStatus(Status.OK);
        res.setMessage(message);
        res.setDevMessage(devMessage);
    }

    /**
     * asigna a response el estatus OK mas los mensajes proporcionados, ademas
     * de poner en metadata el objeto data proporsionado
     *
     * @param res
     * @param data
     * @param message
     * @param devMessage
     */
    public static final void setOkResponse(Response res, Object data, String message, String devMessage) {
        res.setStatus(Status.OK);
        res.setData(data);
        res.setMessage(message);
        res.setDevMessage(devMessage);
    }

    /**
     * asignar a response el estatus OK, el metadata y un mensaje para el
     * desarrollador
     *
     * @param res
     * @param data
     * @param devMessage
     */
    public static final void setOkResponse(Response res, Object data, String devMessage) {
        res.setStatus(Status.OK);
        res.setData(data);
        res.setDevMessage(devMessage);
    }

    /**
     * asignar a response el estatus OK, el metadata y un mensaje para el
     * desarrollador
     *
     * @param res respuesta a asignar los valores
     * @param message mensaje para el usuario final
     * @param data objeto a insertar en res
     */
    public static final void setOkResponse(Response res, String message, Object data) {
        res.setStatus(Status.OK);
        res.setData(data);
        res.setMessage(message);
    }

    /**
     * asigna solo le estatus OK a response y le añade el mensaje para el
     * desarrollador
     *
     * @param res
     * @param devMessage
     */
    public static final void setOkResponse(Response res, String devMessage) {
        res.setStatus(Status.OK);
        res.setDevMessage(devMessage);
    }

    /**
     * asignar a response el estatus PARCIAL_ACCESS y el metadata los elementos a los que no se le pudo realizar la operacion
     * @param res response al cual asignar los valores
     * @param e excepcion lanzada por manager de tipo ElementosSinAccesoException
     */
    public static final void setElementosSinAccesoResponse(Response res, ElementosSinAccesoException e){
        res.setStatus(Status.PARCIAL_ACCESS);
        res.setMessage(e.getMessage());
        res.setDevMessage("no se tiene acceso a los elementos");
        res.setMetaData(e.getElementosSinAcceso());
    }
    
    /**
     * genera el enum correspondiente al sistema operativo del cual la cabecera
     * UserAgent de la peticion indica
     *
     * @param userAgent cadena con el texto de la cabecera User-Agent
     * @return enumarador del sistema operativo
     */
    public static final String obtenerSistemaOperativo(String userAgent) {
        if (userAgent.contains("Linux x")) {
            return LINUX.toString();
        }
        if (userAgent.contains("Windows")) {
            return WIN.toString();
        }
        if (userAgent.contains("iPhone OS")) {
            return MAC.toString();
        }
        if (userAgent.contains("Android")) {
            return ANDROID.toString();
        }
        if (userAgent.contains("Mac")) {
            return IOS.toString();
        } else {
            return DESCONOCIDO.toString();
        }
    }

    /**
     * enumerador del los sistemas operativos conocidos
     */
    public static enum SistemaOperativo {
        WIN, LINUX, MAC, ANDROID, IOS, DESCONOCIDO
    }
}
