package vn.elca.codebase.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Model of a server error to be sent to client in case error happens in the backend.
 * It contains http status and error detail.
 */
@Getter
@AllArgsConstructor
public class TechnicalErrorResponse implements ServerErrorResponse {
    private int httpStatus;
    private String correlationId;
    private String message;
    
    @Override
    public ServerErrorType getErrorType() {
        return ServerErrorType.TECHNICAL;
    }
}
