package vn.elca.codebase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import vn.elca.codebase.common.constants.SpringProfile;

@Profile({SpringProfile.VN_DEV})
@SpringBootApplication
@Slf4j
public class CodeBaseDevModeWebApplication {
    public static void main(String[] args) {
        log.warn("Application is running in Development mode which should be used by developers only!");
        SpringApplication.run(ElcaWebConfig.class, args);
    }
}
