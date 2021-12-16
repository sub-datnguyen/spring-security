package vn.elca.training.common.interceptor.db;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;
import vn.elca.training.entity.AbstractAuditEntity;
import vn.elca.training.entity.QAbstractAuditEntity;

import java.time.LocalDateTime;

/**
 * @author vlp
 */
@Component
@Slf4j
public class HibernatePreInsertInterceptor extends AbstractHibernateAuditInterceptor<PreInsertEventListener>
        implements PreInsertEventListener {
    
    private static final long serialVersionUID = -7271903664013873323L;
    
    @Override
    protected String getEventLogName() {
        return "CREATE";
    }
    
    @Override
    protected EventType<PreInsertEventListener> getSupportedEventType() {
        return EventType.PRE_INSERT;
    }
    
    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        if (event.getEntity() instanceof AbstractAuditEntity) {
            logAuditInfo(event, log);
            AbstractAuditEntity auditEntity = (AbstractAuditEntity) event.getEntity();
            auditEntity.setIntertedUser(getCurrentUsername());
            auditEntity.setUpdatedUser(getCurrentUsername());
            LocalDateTime now = LocalDateTime.now();
            auditEntity.setIntertedDate(now);
            auditEntity.setUpdatedDate(now);
            setValue(event.getState(), event.getPersister().getPropertyNames(),
                    QAbstractAuditEntity.abstractAuditEntity.intertedUser.getMetadata().getName(),
                    getCurrentUsername());
            setValue(event.getState(), event.getPersister().getPropertyNames(),
                    QAbstractAuditEntity.abstractAuditEntity.intertedDate.getMetadata().getName(), now);
            setValue(event.getState(), event.getPersister().getPropertyNames(),
                    QAbstractAuditEntity.abstractAuditEntity.updatedUser.getMetadata().getName(),
                    getCurrentUsername());
            setValue(event.getState(), event.getPersister().getPropertyNames(),
                    QAbstractAuditEntity.abstractAuditEntity.updatedDate.getMetadata().getName(), now);
        }
        return false;
    }
}
