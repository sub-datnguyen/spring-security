package vn.elca.codebase.common.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import vn.elca.codebase.util.ElcaDateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class WebCustomObjectMapper extends ObjectMapper {
    public WebCustomObjectMapper() {
        setDateFormat(new SimpleDateFormat(ElcaDateUtil.JS_SERIALIZE_DATE_FORMAT));
        registerModule(new CustomModule());
    }
    
    /**
     * Customize module for project.
     */
    public static class CustomModule extends SimpleModule {
        public CustomModule() {
            super("ElcaCustomModule", Version.unknownVersion());
            addDeserializer(Date.class, new ElcaCustomDateDeserializer());
            addSerializer(LocalDate.class, new ElcaCustomLocalDateSerializer());
            addDeserializer(LocalDate.class, new ElcaCustomLocalDateDeserializer());
            addSerializer(Enum.class, new ElcaEnumSerializer());
            addDeserializer(Enum.class, new ElcaEnumDeserializer());
        }
    }
}
