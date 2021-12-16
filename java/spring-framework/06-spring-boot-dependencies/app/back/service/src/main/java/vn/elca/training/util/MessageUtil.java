package vn.elca.training.util;

import vn.elca.training.common.exceptions.ProjectJmsException;
import vn.elca.training.common.service.impl.MessageManager;
import vn.elca.training.enums.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
    public static MessageManager messageManager;
    
    @Autowired
    public void setMessageManager(MessageManager messageManager) {
        MessageUtil.messageManager = messageManager;
    }
    
    public static void handleJmsException(ErrorCodeEnum errorCode, String validationMessagesKey, Object... params) {
        throw new ProjectJmsException(errorCode.getCode(), messageManager.getMessage(validationMessagesKey, params));
    }
}
