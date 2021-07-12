package vn.elca.codebase.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vn.elca.codebase.util.ElcaDateUtil;

import java.io.IOException;
import java.time.LocalDate;

@SuppressWarnings("rawtypes")
public class ElcaCustomLocalDateSerializer extends JsonSerializer<LocalDate> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(ElcaDateUtil.localDateToStringWithFormat(value, ElcaDateUtil.DATE_FORMAT));
    }
}
