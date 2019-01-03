/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.utils.commons;

import com.ub.easymoney.utils.commons.IEntity;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import com.ub.easymoney.utils.UtilsJWT;
import java.util.List;

/**
 * fachada de manager general
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 * @param <T> class entity used to restrict the class of use
 * @param <K>
 */
public abstract class ManagerFacade<T extends IEntity, K> {

    protected Long usuario;

    public ManagerFacade() {
    }

    public ManagerFacade(String token) throws TokenInvalidoException, TokenExpiradoException {
        this.usuario = UtilsJWT.getUserIdFrom(token);
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    /**
     * asignar un token de sesion a este manager, con la intencion de validar el usuario en pemisos y registros de bitacoras
     *
     * @param token token de sesion
     * @throws TokenInvalidoException si el token proporsionado no es válido
     * @throws TokenExpiradoException si el token proporsionado ya expiró
     */
    public void setToken(String token) throws TokenInvalidoException, TokenExpiradoException {
        this.setUsuario(UtilsJWT.getUserIdFrom(token));
    }

    /**
     * metodo para persistir la entidad a base de datos
     *
     * @param entity entidad a persistir en base de datos
     * @return la entidad persistida con su _id
     * @throws Exception si existió un problema al intertar persistir
     */
    public abstract T persist(T entity) throws Exception;

    /**
     * metodo para persistir todas las entidades proporsioadas en base de datos
     *
     * @param entities lista de entidades a persistir
     * @return la lista de entidades persistidas con su propiedad _id
     * @throws Exception si existió un problema la intentar persistir
     */
    public abstract List<T> persistAll(List<T> entities) throws Exception;

    /**
     * remueve de base de datos la entidad que corresponda al id proporsionado
     *
     * @param id propiedad que identifia al objeto
     * @throws Exception
     */
    public abstract void delete(K id) throws Exception;

    /**
     * remueve de base de datos las entidades que su propiedad id corresponda con los objetos proporsionados
     *
     * @param ids lista de identificadores de las entidades
     * @throws Exception si existió algún problema al intentar remover
     */
    public abstract void deleteAll(List<K> ids) throws Exception;

    /**
     * reemplaza la entidad proporsionada por la existente en base de datos que coincida con su propiedad id
     *
     * @param entity la entidad con la cual reemplazar la existente en base de datos
     * @throws Exception si exitió un problema al actualizar
     */
    public abstract void update(T entity) throws Exception;

    /**
     * busca la entidad correspondiente al identificador proporsionado
     *
     * @param id identificador de la entidad
     * @return entidad de la base de datos
     * @throws java.lang.Exception
     */
    public abstract T findOne(K id) throws Exception;

    /**
     * busca la primer entidad existente en base de datos
     *
     * @return la entidad manejada en primer posición en base de datos
     * @throws java.lang.Exception
     */
    public abstract T findFirst() throws Exception;

    /**
     * busca todas las entidades existentes en base de datos
     *
     * @return lista con las entidades manejadas que existen en base de datos
     * @throws java.lang.Exception
     */
    public abstract List<T> findAll() throws Exception;

    /**
     * busca todas las entidades existentes en base de datos con un numero maximo de elementos a retornas
     *
     * @param max numero maximo de entidades a tomar de base de datos
     * @return lista con las entidades manejadas que existen en base de datos
     * @throws java.lang.Exception
     */
    public abstract List<T> findAll(int max) throws Exception;

    /**
     * cuenta las entidades manejadas
     *
     * @return numero de entidades existentes en base de datos
     * @throws java.lang.Exception
     */
    public abstract long count() throws Exception;

    /**
     * transforma un String s a el tipo de datos del identificador de la entidad
     *
     * @param s cadena a trasformar
     * @return K, objeto del tipo de dato del identificador de la entidad
     */
    public abstract K stringToKey(String s);

}
