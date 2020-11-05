package vn.elca.codebase.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.util.Collection;

public class ElcaEnumDeserializer extends JsonDeserializer<Enum<?>> implements ContextualDeserializer {
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        return new JsonDeserializer<Enum<?>>() {
            
            @Override
            public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException,
                    JsonProcessingException {
                Integer ordinal = p.readValueAs(Integer.class);
                if (ordinal == null) {
                    return null;
                }
                return (Enum<?>) getPropertyType(property).getEnumConstants()[ordinal];
            }
        };
    }
    
    @Override
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) {
        throw new IllegalStateException("Contextual deserializer should be used");
    }
    
    private Class<?> getPropertyType(BeanProperty property) {
        Class<?> clazz = property.getType().getRawClass();
        if (Collection.class.isAssignableFrom(clazz)) {
            return property.getType().getContentType().getRawClass();
        }
        return (clazz.isArray()) ? clazz.getComponentType() : clazz;
    }
}
