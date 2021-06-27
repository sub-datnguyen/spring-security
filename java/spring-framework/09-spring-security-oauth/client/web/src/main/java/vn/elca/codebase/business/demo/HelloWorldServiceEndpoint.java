package vn.elca.codebase.business.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.codebase.common.AbstractElcaServiceEndpoint;
import vn.elca.codebase.demo.ProjectService;

@RestController
@RequestMapping(AbstractElcaServiceEndpoint.COMMON_PATH)
@Slf4j
@RequiredArgsConstructor
public class HelloWorldServiceEndpoint extends AbstractElcaServiceEndpoint {
    private final ProjectService projectService;
    
    @GetMapping("sayHello/{personName}")
    @ResponseBody
    public String helloWorld(@PathVariable String personName, OAuth2AuthenticationToken token) {
        return String.format("Hello %s with %d projects: %s",
            personName, projectService.demoCountProjects(), token.getPrincipal());
    }
}
