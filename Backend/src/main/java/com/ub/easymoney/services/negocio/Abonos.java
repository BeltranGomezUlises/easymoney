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
import java.util.List;
import javax.ws.rs.Path;

/**
 * servicios para registros de abono a un prestamo
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Path("/abonos")
public class Abonos extends ServiceFacade<Abono, AbonoPK>{
    
    public Abonos() {
        super(new ManagerAbono());
    }

    @Override
    public Response<Abono> eliminar(String token, Abono t) {
        return super.eliminar(token, t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<Abono> modificar(String token, Abono t) {
        return super.modificar(token, t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<Abono> alta(String token, Abono t) {
        return super.alta(token, t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<Abono> detalle(String token, String id) {
        return super.detalle(token, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<List<Abono>> listar(String token) {
        return super.listar(token); //To change body of generated methods, choose Tools | Templates.
    }
    
}
