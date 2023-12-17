package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class FilterOnlyTest {

    @Test
    void testDecorateNested() throws JsonProcessingException {

        String testJSON = "{\"name\": \"Jan\",\"surname\": \"Kowalski\", \"details\": {\"age\": 20, \"city\": \"Poznan\"},\"sex\": \"male\"}";
        FilterOnly decorator = new FilterOnly();

        String[] parameters = {"surname"};
        assertEquals("{\"surname\":\"Kowalski\"}",
                decorator.decorate(testJSON,parameters));

        String[] parameters2 = {"city"};
        assertEquals("{\"details\":{\"city\":\"Poznan\"}}",
                decorator.decorate(testJSON,parameters2));

    }

    @Test
    void testDecorateMultipleNested() throws JsonProcessingException {

        String testJSON = "{\"id\":\"0001\",\"type\":\"donut\",\"name\":\"Cake\",\"ppu\":0.55,\"batters\":{\"batter\":[{\"id\":\"1001\",\"type\":\"Regular\"},{\"id\":\"1002\",\"type\":\"Chocolate\"},{\"id\":\"1003\",\"type\":\"Blueberry\"},{\"id\":\"1004\",\"type\":\"Devil's Food\"}]},\"topping\":[{\"id\":\"5001\",\"type\":\"None\"},{\"id\":\"5002\",\"type\":\"Glazed\"},{\"id\":\"5005\",\"type\":\"Sugar\"},{\"id\":\"5007\",\"type\":\"Powdered Sugar\"},{\"id\":\"5006\",\"type\":\"Chocolate with Sprinkles\"},{\"id\":\"5003\",\"type\":\"Chocolate\"},{\"id\":\"5004\",\"type\":\"Maple\"}]}";
        FilterOnly decorator = new FilterOnly();

        String[] parameters = {"type"};
        assertEquals("{\"type\":\"donut\",\"batters\":{\"batter\":[{\"type\":\"Regular\"},{\"type\":\"Chocolate\"},{\"type\":\"Blueberry\"},{\"type\":\"Devil's Food\"}]},\"topping\":[{\"type\":\"None\"},{\"type\":\"Glazed\"},{\"type\":\"Sugar\"},{\"type\":\"Powdered Sugar\"},{\"type\":\"Chocolate with Sprinkles\"},{\"type\":\"Chocolate\"},{\"type\":\"Maple\"}]}",
                decorator.decorate(testJSON,parameters));
    }

    @Test
    void testDecorateMultipleParameters() throws JsonProcessingException {

        String testJSON = "{\"school_name\":\"Dunder Miflin\",\"class\":\"Year 1\",\"info\":{\"president\":\"Michael Scott\",\"address\":\"Scranton, Pennsylvania\",\"contacts\":{\"email\":\"admin@e.com\",\"tel\":\"123456789\"}},\"students\":[{\"id\":\"A1\",\"name\":\"Jim\",\"math\":60,\"physics\":66,\"chemistry\":61},{\"id\":\"A2\",\"name\":\"Dwight\",\"math\":89,\"physics\":76,\"chemistry\":51},{\"id\":\"A3\",\"name\":\"Kevin\",\"math\":79,\"physics\":90,\"chemistry\":78}]}";
        String[] parameters = {"school_name","name"};

        FilterOnly decorator = new FilterOnly();
        assertEquals("{\"school_name\":\"Dunder Miflin\",\"students\":[{\"name\":\"Jim\"},{\"name\":\"Dwight\"},{\"name\":\"Kevin\"}]}",
                decorator.decorate(testJSON,parameters));
    }

}