package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSON {
    private JsonNode node ;

    public JSON(String jsonString) {
        ObjectMapper  objectMapper = new ObjectMapper();
        try {
            node = objectMapper.readValue(jsonString, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNode get() {return node;}

    public String getString() throws JsonProcessingException {
        ObjectMapper  objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(node);
    }
}