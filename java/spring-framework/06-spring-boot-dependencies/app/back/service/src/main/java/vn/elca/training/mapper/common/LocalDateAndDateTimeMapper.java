package vn.elca.training.mapper.common;

import vn.elca.training.util.DateUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateAndDateTimeMapper {
    
    public LocalDateTime dateAndTimeToLocalDateTime(String dateAndTime) {
        return DateUtil.dateAndTimeToLocalDateTime(dateAndTime);
    }
}
