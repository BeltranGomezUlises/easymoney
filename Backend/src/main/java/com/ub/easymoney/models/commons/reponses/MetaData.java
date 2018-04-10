/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models.commons.reponses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ub.easymoney.models.commons.commons.enums.Status;

/**
 * modelo contenedor de los metadatos de una respuesta
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@JsonInclude(Include.NON_NULL)
public class MetaData {

    private String message;
    private Status status;
    private String devMessage;

    @com.webcohesion.enunciate.metadata.ClientName(value = "data")
    private Object metaData;

    public MetaData() {
        this.status = Status.OK;
    }

    public MetaData(String message) {
        this.message = message;
        this.status = Status.OK;
    }

    public MetaData(Status status, String devMessage) {
        this.status = status;
        this.devMessage = devMessage;
    }

    public MetaData(String message, Status status, String devMessage) {
        this.message = message;
        this.status = status;
        this.devMessage = devMessage;
    }

    public MetaData(Object metaData, String message) {
        this.message = message;
        this.status = Status.OK;
        this.metaData = metaData;
    }

    public MetaData(Object metaData, Status status, String devMessage) {
        this.metaData = metaData;
        this.status = status;
        this.devMessage = devMessage;
    }

    public MetaData(Object metaData, String message, Status status, String devMessage) {
        this.metaData = metaData;
        this.message = message;
        this.status = status;
        this.devMessage = devMessage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public void setMetaData(Object metaData) {
        this.metaData = metaData;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public Object getMetaData() {
        return metaData;
    }

    @Override
    public String toString() {
        return "MetaData{" + "message=" + message + ", status=" + status + ", devMessage=" + devMessage + ", metaData=" + metaData + '}';
    }

}
