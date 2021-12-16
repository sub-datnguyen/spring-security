package vn.elca.training.common.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import vn.elca.training.ServicesConfig;

import java.util.Locale;

/**
 * Manage the getting message from resources which is configured in {@link ServicesConfig}
 *
 */
@Service
public class MessageManager implements MessageSourceAware {
    
    private MessageSource messageSource;
    
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public String getMessage(String key, Object... params) {
        return messageSource.getMessage(key, params, Locale.FRENCH);
    }
}
