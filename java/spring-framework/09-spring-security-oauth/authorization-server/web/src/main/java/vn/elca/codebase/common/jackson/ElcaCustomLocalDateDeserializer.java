package vn.elca.codebase.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import lombok.extern.slf4j.Slf4j;
import vn.elca.codebase.util.ElcaDateUtil;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * @author tcb
 */
@SuppressWarnings("serial") // we don't serialise json *mappers*
@Slf4j
public class ElcaCustomLocalDateDeserializer extends StdScalarDeserializer<LocalDate> {
    private static final Pattern LOCAL_DATE_PATTERN = Pattern.compile("^([0-9]{2}).([0-9]{2}).([0-9]{4})$");
    
    protected ElcaCustomLocalDateDeserializer() {
        super(LocalDate.class);
    }
    
    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return Instant.ofEpochMilli(jp.getLongValue()).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (t == JsonToken.VALUE_NULL) {
            return getNullValue(ctxt);
        }
        if (t == JsonToken.VALUE_STRING) {
            try {
                String str = jp.getText().trim();
                if (str.length() == 0) {
                    return getNullValue(ctxt);
                }
                if (LOCAL_DATE_PATTERN.asPredicate().test(str)) {
                    return ElcaDateUtil.stringToLocalDateWithFormat(str, ElcaDateUtil.DATE_FORMAT);
                } else {
                    return ElcaDateUtil.stringToLocalDateWithISOPattern(str);
                }
            } catch (DateTimeParseException e) {
                log.warn("The date value is null due to parse date exception for value '{}'", jp.getText().trim(), e);
                return null;
            }
        }
        return (LocalDate) ctxt.handleUnexpectedToken(_valueClass, jp);
    }
}
