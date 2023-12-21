package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final JsonDecoratorBuilder builder;

    /**
     * Repozytorium do przechowywania JSONów.
     */
    private final Map<String, JSON> jsonRepository = new HashMap<>();

    /**
     * Przy użyciu obiektu logger zapisywane są informacje dotyczące zapytań oraz wystąpionych błędów
     */
    private static final Logger logger = LoggerFactory.getLogger(TransformerController.class);

    /**
     * Konstruktor klasy TransformerController.
     */
    public TransformerController() {
        this.builder = new JsonDecoratorBuilder();
    }


    /**
     * Wrapper do rozpakowania jsonow do porównania w funkcji compare
     */
    public class RequestWrapper {
        public String json1;
        public String json2;
    }

    /**
     * Porównuje dwa JSONy i zwraca różnice pomiędzy nimi.
     *
     * @param requestBody JSONy do porównania zapakowane w strukture RequestWrapper
     * @return różnice między dwoma JSONami
     */
    @PostMapping("/compare/")
    public String compare(@RequestBody RequestWrapper requestBody) {
        logger.info("Compare POST - json: "+requestBody);
        JSONTransformer transform = new JsonComparator(new JsonDecorator( new BasicJsonTransformer()),requestBody.json1);
        return transform.decorate(requestBody.json2);
    }

    /**
     * Filtruje plik JSON na podstawie podanych parametrów.
     *
     * @param text JSON w foramcie tekstu do przekształcenia
     * @param format format jsona wynikowaego: minify - json zminifikowany, prettify - json czytelny
     * @param filterParameter opcjonalna lista własności do odfiltrowania z jsona
     * @param filterOnlyParameter opcjonalna lista jedynych własności do pozostawienia
     * @return JSON po przekształceniach
     */
    @PostMapping("/")
    public String filter(@RequestBody String text, @RequestParam(value="format", defaultValue="minify") String format,
                         @RequestParam(value="filter", defaultValue="") String[] filterParameter,
                         @RequestParam(value="filterOnly", defaultValue="") String[] filterOnlyParameter) {
        logger.info("Filter POST - json: "+text);

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
        logger.info("Create json POST - json: "+jsonString);
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
        logger.info("GET json, id - : "+id);
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
