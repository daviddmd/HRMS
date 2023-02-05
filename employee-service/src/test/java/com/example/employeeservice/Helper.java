package com.example.employeeservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Helper {
    public static <T> String toJson(ObjectMapper objectMapper, T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T fromJson(ObjectMapper mapper, MvcResult result, Class<T> tClass) throws JsonProcessingException, UnsupportedEncodingException {
        return mapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), tClass);
    }

    public static <T> List<T> listFromJson(ObjectMapper mapper, MvcResult result, Class<T[]> tClass) throws JsonProcessingException, UnsupportedEncodingException {
        return Arrays.asList(mapper.readValue(result.getResponse().getContentAsString(), tClass));
    }
}
