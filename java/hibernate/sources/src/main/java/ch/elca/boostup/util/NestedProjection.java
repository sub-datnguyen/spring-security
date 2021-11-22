package ch.elca.boostup.util;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.MappingProjection;
import com.querydsl.core.types.Path;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Nested projection support for QueryDSL.
 */
public class NestedProjection<T> extends MappingProjection<T> {
    private static final String DOT_PROPERTY_SPLIT_STR = ".";
    
    private final List<Path<?>> others = new LinkedList<>();
    private final List<Path<?>> roots = new LinkedList<>();
    
    private NestedProjection(Class<T> clazz, Path<?>... args) {
        super(clazz, args);
        for (Path<?> path : args) {
            if (path.getRoot().getType().equals(clazz)) {
                roots.add(path);
            } else {
                others.add(path);
            }
        }
    }
    
    /**
     * Create nested projection of the clazz parameter with arguments parameter.
     *
     * @param clazz Is the class
     * @param args  Is the path arguments.
     * @param <T>   expression type
     * @return nested projection
     */
    public static <T> NestedProjection<T> of(Class<T> clazz, Path<?>... args) {
        return new NestedProjection<>(clazz, args);
    }
    
    @Override
    protected T map(Tuple row) {
        try {
            T result = getType().getDeclaredConstructor().newInstance();
            SpelParserConfiguration config = new SpelParserConfiguration(true, true);
            SpelExpressionParser parser = new SpelExpressionParser(config);
            StandardEvaluationContext modelContext = new StandardEvaluationContext(result);
            for (Path<?> fieldPath : roots) {
                String string = fieldPath.toString();
                String propertyPath = StringUtils.join(ArrayUtils.remove(StringUtils.split(string, DOT_PROPERTY_SPLIT_STR), 0), ".");
                parser.parseExpression(propertyPath).setValue(modelContext, row.get(fieldPath));
            }
            for (Path<?> fieldPath : others) {
                parser.parseExpression(fieldPath.toString()).setValue(modelContext, row.get(fieldPath));
            }
            return result;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Unexpected error during map the tuple for NestedProjection", e);
        }
    }
}
