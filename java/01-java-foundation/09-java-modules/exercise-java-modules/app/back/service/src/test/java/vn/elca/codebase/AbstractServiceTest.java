package vn.elca.codebase;

import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import vn.elca.codebase.security.SecurityUtil;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
public abstract class AbstractServiceTest implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    protected void setLoginUser(String username) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                SecurityUtil.DUMMY_USER_NAME, SecurityUtil.DUMMY_USER_NAME,
                List.of(new SimpleGrantedAuthority("DUMMY_ROLE"))));
    }
}
