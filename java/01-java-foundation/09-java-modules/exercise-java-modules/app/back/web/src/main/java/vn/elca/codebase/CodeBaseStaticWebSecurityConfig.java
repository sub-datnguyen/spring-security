package vn.elca.codebase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@Order(1)
@Slf4j
public class CodeBaseStaticWebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final RequestMatcher WEB_APP_STATIC_RESOURCES_REQUEST_MATCHER =
            new OrRequestMatcher(
                    new AntPathRequestMatcher("/index.jsp"), // this line is for the redirection from root context path
                    new AntPathRequestMatcher("/" + MVCConfigForRestWebApp.FRONT_END_SERVICE_PATH + "/*"),
                    new AntPathRequestMatcher("/" + MVCConfigForRestWebApp.FRONT_END_SERVICE_PATH + "/assets/**")
            );
    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // no auth
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().requestMatchers(WEB_APP_STATIC_RESOURCES_REQUEST_MATCHER)
                // no need for csrf on static resources
                .and().csrf().disable();
        log.debug("Completed init of {}", getClass().getName());
    }
}
