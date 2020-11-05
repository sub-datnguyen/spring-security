package vn.elca.codebase;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

@ComponentScan(basePackageClasses = {RestITTestConfig.class})
@Import(ServiceTestConfig.class)
@PropertySource("classpath:application-web-unittest.properties")
@WebAppConfiguration
public class RestITTestConfig {
    @Bean("objectMapperTest")
    public ObjectMapper objectMapper() {
        ObjectMapper result = new ObjectMapper();
        result.findAndRegisterModules();
        return result;
    }
}
