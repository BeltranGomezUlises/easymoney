/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.admin.ManagerUsuario;
import com.ub.easymoney.managers.negocio.ManagerCliente;
import com.ub.easymoney.managers.negocio.ManagerPrestamo;
import com.ub.easymoney.models.ModelCargarPrestamos;
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
import java.util.ArrayList;
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
    public Response<ModeloPrestamoTotales> totalesDelPrestamo(@HeaderParam("Authorization") final String token, @PathParam("id") final int id) {
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
    public Response<ModeloPrestamoTotalesGenerales> totalesGenerales(@HeaderParam("Authorization") final String token) {
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
    @Path("/totalesGeneralesFiltro")
    public Response<ModeloPrestamoTotalesGenerales> totalesGeneralesFiltro(@HeaderParam("Authorization") final String token, final FiltroPrestamo filtro) {
        Response<ModeloPrestamoTotalesGenerales> r = new Response<>();
        try {
            ManagerPrestamo managerPrestamo = new ManagerPrestamo();
            managerPrestamo.setToken(token);
            r.setData(managerPrestamo.totalesPrestamosGenerales(filtro));
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            setInvalidTokenResponse(r);
        } catch (Exception ex) {
            setErrorResponse(r, ex);
        }
        return r;
    }

    @POST
    @Path("/cargarPrestamos")
    public Response<List<ModelCargarPrestamos>> listarFiltrados(@HeaderParam("Authorization") final String token, final FiltroPrestamo filtro) {
        Response<List<ModelCargarPrestamos>> response = new Response();
        try {
            ManagerPrestamo managerPrestamo = new ManagerPrestamo();
            managerPrestamo.setToken(token);
            setOkResponse(response, managerPrestamo.cargarPrestamos(filtro), "Entidades encontradas");
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            setInvalidTokenResponse(response);
        } catch (Exception ex) {
            setErrorResponse(response, ex);
        }
        return response;
    }

    /**
     * Consulta los prestamos asignado a un cobrador que aun se pueden efectuar cobros
     *
     * @param token token de sesion
     * @param cobradorId identificador del cobrador
     * @return lista de prestamos encontrados del cobrador que se pueden cobrar
     */
    @GET
    @Path("/prestamosPorCobrar/{cobradorId}")
    public Response<List<Prestamo>> prestamosConCobroParaHoy(@HeaderParam("Authorization") final String token, @PathParam("cobradorId") final int cobradorId) {
        Response<List<Prestamo>> r = new Response<>();
        try {
            ManagerPrestamo managerPrestamo = new ManagerPrestamo();
            managerPrestamo.setToken(token);
            setOkResponse(r, managerPrestamo.prestamosPorCobrar(cobradorId), "prestamos del cobrador");
        } catch (TokenExpiradoException | TokenInvalidoException e) {
            setInvalidTokenResponse(r);
        } catch (Exception ex) {
            setErrorResponse(r, ex);
        }
        return r;
    }

    /**
     * Consulta todos los prestamos asignado al cobrador
     *
     * @param token token de sesion
     * @param cobradorId identificador dle cobrador
     * @return lista de prestamos que pertenen al cobrador
     */
    @GET
    @Path("/prestamosDelCobrador/{cobradorId}")
    public Response<List<Prestamo>> prestamosCobrador(@HeaderParam("Authorization") final String token, @PathParam("cobradorId") final int cobradorId) {
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

    /**
     * servicio que genera datos de prestamos para las pruebas
     *
     * @return
     */
    @GET
    @Path("/generarPruebas")
    public List<Prestamo> crearPrestamosDePruebas() {
        List<Prestamo> prestamos = new ArrayList<>();

        ManagerPrestamo managerPrestamo = new ManagerPrestamo();
        ManagerCliente managerCliente = new ManagerCliente();
        ManagerUsuario managerUsuario = new ManagerUsuario();

        try {
            //fecha dentro del rango de cobro
            for (int i = 0; i < 3; i++) {
                Prestamo p = new Prestamo();
                p.setCliente(managerCliente.findFirst());
                p.setCobrador(managerUsuario.stream().where(u -> u.getTipo() == false).findFirst().get());
                p.setCantidad(5000);
                managerPrestamo.persistPruebaDentroMes(p);
                prestamos.add(p);
            }
            //fecha para cerrar el mes
            for (int i = 0; i < 3; i++) {
                Prestamo p = new Prestamo();
                p.setCliente(managerCliente.findFirst());
                p.setCobrador(managerUsuario.stream().where(u -> u.getTipo() == false).findFirst().get());
                p.setCantidad(5000);
                managerPrestamo.persistPruebaPorCerrarMes(p);
                prestamos.add(p);
            }
            //fecha despues de cierre de mes
            for (int i = 0; i < 3; i++) {
                Prestamo p = new Prestamo();
                p.setCliente(managerCliente.findFirst());
                p.setCobrador(managerUsuario.stream().where(u -> u.getTipo() == false).findFirst().get());
                p.setCantidad(5000);
                managerPrestamo.persistPruebaCerrardoMes(p);
                prestamos.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return prestamos;
    }

}
