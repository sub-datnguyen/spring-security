package vn.elca.codebase.exception;

/**
 * @author hmt
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User " + id + " is not found");
    }
}
