package pl.put.poznan.transformer.logic;



public class JsonDecoratorBuilder {

    public JSONTransformer getDecorator(String format, String[] filterParameter,String[] filterOnlyParameter) {
        JSONTransformer transformer = new JsonDecorator( new BasicJsonTransformer());

        if(filterParameter.length != 0) {
            transformer = new Filter(transformer,filterParameter);
        }

        if(filterOnlyParameter.length != 0) {
            transformer = new FilterOnly(transformer,filterOnlyParameter);
        }

        if(format.equals("minify")) {
            //transformer = new MinifyDecorator(transformer);
        } else if (format.equals("prettify")) {
            //transformer = new Prettify(transformer);
        }

        return transformer;
    }
}
