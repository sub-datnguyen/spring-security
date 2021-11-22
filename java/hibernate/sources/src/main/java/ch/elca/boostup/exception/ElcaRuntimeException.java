package ch.elca.boostup.exception;

public class ElcaRuntimeException extends RuntimeException {

    public ElcaRuntimeException(String message) {
        super(message);
    }

    public ElcaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
