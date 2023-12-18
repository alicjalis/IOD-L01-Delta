package pl.put.poznan.transformer.logic;


public class BasicJsonTransformer implements JSONTransformer{
    @Override
    public String decorate(String JsonString) {
        return JsonString;
    }
}
