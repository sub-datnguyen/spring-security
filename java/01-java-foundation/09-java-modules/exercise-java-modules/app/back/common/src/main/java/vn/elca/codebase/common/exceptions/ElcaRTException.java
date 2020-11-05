package vn.elca.codebase.common.exceptions;

/**
 * Generic RTE for all RTEs in project.
 */
public class ElcaRTException extends RuntimeException {
    public ElcaRTException(String message, Throwable e) {
        super(message, e);
    }
    
    public ElcaRTException(String message) {
        super(message);
    }
    
    public ElcaRTException(Throwable e) {
        super(e);
    }
}
