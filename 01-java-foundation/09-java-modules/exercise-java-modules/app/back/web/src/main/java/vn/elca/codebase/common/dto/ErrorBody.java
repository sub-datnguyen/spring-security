package vn.elca.codebase.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Model of a error body to be sent to client in case error happens in the backend. Should includes
 * at least a correlation ID and the relevant error message.
 */
@Getter
@AllArgsConstructor
public class ErrorBody implements Serializable {
    private String correlationId;
    private String message;
}
