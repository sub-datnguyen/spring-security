package vn.elca.codebase.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import vn.elca.codebase.AbstractServiceTest;
import vn.elca.codebase.RestITTestConfig;

import java.nio.charset.Charset;

@Profile("unittest")
@SpringBootTest(classes = {RestITTestConfig.class})
public abstract class AbstractRestITTest extends AbstractServiceTest {
    protected MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    @Qualifier("objectMapperTest")
    private ObjectMapper objectMapper;
    
    @Before
    public void onBeforeTest() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    protected <T> T mapFromJson(String json, Class<T> clazz, boolean ensureNotNull) {
        T result = null;
        try {
            result = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            System.out.println("Error while parsing the JSON from string: " + e.getMessage());
            result = null;
        }
        if (ensureNotNull) {
            Assert.assertNotNull(result);
        }
        return result;
    }
    
    protected String sendGetRequest(String uri, int expectedHttpStatus) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assert.assertEquals(expectedHttpStatus, result.getResponse().getStatus());
        return result.getResponse().getContentAsString();
    }
    
    protected String sendPostRequest(String uri, String requestContent, int expectedHttpStatus) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestContent)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assert.assertEquals(expectedHttpStatus, result.getResponse().getStatus());
        return result.getResponse().getContentAsString();
    }
    
    protected String loadPostRequestContent(String requestContentFilePath) {
        try {
            return IOUtils.resourceToString(requestContentFilePath, Charset.forName("UTF-8"));
        } catch (Exception e) {
            System.out.println("Can't load resource content from path " + requestContentFilePath);
            return StringUtils.EMPTY;
        }
    }
}
