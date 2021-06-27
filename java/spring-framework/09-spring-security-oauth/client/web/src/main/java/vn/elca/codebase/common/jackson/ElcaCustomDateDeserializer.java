package vn.elca.codebase.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import vn.elca.codebase.util.ElcaDateUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@SuppressWarnings("serial") // we don't serialise json *mappers*
@Slf4j
public class ElcaCustomDateDeserializer extends StdScalarDeserializer<Date> {
    
    protected ElcaCustomDateDeserializer() {
        super(Date.class);
    }
    
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return new Date(jp.getLongValue());
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
                return FastDateFormat.getInstance(ElcaDateUtil.JS_DESERIALIZE_DATE_FORMAT).parse(str);
            } catch (ParseException e) {
                log.warn("The date value is null due to parse date exception", e);
                return null;
            }
        }
        return (Date) ctxt.handleUnexpectedToken(_valueClass, jp);
    }
}
