package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/transformer")
public class TransformerController {

    private final JsonComparator jsonComparator;
    private final JsonDecoratorBuilder builder;

    private final Map<String, JSON> jsonRepository = new HashMap<>();



    public TransformerController() {
        this.jsonComparator = new JsonComparator();
        this.builder = new JsonDecoratorBuilder();
    }

    @PostMapping("/compare/")
    public String compare(@RequestBody String body) {
        return jsonComparator.compareAndDisplayDifferences(body, body);
    }

    @PostMapping("/")
    public String filter(@RequestBody String text, @RequestParam(value="format", defaultValue="minify") String format,
                         @RequestParam(value="filter", defaultValue="") String[] filterParameter,
                         @RequestParam(value="filterOnly", defaultValue="") String[] filterOnlyParameter)  {

        JSONTransformer transform = builder.getDecorator(format,filterParameter, filterOnlyParameter);
        return transform.decorate(text);
    }

//    @PostMapping("/filterOnly")
//    public String filterOnly(@RequestBody String text, @RequestParam(value="filterParameter", defaultValue="") String[] filterParameter) throws JsonProcessingException {
//        //return filterOnly.decorate(text, filterParameter);
//
//    }

    @PostMapping("/json")
    public String createJSON(@RequestBody String jsonString) {
        JSON json = new JSON(jsonString);
        String id = UUID.randomUUID().toString();
        jsonRepository.put(id, json);
        return id;
    }

    @GetMapping("/json/{id}")
    public String getJSON(@PathVariable String id) {
        JSON json = jsonRepository.get(id);
        if (json == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "JSON with id " + id + " not found");
        }
        try {
            return json.getString();
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing JSON", e);
        }
    }




}