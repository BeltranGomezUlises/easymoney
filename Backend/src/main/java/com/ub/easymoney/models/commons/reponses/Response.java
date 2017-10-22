/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models.commons.reponses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ub.easymoney.models.commons.commons.enums.Status;

/**
 * modelo de respuesta generico para servicios
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private Object data;
    private MetaData meta;
        
    public Response() {
        meta = new MetaData();
    }

    public Response(Object data, String message, Status status, String devMessage) {
        this.data=data;
        this.meta = new MetaData(message, status, devMessage);
    }   

    public Response(Status status,String devMessage) {        
        this.meta = new MetaData(status, devMessage);
    }

    public Response(Object data, Status status, String devMessage) {
        this.data = data;
        this.meta = new MetaData(status, devMessage);
    }
    
    public Response(Object data, Object metaData, String message, Status status, String devMessage) {
        this.data=data;
        this.meta = new MetaData(message, status, devMessage);
    }   

    public Response(Status status,String devMessage, Object metaData ) {        
        this.meta = new MetaData(status, devMessage);
    }

    public Response(Object data, Object metaData, Status status, String devMessage) {
        this.data = data;
        this.meta = new MetaData(status, devMessage);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
    
    public void setMetaData(Object metaData){
        this.meta.setMetaData(metaData);
    }
    
    public void setDevMessage(String devMessage){
        this.meta.setDevMessage(devMessage);
    }
}
