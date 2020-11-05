package vn.elca.codebase.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

@SuppressWarnings("rawtypes")
public class ElcaEnumSerializer extends JsonSerializer<Enum> {
    @Override
    public void serialize(Enum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.ordinal());
    }
}
