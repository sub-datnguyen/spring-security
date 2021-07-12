package vn.elca.codebase.common;

/**
 * Definition of what can be put into MDC so that later on, can be accessible in its logging context.
 */
public interface MDCContext {
    String CORRELATION_ID = "CorrelationId";
    String USERNAME = "Username";
}
