package com.easymoney.data.dataSources;

import java.util.List;

/**
 * Created by ulises on 7/01/18.
 */
public interface DataSource<T, K> {

    List<T> findAll();
    T findById(K id);

    T persist(T entity);
    List<T> persist(T... entities);

    T update(T t);
    List<T> update(T... entity);

    void delete(T entity);
    void delete (T... entities);

}
