package vn.elca.codebase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.elca.codebase.common.AbstractElcaServiceEndpoint;
import vn.elca.codebase.common.MDCContext;
import vn.elca.codebase.common.exceptions.ElcaRTException;
import vn.elca.codebase.common.jackson.WebCustomObjectMapper;
import vn.elca.codebase.security.SecurityUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackageClasses = ElcaWebConfig.class)
@Import(ElcaServiceConfig.class)
@Slf4j
public class ElcaWebConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new WebCustomObjectMapper();
    }
    
    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> logbackUserContextFilter() {
        FilterRegistrationBean<OncePerRequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                try {
                    String username;
                    try {
                        username = SecurityUtil.currentUsername();
                    } catch (ElcaRTException ex) {
                        username = "anonymous";
                    }
                    MDC.put(MDCContext.USERNAME, username);
                    filterChain.doFilter(request, response);
                } finally {
                    MDC.remove(MDCContext.USERNAME);
                }
            }
        });
        
        String[] logbackPath = getLogbackPath();
        log.info("Apply logback log username pattern on ({}) requests.", StringUtils.join(logbackPath, ","));
        registration.addUrlPatterns(logbackPath);
        registration.setName("logbackUserContextFilter");
        // This should be placed after the security filter chain (which configured to 0 - see application.properties)
        // from Spring Security so that the security context and inherently, the user name is already made to be ready
        // in advanced for the logging to work.
        registration.setOrder(100);
        return registration;
    }
    
    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> logbackCommonContextFilter() {
        FilterRegistrationBean<OncePerRequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                try {
                    MDC.put(MDCContext.CORRELATION_ID, UUID.randomUUID().toString());
                    filterChain.doFilter(request, response);
                } finally {
                    MDC.remove(MDCContext.CORRELATION_ID);
                }
            }
        });
        log.info("Apply logback log correlation Id and IP address patterns on all requests.");
        registration.addUrlPatterns("/*");
        registration.setName("logbackCommonContextFilter");
        // This should be placed before the security filter chain (which configured to 0 - see application.properties)
        // from Spring Security so that the security context and inherently, the user name is already made to be ready
        // in advanced for the logging to work.
        registration.setOrder(-1);
        return registration;
    }
    
    private String[] getLogbackPath() {
        return AbstractElcaServiceEndpoint.WEBAPP_ACCESSIBLE_PATHS.stream()
                .map(p -> "/" + p + "/*")
                .collect(Collectors.toList()).toArray(new String[]{});
    }
}
