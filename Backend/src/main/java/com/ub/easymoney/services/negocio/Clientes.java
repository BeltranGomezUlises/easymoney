/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Cliente;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.managers.negocio.ManagerCliente;
import com.ub.easymoney.managers.negocio.ManagerPrestamo;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import com.ub.easymoney.services.commos.ServiceFacade;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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

    @GET
    @Path("/{id}/prestamos")
    public List<Prestamo> prestamosDelClientes(@HeaderParam("Authorization") String token, @PathParam("id") int clienteId) throws TokenInvalidoException, TokenExpiradoException {

        ManagerPrestamo managerPrestamo = new ManagerPrestamo();
        managerPrestamo.setToken(token);

        return managerPrestamo.stream().where(p -> p.getClienteId() == clienteId).collect(toList());

    }

}
