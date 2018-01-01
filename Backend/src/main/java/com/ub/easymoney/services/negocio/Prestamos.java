/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.negocio.ManagerPrestamo;
import com.ub.easymoney.models.ModeloPrestamoTotales;
import com.ub.easymoney.models.ModeloPrestamoTotalesGenerales;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.models.filtros.FiltroPrestamo;
import com.ub.easymoney.services.commos.ServiceFacade;
import com.ub.easymoney.utils.UtilsService;
import static com.ub.easymoney.utils.UtilsService.setErrorResponse;
import static com.ub.easymoney.utils.UtilsService.setInvalidTokenResponse;
import static com.ub.easymoney.utils.UtilsService.setOkResponse;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * servicios para registros de prestamos a clientes
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/prestamos")
public class Prestamos extends ServiceFacade<Prestamo, Integer> {

    public Prestamos() {
        super(new ManagerPrestamo());
    }

    @GET
    @Path("/totales/{id}")
    public Response<ModeloPrestamoTotales> totalesDelPrestamo(@HeaderParam("Authorization") final String token, @PathParam("id")final int id) {
        Response<ModeloPrestamoTotales> r = new Response<>();
        try {
            ManagerPrestamo managerPrestamo = new ManagerPrestamo();
            UtilsService.setOkResponse(r, managerPrestamo.totalesPrestamo(id), "totales del prestamo");
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            setInvalidTokenResponse(r);
        } catch (Exception ex) {
            setErrorResponse(r, ex);
        }
        return r;
    }
    
    @GET
    @Path("/totalesGenerales")
    public Response<ModeloPrestamoTotalesGenerales> totalesGenerales(@HeaderParam("Authorization") final String token){
        Response<ModeloPrestamoTotalesGenerales> r = new Response<>();        
        try {
            ManagerPrestamo managerPrestamo = new ManagerPrestamo();
            managerPrestamo.setToken(token);
            r.setData(managerPrestamo.totalesPrestamosGenerales());            
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            setInvalidTokenResponse(r);
        } catch (Exception ex) {
            setErrorResponse(r, ex);
        }
        return r;
    }
    
    @POST
    @Path("/cargarPrestamos")
    public Response<List<Prestamo>> listarFiltrados(@HeaderParam("Authorization") final String token, final FiltroPrestamo filtro){
        Response response = new Response();
        try {            
            ManagerPrestamo managerPrestamo = new ManagerPrestamo();
            managerPrestamo.setToken(token);
            setOkResponse(response, managerPrestamo.findAll(filtro), "Entidades encontradas");           
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            setInvalidTokenResponse(response);
        } catch (Exception ex) {
            setErrorResponse(response, ex);
        }
         return response;
    }
    
    @GET
    @Path("/prestamosPorCobrar/{cobradorId}")
    public Response<List<Prestamo>> prestamosConCobroParaHoy(@HeaderParam("Authorization") final String token, @HeaderParam("cobradorId") final int cobradorId){
        Response<List<Prestamo>> r = new Response<>();
        try {
            ManagerPrestamo managerPrestamo = new ManagerPrestamo();
            managerPrestamo.setToken(token);
            setOkResponse(r, managerPrestamo.prestamosDelCobrador(cobradorId), "prestamos del cobrador");
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            setInvalidTokenResponse(r);
        } catch (Exception ex) {
            setErrorResponse(r, ex);
        }
        return r;
    }
}
