package vn.elca.codebase.business.demo;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.codebase.common.AbstractElcaServiceEndpoint;
import java.util.List;

@RestController
@RequestMapping(AbstractElcaServiceEndpoint.COMMON_PATH)
@Slf4j
@RequiredArgsConstructor
public class HelloWorldServiceEndpoint extends AbstractElcaServiceEndpoint {
    @GetMapping("customers")
    @ResponseBody
    public List<Object> helloWorld() {
        // TODO: create a Spring feign client to call api from server and return the response here
        return Lists.newArrayList();
    }

    @GetMapping("customers/{id}")
    @ResponseBody
    public Object helloWorld(@PathVariable Long id) {
        // TODO: create a Spring feign client to call api from server and return the response here
        return null;
    }
}
