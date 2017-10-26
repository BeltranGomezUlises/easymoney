/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
//        // following code can be used to customize Jersey 1.x JSON provider:
//        try {
//            Class jacksonProvider = Class.forName("org.codehaus.jackson.jaxrs.JacksonJsonProvider");
//            resources.add(jacksonProvider);
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.ub.easymoney.services.admin.Usuarios.class);
        resources.add(com.ub.easymoney.services.admin.Utilerias.class);
        resources.add(com.ub.easymoney.services.negocio.Abonos.class);
        resources.add(com.ub.easymoney.services.negocio.Accesos.class);
        resources.add(com.ub.easymoney.services.negocio.Clientes.class);
        resources.add(com.ub.easymoney.services.negocio.Cobradores.class);
        resources.add(com.ub.easymoney.services.negocio.Multas.class);
        resources.add(com.ub.easymoney.services.negocio.Prestamos.class);
    }
    
}
