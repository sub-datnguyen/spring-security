package ch.elca.boostup;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@TestPropertySource(value = {"classpath:application-unittest.properties"})
@ComponentScan(basePackageClasses = HibernateBoostUpTestConfiguration.class)
public class HibernateBoostUpTestConfiguration extends HibernateBoostUpConfiguration{
}
