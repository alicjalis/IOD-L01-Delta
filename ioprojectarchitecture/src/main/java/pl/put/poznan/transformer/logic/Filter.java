package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class FilterDecorator {

    public FilterDecorator()  {
        //decorate(text,filterParameter);
    }

    public String decorate(String text, String filterParameter) throws JsonProcessingException {
        JSON jsNode = new JSON(text);

        List<JsonNode> toBeRemoved = new ArrayList<>();
        filterNodeRecursive(jsNode.get(),filterParameter, toBeRemoved);
        for(int i=0;i<toBeRemoved.size();i++) {
            ((ObjectNode)toBeRemoved.get(i)).remove(filterParameter);
        }


        return jsNode.getString();
    }

    private void filterNodeRecursive(JsonNode jsonNode, String filter, List<JsonNode> forRemoval) {

        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            fields.forEachRemaining(field -> {

                if(field.getKey().equals(filter)) {
                    forRemoval.add(jsonNode);

                } else {
                    filterNodeRecursive((JsonNode) field.getValue(), filter,forRemoval);
                }
            });
        } else if (jsonNode.isArray()) {
            ArrayNode arrayField = (ArrayNode) jsonNode;
            arrayField.forEach(node -> {
                filterNodeRecursive(node, filter,forRemoval);
            });
        }
    }
}


