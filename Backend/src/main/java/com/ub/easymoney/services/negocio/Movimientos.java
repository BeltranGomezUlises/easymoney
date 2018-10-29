/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Movimiento;
import com.ub.easymoney.managers.negocio.ManagerMovimiento;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.models.filtros.FiltroMovimientos;
import com.ub.easymoney.services.commos.ServiceFacade;
import com.ub.easymoney.utils.UtilsService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/movimientos")
public class Movimientos extends ServiceFacade<Movimiento, Integer> {

    public Movimientos() {
        super(new ManagerMovimiento());
    }

    /**
     * Obtiene los movimientos generados por un cobrador en especifico
     *
     * @param token token de sesion
     * @param cobradorId identificador del cobrador
     * @return lista de movimietos que fueron generados por el cobrador
     */
    @GET
    @Path("/cobrador/{cobradorId}")
    public Response<List<Movimiento>> movimientosDelCobrador(@HeaderParam("Authorization") final String token, @PathParam("cobradorId") final int cobradorId) {
        Response<List<Movimiento>> r = new Response();
        try {
            ManagerMovimiento managerMovimiento = new ManagerMovimiento();
            managerMovimiento.setToken(token);
            UtilsService.setOkResponse(r, managerMovimiento.movimientosDelCobrador(cobradorId), "Movimientos del cobradro");
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            UtilsService.setWarningResponse(r, e.getMessage(), null);
        } catch (Exception e) {
            UtilsService.setErrorResponse(r, e);
        }
        return r;
    }

    /**
     * Obtiene los movimientos que cumplen con el objeto de filtrado
     *
     * @param token token de sesion
     * @param filtro modelo con las propiedades a filtrar los movimientos
     * @return lista de movimientos que cumplen con el filtro proporsionado
     */
    @Path("/filtro")
    @POST
    public Response<List<Movimiento>> movimientosFiltrados(@HeaderParam("Authorization") String token, 
            FiltroMovimientos filtro) {
        Response<List<Movimiento>> r = new Response();
        try {
            ManagerMovimiento managerMovimiento = new ManagerMovimiento();
            managerMovimiento.setToken(token);
            UtilsService.setOkResponse(r, managerMovimiento.movimientos(filtro), "Movimientos filtrados");
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            UtilsService.setWarningResponse(r, e.getMessage(), null);
        } catch (Exception e) {
            UtilsService.setErrorResponse(r, e);
        }
        return r;
    }

}
