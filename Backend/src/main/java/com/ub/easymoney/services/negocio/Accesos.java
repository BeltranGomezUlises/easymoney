/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ub.easymoney.entities.admin.Config;
import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.managers.admin.ManagerConfig;
import com.ub.easymoney.managers.admin.ManagerUsuario;
import com.ub.easymoney.models.ModelLogin;
import com.ub.easymoney.models.ModelLoginResponse;
import com.ub.easymoney.models.commons.exceptions.UsuarioInexistenteException;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.utils.UtilsJWT;
import static com.ub.easymoney.utils.UtilsService.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * servicios de accesos al sistema, entrada, recuperación, contacto
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/accesos")
public class Accesos {

    /**
     * servicio de autentifición para obtener el token de sesion necesario para interactuar con el sistema
     *
     * @param modelLogin
     * @return
     */
    @Path("/login")
    @POST
    public Response<ModelLoginResponse> login(ModelLogin modelLogin) {
        Response<ModelLoginResponse> r = new Response();
        try {
            ManagerUsuario managerUsuario = new ManagerUsuario();
            Usuario u = managerUsuario.login(modelLogin);

            ManagerConfig managerConfig = new ManagerConfig();
            Config conf = managerConfig.findFirst();

            ModelLoginResponse modelLoginResponse = new ModelLoginResponse(u, conf);

            setOkResponse(r, modelLoginResponse, UtilsJWT.generateSessionToken(u.obtenerIdentificador() + ""), "Bienvenido " + modelLogin.getUser(), "Token de sesion necesario para los siguientes servicios en la cabecera Authorizaiton");
        } catch (JsonProcessingException e) {
            setErrorResponse(r, e);
        } catch (UsuarioInexistenteException e) {
            setWarningResponse(r, e.getMessage(), "verifique usuario");
        } catch (Exception ex) {
            setErrorResponse(r, ex);
        }
        return r;
    }

    /**
     * servicio de reseteo de configuraciones, para uso solo en desarrollo
     *
     * @return
     */
    @GET
    @Path("/resetAll")
    public Response<List> resetUsuarios() {
        Response r = new Response();
        try {
            ManagerUsuario managerUsuario = new ManagerUsuario();
            managerUsuario.deleteAll(managerUsuario.findAll().stream().map(Usuario::getId).collect(toList()));

            Usuario admin = new Usuario();
            admin.setNombre("admin");
            admin.setContra("1234");

            managerUsuario.persist(admin);

            ManagerConfig managerConfig = new ManagerConfig();
            managerConfig.deleteAll(managerConfig.findAll().stream().map(Config::getId).collect(toList()));

            Config config = new Config();
            config.setDiasPrestamo(30);
            config.setPorcentajeInteresPrestamo(20);

            managerConfig.persist(config);

            List objects = new ArrayList();
            objects.add(admin);
            objects.add(config);

            setOkResponse(r, objects, "configuraciones restablecidas con estos datos");

        } catch (Exception e) {
            setErrorResponse(r, e);
        }
        return r;
    }

}
