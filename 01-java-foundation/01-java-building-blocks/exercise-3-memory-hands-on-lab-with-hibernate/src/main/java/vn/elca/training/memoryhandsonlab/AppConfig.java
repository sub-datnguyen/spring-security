package vn.elca.training.memoryhandsonlab;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackageClasses = AppConfig.class)
@EnableJpaRepositories
public class AppConfig {
}
