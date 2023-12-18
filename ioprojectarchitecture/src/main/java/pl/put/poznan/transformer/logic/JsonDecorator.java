package pl.put.poznan.transformer.logic;

public class JsonDecorator implements JSONTransformer {
    protected JSONTransformer transformer;

    public JsonDecorator(JSONTransformer t) {
        transformer = t;
    }

    @Override
    public String decorate(String JsonString) {
        return transformer.decorate(JsonString);
    }
}
