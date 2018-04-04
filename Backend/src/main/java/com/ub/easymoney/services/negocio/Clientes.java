/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Cliente;
import com.ub.easymoney.managers.negocio.ManagerCliente;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.models.filtros.FiltroCliente;
import com.ub.easymoney.services.commos.ServiceFacade;
import static com.ub.easymoney.utils.UtilsService.setErrorResponse;
import static com.ub.easymoney.utils.UtilsService.setInvalidTokenResponse;
import static com.ub.easymoney.utils.UtilsService.setOkResponse;
import java.util.List;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * servicios LCRUD de clientes del negocio a los que se les asignaran prestamos
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/clientes")
public class Clientes extends ServiceFacade<Cliente, Integer> {

    public Clientes() {
        super(new ManagerCliente());
    }

    @POST
    @Path("/cargarClientes")
    public Response<List<Cliente>> listarFiltrados(@HeaderParam("Authorization") String token, FiltroCliente filtro) {
        Response response = new Response();
        try {
            ManagerCliente managerCliente = new ManagerCliente();
            managerCliente.setToken(token);
            setOkResponse(response, managerCliente.findAll(filtro), "Entidades encontradas");
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            setInvalidTokenResponse(response);
        } catch (Exception ex) {
            setErrorResponse(response, ex);
        }
        return response;
    }
}
