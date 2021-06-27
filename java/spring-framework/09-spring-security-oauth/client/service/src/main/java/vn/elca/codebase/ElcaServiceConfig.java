package vn.elca.codebase;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = ElcaServiceConfig.class)
@EnableAutoConfiguration
public class ElcaServiceConfig {
}
