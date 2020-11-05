package vn.elca.codebase.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.elca.codebase.common.exceptions.ElcaBusinessErrorParam;

import java.util.List;

/**
 * Model of a business error body to be sent to client in case business error happens in the backend.
 */
@Getter
@AllArgsConstructor
public class BusinessErrorResponse implements ServerErrorResponse {
    private String[] controlNames;
    private String keyCode;
    private List<ElcaBusinessErrorParam> params;
    private Object customErrorObject;
    private boolean reloadOnClose;
    
    @Override
    public ServerErrorType getErrorType() {
        return ServerErrorType.BUSINESS;
    }
}
