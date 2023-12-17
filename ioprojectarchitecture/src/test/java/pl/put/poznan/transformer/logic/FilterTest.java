package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    @Test
    void testDecorateSimpleNested() throws JsonProcessingException {

        String testJSON = "{\"name\": \"Kowalski\", \"details\": {\"age\": 20, \"city\": \"Poznan\"}}";

        Filter decorator = new Filter();
        assertEquals("{\"name\":\"Kowalski\",\"details\":{\"city\":\"Poznan\"}}",decorator.decorate(testJSON,"age"));
    }

    @Test
    void testDecorateMultipleNested() throws JsonProcessingException {

        String testJSON = "{\"type\":\"donut\",\"name\":\"Cake\",\"ppu\":0.55,\"batters\":{\"batter\":[{\"type\":\"Regular\"},{\"type\":\"Chocolate\"},{\"type\":\"Blueberry\"},{\"type\":\"Devil's Food\"}]},\"topping\":[{\"type\":\"None\"},{\"type\":\"Glazed\"},{\"type\":\"Sugar\"},{\"type\":\"Powdered Sugar\"},{\"type\":\"Chocolate with Sprinkles\"},{\"type\":\"Chocolate\"},{\"type\":\"Maple\"}]}";

        Filter decorator = new Filter();
        assertEquals("{\"type\":\"donut\",\"name\":\"Cake\",\"ppu\":0.55,\"batters\":{\"batter\":[{\"type\":\"Regular\"},{\"type\":\"Chocolate\"},{\"type\":\"Blueberry\"},{\"type\":\"Devil's Food\"}]},\"topping\":[{\"type\":\"None\"},{\"type\":\"Glazed\"},{\"type\":\"Sugar\"},{\"type\":\"Powdered Sugar\"},{\"type\":\"Chocolate with Sprinkles\"},{\"type\":\"Chocolate\"},{\"type\":\"Maple\"}]}",decorator.decorate(testJSON,"id"));
    }
}