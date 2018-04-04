/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class UtilsJson {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static String jsonSerialize(Object o) throws JsonProcessingException {
        return MAPPER.writeValueAsString(o);
    }

    public static <T> T jsonDeserialize(String json, Class<T> clazz) throws IOException {
        return (T) MAPPER.readValue(json, clazz);
    }

}
