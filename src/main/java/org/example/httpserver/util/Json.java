package org.example.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {
    private static ObjectMapper myObjectMapper = defaultObjectMapper();
    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return om;
    }
    public static JsonNode parse(String jsonSource) throws IOException {
        return myObjectMapper.readTree(jsonSource);
    }
    public static <T> T fromJson(JsonNode node, Class<T> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node,clazz);
    }
    public static JsonNode toJson(Object object){
        return myObjectMapper.valueToTree(object);
    }
    public static String stringify(JsonNode jsonNode) throws JsonProcessingException {
        return generateJson(jsonNode,false);
    }
    public static String stringifyPretty(JsonNode jsonNode) throws JsonProcessingException {
        return generateJson(jsonNode,true);
    }
    private static String generateJson(Object object, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if(pretty){
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(objectWriter);
    }
}
