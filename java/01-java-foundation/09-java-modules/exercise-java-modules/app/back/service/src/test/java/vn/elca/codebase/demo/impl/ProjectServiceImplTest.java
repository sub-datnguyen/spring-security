package vn.elca.codebase.demo.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.codebase.AbstractServiceTest;
import vn.elca.codebase.demo.ProjectService;

public class ProjectServiceImplTest extends AbstractServiceTest {
    @Autowired
    private ProjectService projectService;
    
    @Test
    public void testDemoLoadProjects() {
        projectService.demoCountProjects();
    }

}