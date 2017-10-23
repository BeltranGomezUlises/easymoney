/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.managers.admin.ManagerUsuario;
import com.ub.easymoney.models.ModelLogin;
import com.ub.easymoney.models.commons.exceptions.UsuarioInexistenteException;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.utils.UtilsJWT;
import static com.ub.easymoney.utils.UtilsService.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * servicios de accesos al sistema, entrada, recuperación, contacto 
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
            ManagerUsuario managerUsuario = new ManagerUsuario();
            Usuario u = managerUsuario.login(modelLogin);
            
            r.setMetaData(UtilsJWT.generateSessionToken(u.getId() + ""));
            r.setMessage("Bienvenido " + modelLogin.getUser());
            r.setDevMessage("Token de sesion necesario para los siguientes servicios en la cabecera Authorizaiton");                                                    
        } catch (JsonProcessingException e) {
            setErrorResponse(r, e);
        } catch (UsuarioInexistenteException e) {
            setWarningResponse(r, e.getMessage(), "verifique usuario");
        }
        return r;
    }

}
