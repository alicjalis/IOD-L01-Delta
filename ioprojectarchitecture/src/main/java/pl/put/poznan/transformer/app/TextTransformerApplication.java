package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.transformer.logic.JsonComparator;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class TextTransformerApplication {

    public static void main(String[] args) {

        SpringApplication.run(TextTransformerApplication.class, args);

        // compare jsons from path
        CompareJsons("ioprojectarchitecture/src/main/resources/changed.json", "ioprojectarchitecture/src/main/resources/full.json");
    }

    private static void CompareJsons(String jsonFilePath1, String jsonFilePath2){
        JsonComparator.compareAndDisplayDifferences(jsonFilePath1, jsonFilePath2);
    }
}
