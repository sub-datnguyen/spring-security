package vn.elca.training.common;

import org.slf4j.MDC;

/**
 * @author vlp
 */
public final class MDCUtil {
    public static void setJmsContext(String interfaceName, String businessId, String correlationIdToUseIfNotAvailable) {
        MDC.put(MDCContext.INTERFACE_NAME, String.format("[%s]", interfaceName));
        MDC.put(MDCContext.BUSINESS_ID, String.format("[businessId: %s]", businessId));
        if (MDC.get(MDCContext.CORRELATION_ID) == null) {
            MDC.put(MDCContext.CORRELATION_ID, correlationIdToUseIfNotAvailable);
        }
    }
    
    public static void clearContext(String... contextKeys) {
        for (String ctx : contextKeys) {
            MDC.remove(ctx);
        }
    }
}
