package vn.elca.training.common.jms;

import vn.elca.training.common.MDCContext;
import vn.elca.training.common.MDCUtil;
import ch.vd.technical.esb.EsbMessage;
import ch.vd.technical.esb.jms.AbstractEsbJmsTemplate;
import ch.vd.technical.esb.jms.EsbMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Common abstract class for all JMS listeners for ActiveMQ.
 *
 * @author vlp
 */
@Slf4j
public abstract class AbstractJmsQueueNotificationListener extends EsbMessageListener {
    
    protected AbstractEsbJmsTemplate esbJmsTemplate;
    
    @Value("${spring.activemq.user}")
    private String jmsUsername;
    
    @Autowired
    public AbstractJmsQueueNotificationListener(AbstractEsbJmsTemplate esbJmsTemplate) {
        super(esbJmsTemplate);
        this.esbJmsTemplate = esbJmsTemplate;
    }
    
    @Override
    public void onEsbMessage(EsbMessage esbMessage) {
        initializeLogcontextParams(esbMessage);
        //initializeUserContext();
        onStartProcessing(esbMessage);
        onHandleMessage(esbMessage);
        onFinishProcessing(esbMessage);
        clearLogContextParams();
    }
    
    private void initializeLogcontextParams(EsbMessage esbMessage) {
        MDCUtil.setJmsContext(getInterfaceLogName(), esbMessage.getBusinessId(), esbMessage.getBusinessCorrelationId());
    }
    
    protected abstract String getInterfaceLogName();
    
    private void clearLogContextParams() {
        MDCUtil.clearContext(MDCContext.CORRELATION_ID, MDCContext.INTERFACE_NAME, MDCContext.BUSINESS_ID);
    }
    
    protected abstract void onHandleMessage(EsbMessage esbMessage);
    
    protected abstract void onStartProcessing(EsbMessage esbMessage);
    
    protected abstract void onFinishProcessing(EsbMessage esbMessage);
}
