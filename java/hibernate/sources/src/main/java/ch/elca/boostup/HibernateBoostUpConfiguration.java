package ch.elca.boostup;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = HibernateBoostUpConfiguration.class)
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class HibernateBoostUpConfiguration {
}
