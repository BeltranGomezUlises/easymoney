/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services.admin;

import com.ub.easymoney.entities.admin.Config;
import com.ub.easymoney.managers.admin.ManagerConfig;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Configs{
            
    public Configs() {        
    }
        
    @GET        
    public Config getConf(@HeaderParam("Authorization") String token) throws Exception{
        try {
            ManagerConfig manager = new ManagerConfig();
            return manager.findFirst();
        } catch (Exception ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    @PUT
    public Config updateConfig(@HeaderParam("Authorization") String token, Config conf) throws Exception{
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
    
}
