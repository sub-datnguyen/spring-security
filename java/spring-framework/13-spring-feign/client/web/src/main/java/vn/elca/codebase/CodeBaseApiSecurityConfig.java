package vn.elca.codebase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Slf4j
@Order(3)
@RequiredArgsConstructor
public class CodeBaseApiSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected final void configure(AuthenticationManagerBuilder auth) {
        // no authentication manager: already done by some filters
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        log.debug("Completed init of {}", getClass().getName());
    }
}
