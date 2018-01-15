package com.easymoney.models.services;

/**
 * Created by ulises on 30/12/17.
 */

public class Response<T, K> {

    private T data;
    private MetaData<K> meta;

    public Response() {
        meta = new MetaData();
    }

    public Response(T data, String message, Status status, String devMessage) {
        this.data=data;
        this.meta = new MetaData(message, status, devMessage);
    }

    public Response(Status status,String devMessage) {
        this.meta = new MetaData(status, devMessage);
    }

    public Response(T data, Status status, String devMessage) {
        this.data = data;
        this.meta = new MetaData(status, devMessage);
    }

    public Response(T data, Object metaData, String message, Status status, String devMessage) {
        this.data=data;
        this.meta = new MetaData(message, status, devMessage);
    }

    public Response(Status status,String devMessage, Object metaData ) {
        this.meta = new MetaData(status, devMessage);
    }

    public Response(T data, Object metaData, Status status, String devMessage) {
        this.data = data;
        this.meta = new MetaData(status, devMessage);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }

    public void setMessage(String message){
        this.meta.setMessage(message);
    }

    public void setStatus(Status estatus){
        this.meta.setStatus(estatus);
    }

    public void setMetaData(K metaData){
        this.meta.setMetaData(metaData);
    }

    public void setDevMessage(String devMessage){
        this.meta.setDevMessage(devMessage);
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", meta=" + meta +
                '}';
    }
}
