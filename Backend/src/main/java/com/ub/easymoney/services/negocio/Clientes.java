/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.negocio;

import com.ub.easymoney.entities.negocio.Cliente;
import com.ub.easymoney.managers.negocio.ManagerCliente;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.services.commos.ServiceFacade;
import java.util.List;
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

    @Override
    public Response<Cliente> eliminar(String token, Cliente t) {
        return super.eliminar(token, t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<Cliente> modificar(String token, Cliente t) {
        return super.modificar(token, t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<Cliente> alta(String token, Cliente t) {
        return super.alta(token, t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<Cliente> detalle(String token, String id) {
        return super.detalle(token, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<List<Cliente>> listar(String token) {
        return super.listar(token); //To change body of generated methods, choose Tools | Templates.
    }        
}
