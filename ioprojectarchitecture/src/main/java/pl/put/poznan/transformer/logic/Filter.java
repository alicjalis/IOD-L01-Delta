package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public class Filter extends JsonDecorator  {
    private final String[] filterParameters;
    public Filter(JSONTransformer json, String[] parameters) {
        super(json);
        filterParameters = parameters;
    }

    public String decorate(String text)  {
        return filterString(super.decorate(text),filterParameters);
    }


    private String filterString(String text, String[] filterParameter)  {
        JSON jsNode = new JSON(text);

        ArrayList<JsonNode> toBeRemovedField = new ArrayList<>();
        ArrayList<String> toBeRemovedFieldName = new ArrayList<>();
        filterNodeRecursive(jsNode.get(),filterParameter, toBeRemovedField, toBeRemovedFieldName);
        for(int i =0;i<toBeRemovedField.size();i++) {
            ((ObjectNode) toBeRemovedField.get(i)).remove(toBeRemovedFieldName.get(i));
        }


        try {
            return jsNode.getString();
        } catch (JsonProcessingException e) {
            return "Failure during filtering process";
        }
    }

    private void filterNodeRecursive(JsonNode jsonNode, String[] filter, List<JsonNode> forRemoval, ArrayList<String> forRemovalName) {

        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            fields.forEachRemaining(field -> {

                if(Arrays.asList(filter).contains(field.getKey())) {
                    forRemoval.add(jsonNode);
                    forRemovalName.add(field.getKey());
                } else {
                    filterNodeRecursive((JsonNode) field.getValue(), filter,forRemoval, forRemovalName);
                }
            });
        } else if (jsonNode.isArray()) {
            ArrayNode arrayField = (ArrayNode) jsonNode;
            arrayField.forEach(node -> {
                filterNodeRecursive(node, filter,forRemoval, forRemovalName);
            });
        }
    }
}


