package vn.elca.training;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author vlp
 */
@Configuration
@Slf4j
public class EmbeddedTomcatWebConfig {
    
    @Value("${spring.datasource.jndi-name:jdbc/TrainingDatasource}")
    private String jndiName;
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    public EmbeddedTomcatWebConfig() {
        log.warn("WARNING!!! Application is running with embedded Tomcat. This mode should be used for development only!");
    }
    
    @Bean
    public TomcatServletWebServerFactory embeddedTomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }
    
            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setType(javax.sql.XADataSource.class.getName());
                resource.setAuth("Container");
                resource.setName(jndiName);
                resource.setProperty("factory", org.apache.tomcat.jdbc.pool.DataSourceFactory.class.getName());
                resource.setProperty("driverClassName", driverClassName);
                resource.setProperty("url", url);
                resource.setProperty("password", username);
                resource.setProperty("username", password);
                resource.setProperty("maxTotal", "100");
                resource.setProperty("maxIdle", "30");
                resource.setProperty("maxWaitMillis", "600000");
                context.getNamingResources().addResource(resource);
            }
        };
    }
}
