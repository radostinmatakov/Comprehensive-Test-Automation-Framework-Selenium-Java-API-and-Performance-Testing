package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Serializer {
    public static String convertObjectToJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error converting object to JSON string", e);
        }
    }
}
