package vn.elca.training.common.exceptions;

/**
 * General runtime exception for all RTEs in project.
 *
 * @author vlp
 */
public class ProjectRuntimeException extends RuntimeException {
    
    public ProjectRuntimeException(String message, Throwable e) {
        super(message, e);
    }
    
    public ProjectRuntimeException(String message) {
        super(message);
    }
    
    public ProjectRuntimeException(Throwable e) {
        super(e);
    }

}
