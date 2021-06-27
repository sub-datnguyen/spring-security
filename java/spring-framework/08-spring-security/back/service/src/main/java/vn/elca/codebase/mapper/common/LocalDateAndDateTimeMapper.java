package vn.elca.codebase.mapper.common;

import org.springframework.stereotype.Component;
import vn.elca.codebase.util.ElcaDateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class LocalDateAndDateTimeMapper {
    
    public LocalDateTime stringToLocalDateTime(String localDateTime) {
        return ElcaDateUtil.stringToLocalDateTimeWithFormat(localDateTime, ElcaDateUtil.DATE_TIME_FORMAT);
    }
    
    public String convertDateTimeToString(LocalDateTime dateTime) {
        return ElcaDateUtil.localDateTimeToStringWithFormat(dateTime, ElcaDateUtil.DATE_TIME_FORMAT);
    }
    
    public LocalDate stringToLocalDate(String date) {
        return ElcaDateUtil.stringToLocalDateWithFormat(date, ElcaDateUtil.DATE_FORMAT);
    }
}
