package vn.elca.codebase.security;

/**
 * A light wrapper over spring-security API.
 */
public class SecurityUtil {
    public static final String DUMMY_USER_NAME = "code-base-mock-user";
    
    public static String currentUsername() {
        return DUMMY_USER_NAME;
    }
}
