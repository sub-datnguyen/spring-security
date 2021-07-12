package vn.elca.codebase.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CodeBaseDummyBasicAuthenticationFilter extends BasicAuthenticationFilter {
    
    public static final String DUMMY_ROLE = "DUMMY_ROLE";
    
    public CodeBaseDummyBasicAuthenticationFilter() {
        super(new DummyAuthenticationManager());
    }
    
    /**
     * Dummy authentication manager which accepts any username/password input from any request.
     */
    private static class DummyAuthenticationManager implements AuthenticationManager {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            if (!SecurityUtil.DUMMY_USER_NAME.equals(authentication.getName())) {
                throw new BadCredentialsException("Username must be " + SecurityUtil.DUMMY_USER_NAME);
            }
            return new UsernamePasswordAuthenticationToken(SecurityUtil.DUMMY_USER_NAME, SecurityUtil.DUMMY_USER_NAME,
                    List.of(new SimpleGrantedAuthority(DUMMY_ROLE)));
        }
    }
    
}
