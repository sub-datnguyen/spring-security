package vn.elca.codebase.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ElcaDateUtil {
    public static final String JS_SERIALIZE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String JS_DESERIALIZE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    
    public static LocalDateTime stringToLocalDateTimeWithFormat(String localDateTime, String format) {
        return StringUtils.isBlank(localDateTime) ? null
                : LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(format));
    }
    
    public static LocalDate stringToLocalDateWithFormat(String date, String format) {
        return StringUtils.isBlank(date) ? null
                : LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }
    
    public static LocalDate stringToLocalDateWithISOPattern(String date) {
        return StringUtils.isBlank(date) ? null
                : LocalDate.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    
    public static String localDateToStringWithFormat(LocalDate localDate, String format) {
        return localDate == null ? null : DateTimeFormatter.ofPattern(format).format(localDate);
    }
    
    public static String localDateTimeToStringWithFormat(LocalDateTime localDate, String format) {
        return localDate == null ? null : DateTimeFormatter.ofPattern(format).format(localDate);
    }
}
