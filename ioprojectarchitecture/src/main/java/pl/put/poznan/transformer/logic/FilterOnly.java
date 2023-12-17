package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public class FilterOnly {

    public FilterOnly()  {
        //decorate(text,filterParameter);
    }

    public String decorate(String text, String[] filterParameter) throws JsonProcessingException {
        JSON jsNode = new JSON(text);

        ArrayList<JsonNode> toBeRemovedField = new ArrayList<>();
        ArrayList<String> toBeRemovedFieldName = new ArrayList<>();
        filterNodeRecursive(jsNode.get(),filterParameter, toBeRemovedField, toBeRemovedFieldName);
        for(int i =0;i<toBeRemovedField.size();i++) {
            ((ObjectNode) toBeRemovedField.get(i)).remove(toBeRemovedFieldName.get(i));
        }


        return jsNode.getString();
    }

    private int filterNodeRecursive(JsonNode jsonNode, String filter[], List<JsonNode>  forRemoval, ArrayList<String> forRemovalName) {
        int numberOfFilterNodesAbove = 0;
        int numberOfFilterNodesCurrent = 0;
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while(fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                numberOfFilterNodesAbove = filterNodeRecursive((JsonNode) field.getValue(), filter,forRemoval,forRemovalName);


                if(Arrays.asList(filter).contains(field.getKey())) {
                    numberOfFilterNodesCurrent += 1;
                 } else if(numberOfFilterNodesAbove == 0) {
                    forRemoval.add(jsonNode);
                    forRemovalName.add(field.getKey());
                }
            }

        } else if (jsonNode.isArray()) {
            ArrayNode arrayField = (ArrayNode) jsonNode;
            for(int i=0; i < arrayField.size(); i++) {
                numberOfFilterNodesAbove = filterNodeRecursive(arrayField.get(i), filter,forRemoval, forRemovalName);
            };

        }
        return numberOfFilterNodesCurrent + numberOfFilterNodesAbove;
    }


}


