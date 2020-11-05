package vn.elca.codebase.business.demo;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import vn.elca.codebase.business.AbstractRestITTest;

public class HelloWorldServiceEndpointTest extends AbstractRestITTest {
    
    @Test
    public void testSayHello() throws Exception {
        // prepare context
        setLoginUser("user1");
    
        // execute the test
        String actual = sendGetRequest("/service/common/sayHello/my-test", HttpStatus.OK.value());
        
        // verify result
        Assert.assertEquals("Hello my-test with 0 projects", actual);
    }
    
}
