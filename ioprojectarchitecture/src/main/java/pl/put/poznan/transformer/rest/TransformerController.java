package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.Filter;
import pl.put.poznan.transformer.logic.FilterOnly;
import pl.put.poznan.transformer.logic.JSON;
import pl.put.poznan.transformer.logic.JsonComparator;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/transformer")
public class TransformerController {

    private final JsonComparator jsonComparator;
    private final Filter filter;
    private final FilterOnly filterOnly;

    private final Map<String, JSON> jsonRepository = new HashMap<>();


    public TransformerController() {
        this.jsonComparator = new JsonComparator();
        this.filter = new Filter();
        this.filterOnly = new FilterOnly();
    }

    @PostMapping("/compare")
    public String compare(@RequestBody String json1, @RequestBody String json2) {
        return jsonComparator.compareAndDisplayDifferences(json1, json2);
    }

    @PostMapping("/filter")
    public String filter(@RequestBody String text, @RequestParam(value="filterParameter", defaultValue="") String[] filterParameter) throws JsonProcessingException {
        return filter.decorate(text, filterParameter);

    }

    @PostMapping("/filterOnly")
    public String filterOnly(@RequestBody String text, @RequestParam(value="transforms", defaultValue="upper,escape") String[] transforms) throws JsonProcessingException {
        return filterOnly.decorate(text, transforms);
    }

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
