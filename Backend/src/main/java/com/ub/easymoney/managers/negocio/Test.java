/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ulises Beltrán Gómez - beltrangomezulises@gmail.com
 */
public class Test {
    public static void main(String[] args) throws ParseException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        Date fechaFinal = sdf.parse("2018/03/15");
        Date fechaInicial = sdf.parse("2018/02/1");
                
        long diffMillis = fechaFinal.getTime() - fechaInicial.getTime();
        System.out.println("Diferencia de dias = " + TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS));
        
        
    }
}
