/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.admin;

import com.ub.easymoney.entities.admin.Config;
import com.ub.easymoney.entities.admin.Usuario;
import com.ub.easymoney.managers.admin.ManagerConfig;
import com.ub.easymoney.managers.admin.ManagerUsuario;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.utils.UtilsService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/configs")
public class Configs {

    public Configs() {
    }

    @GET
    public Config getConf(@HeaderParam("Authorization") String token) throws Exception {
        try {
            ManagerConfig manager = new ManagerConfig();
            return manager.findFirst();
        } catch (Exception ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @PUT
    public Config updateConfig(@HeaderParam("Authorization") String token, Config conf) throws Exception {
        try {
            ManagerConfig manager = new ManagerConfig();
            manager.setToken(token);
            manager.update(conf);
            return conf;
        } catch (Exception ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @GET
    @Path("/reset")
    public Response<List> resetConfig() {
        Response<List> r = new Response();

        List res = new ArrayList();
        try {
            ManagerConfig manager = new ManagerConfig();
            Config conf = new Config();
            conf.setContraDefault("1234");
            conf.setDiasPrestamo(30);
            conf.setPorcentajeInteresPrestamo(20);
            manager.deleteAll(manager.findAll().stream().map(c -> c.getId()).collect(toList()));
            manager.persist(conf);
            res.add(conf);

            ManagerUsuario managerUsuario = new ManagerUsuario();
            managerUsuario.deleteAll(managerUsuario.findAll().stream().map(u -> u.getId()).collect(toList()));

            Usuario u = new Usuario();
            u.setNombre("admin");
            u.setContra("easymoney");
            u.setNombreCompleto("administrador del sistema");
            u.setTipo(true);

            managerUsuario.persist(u);

            res.add(u);

            UtilsService.setOkResponse(r, res, "configuraciones y usuario predeterminado", "configuracion default asignada");
        } catch (Exception ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

}
