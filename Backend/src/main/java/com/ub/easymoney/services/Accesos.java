/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ub.easymoney.models.ModelLogin;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.utils.UtilsJWT;
import static com.ub.easymoney.utils.UtilsService.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * servicios de accesos al sistema, entrada, recuperación, contacto 
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/accesos")
public class Accesos {

    /**
     * servicio de autentifición para obtener el token de sesion necesario para interactuar con el sistema
     * @param modelLogin
     * @return 
     */
    @Path("/login")
    @POST
    public Response login(ModelLogin modelLogin) {
        Response r = new Response();
        try {
            if (modelLogin.getUser().equals("admin") && modelLogin.getPass().equals("1234")) {                
                r.setMetaData(UtilsJWT.generateSessionToken(modelLogin.getUser()));
                r.setMessage("Bienvenido " + modelLogin.getUser());
                r.setDevMessage("Token de sesion necesario para los siguientes servicios en la cabecera Authorizaiton");                
            }else{
                setWarningResponse(r, "No se reconoce usuario y/o contraseña", "usuario inválido");
            }
        } catch (JsonProcessingException e) {
            setErrorResponse(r, e);
        } catch (Exception e) {
            setErrorResponse(r, e);
        }
        return r;
    }

}
