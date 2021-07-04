package vn.elca.codebase.common;

import org.slf4j.MDC;

public final class MDCUtil {
    /**
     * Set the default context params for Logging. Normally we pass the correlationID here. The method is designed in
     * the way that in case you already define the correlationID for the current thread we won't re-add a new one anymore.
     * In case there is no correlationID available then the input param will be used.
     *
     * @param correlationIdToUseIfNotAvailable
     */
    public static void setDefaultContext(String correlationIdToUseIfNotAvailable) {
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
