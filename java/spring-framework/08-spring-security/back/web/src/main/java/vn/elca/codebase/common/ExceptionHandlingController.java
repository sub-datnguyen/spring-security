package vn.elca.codebase.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.elca.codebase.common.dto.BusinessErrorResponse;
import vn.elca.codebase.common.dto.ErrorBody;
import vn.elca.codebase.common.dto.TechnicalErrorResponse;
import vn.elca.codebase.common.exceptions.ElcaBusinessException;
import vn.elca.codebase.common.exceptions.ElcaRTException;

import java.util.List;
import java.util.UUID;

/**
 * Centralized place for handling all generic and unexpected exceptions in project.
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlingController {
    
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthenticationException(Exception exception) {
        return log("Authentication error occurred.", exception);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(Exception exception) {
        return log("Access to forbidden data is not allowed.", exception);
    }

    @ResponseBody
    @ExceptionHandler(ElcaBusinessException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<BusinessErrorResponse> handleBusinessException(ElcaBusinessException exception) {
        return businessError(exception);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<TechnicalErrorResponse> handleUnexpectedException(Throwable exception) {
        return technicalError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
    }

    @ExceptionHandler(ElcaRTException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleRuntimeException(ElcaRTException exception) {
        if (exception.getCause() instanceof ElcaBusinessException) {
            return handleBusinessException((ElcaBusinessException) exception.getCause());
        } else {
            return handleUnexpectedException(exception);
        }
    }

    private ResponseEntity<TechnicalErrorResponse> technicalError(HttpStatus status, String message,
                                                                  Throwable exception) {
        log.error("Unhandled exception occurred", exception);
        return ResponseEntity.status(status).body(
                new TechnicalErrorResponse(status.value(), MDC.get(MDCContext.CORRELATION_ID), message));
    }

    private ResponseEntity<BusinessErrorResponse> businessError(ElcaBusinessException exception) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new BusinessErrorResponse(exception.getControlNames(),
                                          exception.getKeyCode(),
                                          List.of(exception.getParams()),
                                          exception.getCustomErrorObject(),
                                          false));
    }

    public static String serverErrorResponseFor(HttpStatus status, String error, ObjectMapper objectMapper) {
        try {
            ErrorBody errorBody = new ErrorBody(MDC.get(MDCContext.CORRELATION_ID), error);
            if (errorBody.getCorrelationId() == null) {
                errorBody = new ErrorBody(UUID.randomUUID().toString(), error);
            }
            return objectMapper.writeValueAsString(errorBody);
        } catch (JsonProcessingException e) {
            throw new ElcaRTException("An error occurred during serialization", e);
        }
    }

    private String log(String errorMessage, Exception exception) {
        log.error(errorMessage, exception);
        return errorMessage;
    }
}
