package vn.elca.codebase;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Test config which override the {@link ElcaServiceConfig} so that you can mock spring beans as your choice.
 */
@Configuration
@PropertySource(value = {"classpath:application-unittest.properties"})
@ComponentScan(basePackageClasses = ServiceTestConfig.class)
public class ServiceTestConfig extends ElcaServiceConfig {
}
