package vn.elca.training.common;

/**
 * Definition of what can be put into MDC so that later on, can be accessible in its logging context.
 *
 * @author vlp
 */
public interface MDCContext {
    String CORRELATION_ID = "CorrelationId";
    String INTERNET_IP = "IP-Address";
    String USERNAME = "Username";
    String INTERFACE_NAME = "InterfaceName";
    String BUSINESS_ID = "BusinessId";
    
    // JMS context fields declaration
    
}
