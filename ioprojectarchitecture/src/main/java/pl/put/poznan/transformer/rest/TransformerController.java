package pl.put.poznan.transformer.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.JsonComparator;
import pl.put.poznan.transformer.logic.Filter;
import pl.put.poznan.transformer.logic.FilterOnly;

@RestController
@RequestMapping("/transformer")
public class TransformerController {

    private final JsonComparator jsonComparator;
    private final Filter filter;
    private final FilterOnly filterOnly;

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
}
