package vn.elca.codebase.common.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

/**
 * Root for all business exceptions in project.
 */
@Getter @Setter
public class ElcaBusinessException  extends Exception {
    protected String keyCode;
    protected String[] controlNames = ArrayUtils.EMPTY_STRING_ARRAY;
    protected ElcaBusinessErrorParam[] params = new ElcaBusinessErrorParam[0];
    protected Object customErrorObject;
    protected boolean customHandle;
    
    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not initialized, and may 
     * subsequently be initialized by a call to {@link #initCause}.
     */
    protected ElcaBusinessException(String keyCode) {
        super();
        this.keyCode = keyCode;
    }
    
    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not initialized, and may 
     * subsequently be initialized by a call to {@link #initCause}.
     */
    protected ElcaBusinessException(String keyCode, List<ElcaBusinessErrorParam> params) {
        super();
        this.keyCode = keyCode;
        this.params = params.toArray(new ElcaBusinessErrorParam[]{});
    }
    
    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not initialized, and may 
     * subsequently be initialized by a call to {@link #initCause}.
     */
    protected ElcaBusinessException(String keyCode, String... controlNames) {
        super();
        this.keyCode = keyCode;
        this.controlNames = ArrayUtils.clone(controlNames);
    }
    
    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not initialized, and may 
     * subsequently be initialized by a call to {@link #initCause}.
     */
    protected ElcaBusinessException(String keyCode, List<ElcaBusinessErrorParam> params, String... controlNames) {
        super();
        this.keyCode = keyCode;
        this.params = params.toArray(new ElcaBusinessErrorParam[]{});
        this.controlNames = ArrayUtils.clone(controlNames);
    }
    
    protected ElcaBusinessException(String keyCode, boolean customHandle) {
        super();
        this.keyCode = keyCode;
        this.customHandle = customHandle;
    }
}
