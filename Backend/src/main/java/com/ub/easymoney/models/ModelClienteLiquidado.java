/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.models;

import java.util.Date;

/**
 * Modelo para llenar la tabla del reporte de clientes liquidados
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class ModelClienteLiquidado {

    private int id;
    private String nombre;
    private String apodo;
    private String telefono;
    private Date fechaUltimoAbono;

    public ModelClienteLiquidado() {
    }

    public ModelClienteLiquidado(int id, String nombre, String apodo, String telefono, Date fechaUltimoAbono) {
        this.id = id;
        this.nombre = nombre;
        this.apodo = apodo;
        this.telefono = telefono;
        this.fechaUltimoAbono = fechaUltimoAbono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaUltimoAbono() {
        return fechaUltimoAbono;
    }

    public void setFechaUltimoAbono(Date fechaUltimoAbono) {
        this.fechaUltimoAbono = fechaUltimoAbono;
    }

}
