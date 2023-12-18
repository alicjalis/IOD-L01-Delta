package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MinifyDecorator extends JsonDecorator {
    public MinifyDecorator(JSONTransformer json) {
        super(json);
    }

    public String decorate(String jsonString)  {
        return minify(super.decorate(jsonString));
    }

    private String minify(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readValue(jsonString, JsonNode.class);
            return objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            return "Invalid json";
        }
    }
}
