package vn.elca.training.common.jms;

import ch.vd.technical.esb.jms.EsbMessageListener;
import ch.vd.technical.esb.jms.EsbMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;


/**
 * Configuration for ESB client.
 *
 * @author vlp
 */
@Configuration
public class EsbConfig {
    @Value("${jms.queue.engin}")
    private String esbQueueNotification;
    
    @Bean
    public DefaultMessageListenerContainer notificationListenerContainer(
            @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
            PlatformTransactionManager transactionManager,
            @Qualifier("enginQueueListener") EsbMessageListener notificationListener) {
        return buildMessageListenerContainer(connectionFactory, transactionManager, notificationListener,
                esbQueueNotification);
    }
    
    /**
     * common method to build a listener for a specific queue. Use it to declare other listeners for other queues.
     */
    private DefaultMessageListenerContainer buildMessageListenerContainer(ConnectionFactory connectionFactory,
                                                                          PlatformTransactionManager transactionManager,
                                                                          EsbMessageListener notificationListener,
                                                                          String listenedQueueName) {
        final EsbMessageListenerContainer container = new EsbMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setAutoStartup(true);
        container.setDestinationName(listenedQueueName);
        container.setMessageListener(notificationListener);
        container.setTransactionManager(transactionManager);
        container.setSessionTransacted(true);
        container.setCacheLevelName("CACHE_CONNECTION");
        container.afterPropertiesSet();
        return container;
    }
    
}
