package com.fantasyhnl.util;

import com.fantasyhnl.exception.InvalidJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonToObjectMapper {
    private static final Logger logger = LoggerFactory.getLogger(JsonToObjectMapper.class);

    private final ObjectMapper objectMapper;

    public JsonToObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <S> Root<S> mapToRootObject(String body, Class<S> valueType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Root.class, valueType);
            Root<S> root = objectMapper.readValue(body, javaType);
            logger.info("Root object {} \n", root);
            return root;
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException("Invalid JSON response");
        }
    }
}