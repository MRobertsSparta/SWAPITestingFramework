package com.sparta.framework.injection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.dto.PersonCollectionDTO;
import com.sparta.framework.dto.PersonDTO;
import com.sparta.framework.exception.ConnectionManagementException;

public class Injector {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T getDTO(String json, Class<T> c) {
        try {
            return mapper.readValue(json, c);
        } catch (JsonProcessingException e) {
            throw new ConnectionManagementException("Given JSON is invalid, " + json);
        }
    }
}
