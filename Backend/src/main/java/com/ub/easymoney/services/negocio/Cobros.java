/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.daos.negocio.DaoCobro;
import com.ub.easymoney.entities.negocio.Cobro;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.negocio.ManagerCobro;
import com.ub.easymoney.models.ModelAbonarPrestamo;
import com.ub.easymoney.models.ModelConsultaCobros;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.services.commos.ServiceFacade;
import com.ub.easymoney.utils.UtilsJWT;
import com.ub.easymoney.utils.UtilsService;
import java.util.List;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Servicios LCRUD para los registro de cobros del sistema
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
@Path("/cobros")
public class Cobros extends ServiceFacade<Cobro, Integer> {

    public Cobros() {
        super(new ManagerCobro());
    }

    /**
     * Genera el cobro a un usuario abonandole a su prestamo y actualizando la distribución del pago
     *
     * @param token token de sesion
     * @param model modelo con el pago y su distribución
     * @return prestamo actualizado
     */
    @POST
    @Path("/generarAbono")
    public Response<Prestamo> abonarAlPrestamo(@HeaderParam("Authorization") final String token, ModelAbonarPrestamo model) {
        Response<Prestamo> r = new Response<>();
        try {
            ManagerCobro managerCobro = new ManagerCobro();
            managerCobro.setToken(token);
            UtilsService.setOkResponse(r, managerCobro.generarAbonoPrestamo(model), "Abono persitido con éxito", "");
        } catch (Exception e) {
            UtilsService.setErrorResponse(r, e);
        }
        return r;
    }

    /**
     * Consulta los cobros que ha tenido el cliente con un rango de fechas
     *
     * @param token token de sesion
     * @param m modelo con los atributos de consulta
     * @return lista de cobros del usuario obtenidos
     */
    @POST
    @Path("/cliente")
    public Response<List<Cobro>> cobrosDelCliente(@HeaderParam("Authorization") final String token, final ModelConsultaCobros m) {
        Response<List<Cobro>> r = new Response<>();
        try {
            DaoCobro daoCobro = new DaoCobro();
            UtilsJWT.validateSessionToken(token);
            UtilsService.setOkResponse(r, daoCobro.cobrosDelCliente(m.getAgrupadorId(), m.getFechaInicial(), m.getFechaFinal()), "Cobros del cliente", null);
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            UtilsService.setWarningResponse(r, e.getMessage());
        } catch (Exception e) {
            UtilsService.setErrorResponse(r, e);
        }
        return r;
    }

    /**
     * Consulta los cobros que ha realizado un cobrador con un rango de fechas
     *
     * @param token token de sesion
     * @param m modelo con los atributos de consulta
     * @return lista de cobros del cobrador obtenidos
     */
    @POST
    @Path("/cobrador")
    public Response<List<Cobro>> cobrosDelCobrador(@HeaderParam("Authorization") final String token, final ModelConsultaCobros m) {
        Response<List<Cobro>> r = new Response<>();
        try {
            DaoCobro daoCobro = new DaoCobro();
            UtilsJWT.validateSessionToken(token);
            UtilsService.setOkResponse(r, daoCobro.cobrosDelCobrador(m.getAgrupadorId(), m.getFechaInicial(), m.getFechaFinal()), "Cobros del cliente", null);
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            UtilsService.setWarningResponse(r, e.getMessage());
        } catch (Exception e) {
            UtilsService.setErrorResponse(r, e);
        }
        return r;
    }
}
