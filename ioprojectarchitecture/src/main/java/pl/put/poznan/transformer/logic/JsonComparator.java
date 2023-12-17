package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class JsonComparator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String compareAndDisplayDifferences(String jsonFilePath1, String jsonFilePath2) {
        try {
            JsonNode jsonNode1 = objectMapper.readTree(new File(jsonFilePath1));
            JsonNode jsonNode2 = objectMapper.readTree(new File(jsonFilePath2));

            // check for differences
            JsonNode differences = getDifferences(jsonNode1, jsonNode2);

            // print differences
            System.out.println("Differences between " + jsonFilePath1 + " and " + jsonFilePath2 + ":");
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(differences));
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(differences);

        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private static JsonNode getDifferences(JsonNode jsonNode1, JsonNode jsonNode2) {
        // objects are equal
        if (jsonNode1.equals(jsonNode2)) {
            return objectMapper.createObjectNode();
        }



        // comparing
        if (jsonNode1.isObject() && jsonNode2.isObject()) {
            return compareObjects((ObjectNode) jsonNode1, (ObjectNode) jsonNode2);
        }



        // return if one object is empty
        if (jsonNode1.size() > jsonNode2.size()){
            return jsonNode1;
        }else{
            return jsonNode2;
        }

    }

    private static JsonNode compareObjects(ObjectNode objectNode1, ObjectNode objectNode2) {
        // store differences here
        ObjectNode differences = objectMapper.createObjectNode();

        // getting all field names
        Iterator<String> fieldNames = objectNode1.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            // check if second object has the same field
            if (!objectNode2.has(fieldName)) {
                differences.set(fieldName, objectNode1.get(fieldName));
            } else {
                JsonNode value1 = objectNode1.get(fieldName);
                JsonNode value2 = objectNode2.get(fieldName);

                // recursion to compare objects
                if (!value1.equals(value2)) {
                    differences.set(fieldName, getDifferences(value1, value2));
                }
            }
        }

        // add fields from second object that are not in the first one
        fieldNames = objectNode2.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            if (!objectNode1.has(fieldName)) {
                differences.set(fieldName, objectNode2.get(fieldName));
            }
        }

        return differences;
    }
}
