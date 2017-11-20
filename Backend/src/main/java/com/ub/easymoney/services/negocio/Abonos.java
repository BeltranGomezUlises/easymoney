/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Abono;
import com.ub.easymoney.entities.negocio.AbonoPK;
import com.ub.easymoney.managers.negocio.ManagerAbono;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.services.commos.ServiceFacade;
import com.ub.easymoney.utils.UtilsService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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

    @GET
    @Path("/prestamo/{id}")
    public Response<List<Abono>> abonosDelPrestamo(@HeaderParam("Authorization") String token, @PathParam("id") int id) {
        Response<List<Abono>> res = new Response<>();
        try {
            ManagerAbono managerAbono = new ManagerAbono();
            List<Abono> abonosDelPrestamo = managerAbono.abonosDelPrestamo(id);
            UtilsService.setOkResponse(res, abonosDelPrestamo, "Abonos del prestamo " + id, "lista de abonos del prestamo");
        } catch (Exception e) {
            UtilsService.setErrorResponse(res, e);
        }
        return res;
    }

}
