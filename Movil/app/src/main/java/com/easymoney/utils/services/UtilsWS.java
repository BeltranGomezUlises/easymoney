package com.easymoney.utils.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Clase de utilerias para el consumo de Servicios web REST
 * Created by ulises on 22/02/17.
 */
public class UtilsWS {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static IService webServices() {
        return SingletonRetrofit.getIntance().create(IService.class);
    }

    public static String asStringJson(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T readValue(Object content, TypeReference valueTypeRef) throws IOException {
        return MAPPER.readValue(MAPPER.writeValueAsString(content), valueTypeRef);
    }

    public static <T> T readValue(Object content, Class<T> clase) throws IOException {
        return MAPPER.readValue(MAPPER.writeValueAsString(content), clase);
    }

}
