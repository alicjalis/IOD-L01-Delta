package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * TransformerController jest odpowiedzialny za obsługę żądań HTTP do formatowania plików JSON.
 *
 * @author Alicja Lis, Józef Godlewski
 */
@RestController
@RequestMapping("/transformer")
public class TransformerController {

    private final JsonComparator jsonComparator;
    private final JsonDecoratorBuilder builder;

    /**
     * Repozytorium do przechowywania JSONów.
     */
    private final Map<String, JSON> jsonRepository = new HashMap<>();

    /**
     * Konstruktor klasy TransformerController.
     */
    public TransformerController() {
        this.jsonComparator = new JsonComparator();
        this.builder = new JsonDecoratorBuilder();
    }

    /**
     * Porównuje dwa JSONy i zwraca różnice.
     *
     * @param body JSON do porównania
     * @return różnice między dwoma JSONami
     */
    @PostMapping("/compare/")
    public String compare(@RequestBody String body) {
        return jsonComparator.compareAndDisplayDifferences(body, body);
    }

    /**
     * Filtruje plik JSON na podstawie podanych parametrów.
     *
     * @param text tekst JSON do filtrowania
     * @param format format filtrowania
     * @param filterParameter parametry filtrowania
     * @param filterOnlyParameter parametry filtrowania
     * @return JSON po filtrowaniu
     */
    @PostMapping("/")
    public String filter(@RequestBody String text, @RequestParam(value="format", defaultValue="minify") String format,
                         @RequestParam(value="filter", defaultValue="") String[] filterParameter,
                         @RequestParam(value="filterOnly", defaultValue="") String[] filterOnlyParameter) {

        JSONTransformer transform = builder.getDecorator(format,filterParameter, filterOnlyParameter);
        return transform.decorate(text);
    }

    /**
     * Tworzy nowego JSONa i zapisuje go do repozytorium.
     *
     * @param jsonString JSON do zapisania
     * @return identyfikator zapisanego JSONa
     */
    @PostMapping("/json")
    public String createJSON(@RequestBody String jsonString) {
        JSON json = new JSON(jsonString);
        String id = UUID.randomUUID().toString();
        jsonRepository.put(id, json);
        return id;
    }

    /**
     * Pobiera plik JSON z repozytorium na podstawie podanego identyfikatora.
     *
     * @param id identyfikator JSONa do pobrania
     * @return JSON po pobraniu
     */
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
