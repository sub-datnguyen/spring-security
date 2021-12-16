package vn.elca.training.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * @author tcb
 */
public class DateUtil {
    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    private DateUtil() {
    }
    
    /**
     * Parse a String with format "yyyy-MM-dd'T'HH:mm:ss" into LocalDateTime
     * @param dateAndTime like example "2007-12-03T10:15:30"
     * @return
     */
    public static LocalDateTime dateAndTimeToLocalDateTime(String dateAndTime) {
        if (StringUtils.isBlank(dateAndTime)) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(dateAndTime);
    }
}
