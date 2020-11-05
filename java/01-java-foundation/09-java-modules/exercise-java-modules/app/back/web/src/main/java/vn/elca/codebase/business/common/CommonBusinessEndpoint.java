package vn.elca.codebase.business.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.codebase.MVCConfigForRestWebApp;
import vn.elca.codebase.common.AbstractElcaServiceEndpoint;
import vn.elca.codebase.common.dto.ApplicationContextDto;
import vn.elca.codebase.security.SecurityUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

/**
 * Endpoint to retrieve common info for project: version, base-href path, build number,...
 */
@RestController
@RequestMapping(AbstractElcaServiceEndpoint.COMMON_PATH)
@Slf4j
public class CommonBusinessEndpoint extends AbstractElcaServiceEndpoint {
    private static final String APPLICATION_CONTEXT_LOAD_URL = "application-context";
    
    @GetMapping(APPLICATION_CONTEXT_LOAD_URL)
    public ApplicationContextDto getApplicationContextData() {
        ApplicationContextDto applicationContextDto = new ApplicationContextDto();
        applicationContextDto.setLoginUser(SecurityUtil.currentUsername());
        applicationContextDto.setBaseHref(MVCConfigForRestWebApp.getBaseHref());
        applicationContextDto.setVersion(getAppVersion());
        return applicationContextDto;
    }
    
    private String getAppVersion() {
        String appVersion = "<development-local>";
        try (InputStream manifestIS = MVCConfigForRestWebApp.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF")) {
            if (manifestIS != null) {
                Manifest mf = new Manifest();
                mf.read(manifestIS);
                appVersion = String.format("%s - build %s at %s",
                        mf.getMainAttributes().getValue("Implementation-Version"),
                        mf.getMainAttributes().getValue("Implementation-Build"),
                        mf.getMainAttributes().getValue("Implementation-Build-Time"));
            }
        } catch (IOException e) {
            log.warn("Can't extract version info of application: {}", e.getMessage(), e);
            appVersion = "<unidentified app info>";
        }
        return appVersion;
    }
    
}
