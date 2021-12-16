package vn.elca.training.common.interceptor.db;

import vn.elca.training.entity.AbstractAuditEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;
import vn.elca.training.entity.QAbstractAuditEntity;

import java.time.LocalDateTime;

/**
 * @author vlp
 */
@Component
@Slf4j
public class HibernatePreUpdateInterceptor extends AbstractHibernateAuditInterceptor<PreUpdateEventListener>
        implements PreUpdateEventListener {
    private static final long serialVersionUID = 5997319463016866347L;
    
    @Override
    protected String getEventLogName() {
        return "UPDATE";
    }
    
    protected EventType<PreUpdateEventListener> getSupportedEventType() {
        return EventType.PRE_UPDATE;
    }
    
    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        if (event.getEntity() instanceof AbstractAuditEntity) {
            logAuditInfo(event, log);
            
            AbstractAuditEntity auditEntity = (AbstractAuditEntity) event.getEntity();
            LocalDateTime now = LocalDateTime.now();
            auditEntity.setUpdatedDate(now);
            setValue(event.getState(), event.getPersister().getPropertyNames(),
                    QAbstractAuditEntity.abstractAuditEntity.updatedUser.getMetadata().getName(),
                    getCurrentUsername());
            setValue(event.getState(), event.getPersister().getPropertyNames(),
                    QAbstractAuditEntity.abstractAuditEntity.updatedDate.getMetadata().getName(), now);
        }
        return false;
    }
    

}
