package pl.put.poznan.transformer.logic;



/**
 * Klasa JsonDecoratorBuilder jest klasą do tworzenia obiektów implementujących interfejs JSONTransformer.
 * Buduje obiekt, dodając do niego różne dekoratory w zależności od podanych parametrów.
 */
public class JsonDecoratorBuilder {

    /**
     * Metoda tworząca i zwracająca obiekt implementujący interfejs JSONTransformer.
     * Na podstawie podanych parametrów dodaje do obiektu różne dekoratory.
     *
     * @param format Format, w którym ma być zwrócony ciąg JSON. Może to być "minify" lub "prettify".
     * @param filterParameter Tablica zawierająca parametry, które mają być filtrowane.
     * @param filterOnlyParameter Tablica zawierająca parametry, które mają być filtrowane NIE WIEM CO Z NIMI
     * @return Zwraca obiekt implementujący interfejs JSONTransformer.
     */
    public JSONTransformer getDecorator(String format, String[] filterParameter,String[] filterOnlyParameter) {
        JSONTransformer transformer = new JsonDecorator( new BasicJsonTransformer());

        if(filterParameter.length != 0) {
            transformer = new Filter(transformer,filterParameter);
        }

        if(filterOnlyParameter.length != 0) {
            transformer = new FilterOnly(transformer,filterOnlyParameter);
        }

        if(format.equals("minify")) {
            transformer = new MinifyDecorator(transformer);
        } else if (format.equals("prettify")) {
            transformer = new PrettifyDecorator(transformer);
        }

        return transformer;
    }
}

