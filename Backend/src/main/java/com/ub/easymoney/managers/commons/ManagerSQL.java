/*
 * Copyright (C) 2017 Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ub.easymoney.managers.commons;

import com.ub.easymoney.daos.commons.DaoSQLFacade;
import com.ub.easymoney.entities.commons.commons.EntitySQL;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import java.util.List;
import org.jinq.jpa.JPAJinqStream;

/**
 * fachada para manejar entidades sql 
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 * @param <T> Entidad a manejar
 * @param <K> Tipo de dato de llave primaria de la entidad
 */
public abstract class ManagerSQL<T extends EntitySQL, K> extends ManagerFacade<T, K> {

    protected final DaoSQLFacade<T, K> dao;
    
    public ManagerSQL(DaoSQLFacade<T, K> dao) {
        super();
        this.dao = dao;
    }
    
    public ManagerSQL(DaoSQLFacade dao, String token) throws TokenInvalidoException, TokenExpiradoException {
        super(token);
        this.dao = dao;
    }
    
    @Override
    public List<T> persistAll(List<T> entities) throws Exception {        
        return dao.persistAll(entities);        
    }

    @Override
    public T persist(T entity) throws Exception {
       
        dao.persist(entity);
        return entity;
    }
    @Override
    public void delete(K id) throws Exception {        
        dao.delete(id);
    }

    @Override
    public void deleteAll(List<K> ids) throws Exception {        
        dao.deleteAll(ids);
    }

    @Override
    public void update(T entity) throws Exception {
        dao.update(entity);
    }

    @Override
    public T findOne(K id) throws Exception {                  
        return dao.findOne(id);
    }

    @Override
    public List<T> findAll() throws Exception {
        return dao.findAll();
    }

    @Override
    public List<T> findAll(int max) throws Exception{
        return dao.findAll(max);
    }

    @Override
    public long count()throws Exception {
        return dao.count();
    }

    @Override
    public T findFirst() throws Exception{
        return (T) dao.findFirst();
    }
    
    public JPAJinqStream<T> stream() {
        return dao.stream();
    }

    @Override
    public K stringToKey(String s) {
        return dao.stringToPK(s);
    }

}
