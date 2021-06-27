package vn.elca.codebase;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.TransformedResource;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * Customization for REST based web apps (e.g Angular, React) which load the resource context base on <base href="xxx"> tag.
 * This class base on configuration that all the front end resources are put in front folder. Please adapt
 */
@Configuration
public class MVCConfigForRestWebApp implements WebMvcConfigurer {
    public static final String FRONT_END_SERVICE_PATH = "front";
    
    private static final String PATH_PATTERNS = "/**";
    private static final String FRONT_CONTROLLER_PAGE_PATH = "index.html";
    private static final String CONTEXT_PATH_PLACEHOLDER = "#context-path#";
    private static final String FRONT_CONTROLLER_ENCODING = "UTF-8";
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PATH_PATTERNS)
                .addResourceLocations("/")
                .resourceChain(false)
                .addResolver(new SinglePageAppResourceResolver());
    }
    
    private class SinglePageAppResourceResolver extends PathResourceResolver {
        private TransformedResource transformedResource(final Resource resource) throws IOException {
            String fileContent = IOUtils.toString(resource.getInputStream(), FRONT_CONTROLLER_ENCODING);
            if (fileContent.contains(CONTEXT_PATH_PLACEHOLDER)) {
                fileContent = fileContent.replace(CONTEXT_PATH_PLACEHOLDER, getBaseHref());
            } else {
                // This is prevent in case the CONTEXT_PATH_PLACEHOLDER is not filled when build with Angular.
                fileContent = fileContent.replace("<base href=\"/\">", "<base href=\"" + getBaseHref() + "\">");
            }
            
            return new TransformedResource(resource, fileContent.getBytes());
        }
        
        @Override
        protected Resource getResource(final String resourcePath, final Resource location) throws IOException {
            Resource resource = location.createRelative(resourcePath);
            if (resource.exists() && resource.isReadable()) {
                //if the asked resource is index.html, we serve it with the base-href rewritten
                if (resourcePath.contains(FRONT_CONTROLLER_PAGE_PATH)) {
                    return transformedResource(resource);
                }
                
                return resource;
            }
            
            // this is for the support when user just enters "/" in the URL
            if ((resourcePath).equals(FRONT_END_SERVICE_PATH)) {
                return transformedResource(location.createRelative("/" + FRONT_END_SERVICE_PATH  + "/" + FRONT_CONTROLLER_PAGE_PATH));
            }
            
            //we just refreshed a page, no ?
            resource = location.createRelative(FRONT_CONTROLLER_PAGE_PATH);
            if (resource.exists() && resource.isReadable()) {
                return transformedResource(resource);
            }
            
            return null;
        }
    }
    
    public static ServletContext getServletContext() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext();
    }
    
    public static String getBaseHref() {
        return getServletContext().getContextPath() + "/" + FRONT_END_SERVICE_PATH + "/";
    }

}
