package vn.elca.training.common.interceptor.db;

import lombok.Getter;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.AbstractPreDatabaseOperationEvent;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;

/**
 * @author vlp
 */
public abstract class AbstractHibernateAuditInterceptor<T> implements InitializingBean {
    @Getter
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Override
    public void afterPropertiesSet() {
        SessionFactoryImpl sessionFactory = getEntityManagerFactory().unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(getSupportedEventType()).appendListener((T) this);
    }

    protected String getCurrentUsername() {
        return "dev";
    }
    
    protected void setValue(Object[] currentState, String[] propertyNames, String propertyToSet, Object value) {
        int index = Arrays.asList(propertyNames).indexOf(propertyToSet);
        if (index >= 0) {
            currentState[index] = value;
        }
    }
    
    protected void logAuditInfo(AbstractPreDatabaseOperationEvent event, Logger log) {
        log.info(String.format("[%s] %s %s", getEventLogName(), event.getEntity().getClass().getSimpleName(),
                event.getId()));
    }
    
    protected abstract String getEventLogName();
    
    protected abstract EventType<T> getSupportedEventType();
}
