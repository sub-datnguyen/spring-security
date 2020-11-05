package vn.elca.codebase.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
public class CodeBaseDateUtil {
    public static final String JS_SERIALIZE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String JS_DESERIALIZE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final int DECIMAL_SCALE = 2;
    
    private CodeBaseDateUtil() { }
    
    /**
     * Parse a String with format "yyyy-MM-dd'T'HH:mm:ss" into LocalDateTime
     * @param dateAndTime like example "2007-12-03T10:15:30"
     * @return today if @param dateAndTime is blank, or else return LocalDateTime after parsing
     */
    public static LocalDateTime dateAndTimeToLocalDateTime(String dateAndTime) {
        if (StringUtils.isBlank(dateAndTime)) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(dateAndTime);
    }
    
    /**
     * Parse a java.util.Date into XMLGregorianCalendar
     */
    public static XMLGregorianCalendar convertFromDate(Date date) {
        XMLGregorianCalendar result = null;
        if (date != null) {
            try {
                GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
                gc.setTime(date);
                result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
                result.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            } catch (Exception e) {
                log.error("Error when create XMLGregorianCalendar", e);
            }
        }
        return result;
    }
    
    public static LocalDateTime stringToLocalDateTimeWithFormat(String localDateTime, String format) {
        return StringUtils.isBlank(localDateTime) ? null
                : LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(format));
    }
    
    public static LocalDate stringToLocalDateWithFormat(String date, String format) {
        return StringUtils.isBlank(date) ? null
                : LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }
    
    public static String localDateToStringWithFormat(LocalDate localDate, String format) {
        return localDate == null ? null : DateTimeFormatter.ofPattern(format).format(localDate);
    }
    
    public static boolean verifyDatePattern(String date, String format) {
        try {
            stringToLocalDateWithFormat(date, format);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}
