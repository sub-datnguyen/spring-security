package vn.elca.training.common.exceptions;

import lombok.Getter;

@Getter
public class ProjectJmsException extends ProjectRuntimeException {
    private int errorCode;
    private String errorMessage;
    
    public ProjectJmsException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
