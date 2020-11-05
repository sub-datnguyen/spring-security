package vn.elca.codebase.common;

import java.util.Set;

public class AbstractElcaServiceEndpoint {
    public static final String WEBAPP_SERVICE_PATH = "service";
    /** project interface path (used for interfaces which may not be triggered from web GUI) */
    private static final String INTERFACE_PATH = "interface";
    
    public static final Set<String> WEBAPP_ACCESSIBLE_PATHS = Set.of(WEBAPP_SERVICE_PATH);
    
    // path for service endpoints for GUI
    public static final String COMMON_PATH = WEBAPP_SERVICE_PATH + "/common";
    
    public static final Set<String> CSRF_IGNORE_PATH = Set.of("/" + INTERFACE_PATH + "/**");
}
