package com.easymoney.data.dataSources;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ulises on 7/01/18.
 */
public interface DataSource<T, K> {

    Flowable<List<T>> findAll();
    Flowable<T> findById(K id);

    Flowable<T> persist(T entity);
    Flowable<List<T>> persist(List<T> entities);

    Flowable<T> update(T t);
    Flowable<List<T>> update(List<T> entity);

    void delete(T entity);
    void delete(List<T> entities);
    void deleteAll();
}
