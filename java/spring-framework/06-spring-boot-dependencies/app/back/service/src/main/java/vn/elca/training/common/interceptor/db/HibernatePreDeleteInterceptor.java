package vn.elca.training.common.interceptor.db;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.springframework.stereotype.Component;

/**
 * @author vlp
 */
@Component
@Slf4j
public class HibernatePreDeleteInterceptor extends AbstractHibernateAuditInterceptor<PreDeleteEventListener>
        implements PreDeleteEventListener {
    
    @Override
    public boolean onPreDelete(PreDeleteEvent preDeleteEvent) {
        logAuditInfo(preDeleteEvent, log);
        return false;
    }
    
    @Override
    protected String getEventLogName() {
        return "DELETE";
    }
    
    @Override
    protected EventType<PreDeleteEventListener> getSupportedEventType() {
        return EventType.PRE_DELETE;
    }
}
