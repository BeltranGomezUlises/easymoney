/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Abono;
import com.ub.easymoney.entities.negocio.AbonoPK;
import com.ub.easymoney.managers.negocio.ManagerAbono;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.services.commos.ServiceFacade;
import com.ub.easymoney.utils.UtilsService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * servicios para registros de abono a un prestamo
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/abonos")
public class Abonos extends ServiceFacade<Abono, AbonoPK> {

    public Abonos() {
        super(new ManagerAbono());
    }

    /**
     * devuelve la lista de abonos de un prestamo (detalle del prestamo)
     *
     * @param token token de sesion
     * @param id identificador del prestamo al que se busca su detalle
     * @return lista de abonos del prestamo buscado en data
     */
    @GET
    @Path("/prestamo/{id}")
    public Response<List<Abono>> abonosDelPrestamo(@HeaderParam("Authorization") String token, @PathParam("id") int id) {
        Response<List<Abono>> res = new Response<>();
        try {
            ManagerAbono managerAbono = new ManagerAbono();
            managerAbono.setToken(token);
            List<Abono> abonosDelPrestamo = managerAbono.abonosDelPrestamo(id);
            UtilsService.setOkResponse(res, abonosDelPrestamo, "Abonos del prestamo " + id, "lista de abonos del prestamo");
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            UtilsService.setInvalidTokenResponse(res);
        } catch (Exception ex) {
            UtilsService.setErrorResponse(res, ex);
        }
        return res;
    }

    /**
     * genera un abono al prestamo cuando es necesario realizar un ajuste de pago,sucede cuando el cliente no va al corriente o es necesario ponerle
     * @param token token de sesion
     * @param abonoAgregar abono a agregar
     * @return 
     */
    @POST
    @Path("/agregarAjuste")
    public Response<Abono> agregarAbonoAjuste(@HeaderParam("Authorization") String token, Abono abonoAgregar) {
        Response<Abono> res = new Response<>();
        try {
            this.alta(token, abonoAgregar);
        } catch (Exception ex) {
            UtilsService.setErrorResponse(res, ex);
        }
        return res;
    }

}
