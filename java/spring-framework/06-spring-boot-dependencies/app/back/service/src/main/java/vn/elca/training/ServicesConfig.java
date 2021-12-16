package vn.elca.training;

import vn.elca.training.common.exceptions.ProjectRuntimeException;
import vn.elca.training.engine.ZgenDGMRvehicleReturn;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.xml.bind.JAXBContext;

@Configuration
@ComponentScan(basePackageClasses = ServicesConfig.class)
@EnableAutoConfiguration
@EnableCaching
@EnableAspectJAutoProxy
@PropertySource(value = {"classpath:serviceConfig/application.internal.properties"})
public class ServicesConfig {
    
    /**
     * To supports Bean Validation on service method level.
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
    
    @Bean
    public JAXBContext vehicleEngineJaxbContext() {
        try {
            return JAXBContext.newInstance(new Class[]{ZgenDGMRvehicleReturn.class});
        } catch (Exception e) {
            throw new ProjectRuntimeException(
                    "Cannot start application due to JAXBContext initialization error for Engin class.", e);
        }
    }
    
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:ValidationErrorMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
