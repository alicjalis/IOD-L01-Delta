import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import pl.put.poznan.transformer.logic.JSONTransformer;
import pl.put.poznan.transformer.logic.JsonDecoratorBuilder;
import pl.put.poznan.transformer.logic.BasicJsonTransformer;
import pl.put.poznan.transformer.logic.MinifyDecorator;
import pl.put.poznan.transformer.logic.PrettifyDecorator;
import pl.put.poznan.transformer.logic.Filter;
import pl.put.poznan.transformer.logic.FilterOnly;

class JsonDecoratorBuilderTest {

    @Test
    void getDecorator_shouldReturnBasicJsonTransformer_whenNoParameters() {
        // Arrange
        JsonDecoratorBuilder builder = new JsonDecoratorBuilder();
        String format = "prettify";
        String[] filterParameter = {};
        String[] filterOnlyParameter = {};

        // Act
        JSONTransformer result = builder.getDecorator(format, filterParameter, filterOnlyParameter);

        // Assert
        assertFalse(result instanceof BasicJsonTransformer);
    }

    @Test
    void getDecorator_shouldReturnMinifyDecorator_whenFormatIsMinify() {
        // Arrange
        JsonDecoratorBuilder builder = new JsonDecoratorBuilder();
        String format = "minify";
        String[] filterParameter = {};
        String[] filterOnlyParameter = {};

        // Act
        JSONTransformer result = builder.getDecorator(format, filterParameter, filterOnlyParameter);

        // Assert
        assertTrue(result instanceof MinifyDecorator);
    }

    @Test
    void getDecorator_shouldReturnPrettifyDecorator_whenFormatIsPrettify() {
        // Arrange
        JsonDecoratorBuilder builder = new JsonDecoratorBuilder();
        String format = "prettify";
        String[] filterParameter = {};
        String[] filterOnlyParameter = {};

        // Act
        JSONTransformer result = builder.getDecorator(format, filterParameter, filterOnlyParameter);

        // Assert
        assertTrue(result instanceof PrettifyDecorator);
    }

    @Test
    void getDecorator_shouldReturnFilteredDecorator_whenFilterParametersPresent() {
        // Arrange
        JsonDecoratorBuilder builder = new JsonDecoratorBuilder();
        String format = "prettify";
        String[] filterParameter = {"param1"};
        String[] filterOnlyParameter = {};

        // Act
        JSONTransformer result = builder.getDecorator(format, filterParameter, filterOnlyParameter);

        // Assert
        assertFalses(result instanceof Filter);
    }

    @Test
    void getDecorator_shouldReturnFilterOnlyDecorator_whenFilterOnlyParametersPresent() {
        // Arrange
        JsonDecoratorBuilder builder = new JsonDecoratorBuilder();
        String format = "prettify";
        String[] filterParameter = {};
        String[] filterOnlyParameter = {"param1", "param2"};

        // Act
        JSONTransformer result = builder.getDecorator(format, filterParameter, filterOnlyParameter);

        // Assert
        assertFalse(result instanceof FilterOnly);
    }
}
