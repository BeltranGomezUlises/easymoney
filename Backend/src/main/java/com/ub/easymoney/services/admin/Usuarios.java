/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.admin;

import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.managers.admin.ManagerUsuario;
import com.ub.easymoney.models.ModelCambiarContra;
import com.ub.easymoney.models.commons.exceptions.ParametroInvalidoException;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.services.commos.ServiceFacade;
import static com.ub.easymoney.utils.UtilsService.*;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * servicios LCRUD de usuarios del sistema
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Usuarios extends ServiceFacade<Usuario, Integer> {

    public Usuarios() {
        super(new ManagerUsuario());
    }

    @GET
    @Path("/usuariosCobradores")
    public Response<List<Usuario>> usuariosCobradores(@HeaderParam("Authorization") String token) {
        Response<List<Usuario>> res = new Response();
        try {
            ManagerUsuario managerUsuario = new ManagerUsuario();
            managerUsuario.setToken(token);
            setOkResponse(res, managerUsuario.usuariosCobradores(), "usuarios cobradores");
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            setInvalidTokenResponse(res);
        } catch (Exception e) {
            setErrorResponse(res, e);
        }
        return res;
    }

    @POST
    @Path("/cambiarContra")
    public Response<Usuario> cambiarContraseña(@HeaderParam("Authorization") String token, ModelCambiarContra modelCambiarContra) {
        Response<Usuario> res = new Response<>();
        try {
            ManagerUsuario managerUsuario = new ManagerUsuario();
            managerUsuario.setToken(token);
            setOkResponse(res, "Contraseña actualizada", managerUsuario.cambiarContraseña(modelCambiarContra));
        } catch (ParametroInvalidoException e) {
            setWarningResponse(res, e.getMessage(), null);
        } catch (Exception e) {
            setErrorResponse(res, e);
        }
        return res;
    }
}
