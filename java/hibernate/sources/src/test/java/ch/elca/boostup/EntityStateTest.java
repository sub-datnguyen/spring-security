package ch.elca.boostup;

import ch.elca.boostup.entity.ProjectEntity;
import ch.elca.boostup.exception.ElcaBusinessException;
import ch.elca.boostup.services.ProjectService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class EntityStateTest extends HibernateApplicationTests {

    @Autowired
    private ProjectService projectService;

    @Test
    void testEntityState1() throws ElcaBusinessException {
        String projectName = "Boostup program";
        var project = projectService.getProjectByName(projectName)
            .orElseThrow(() -> new ElcaBusinessException("Project " + projectName + " cannot be found"));
        jpaUtil.detach(project);
        var query = jpaUtil.getEntityManager().createNativeQuery("delete from task where name = :name");
        query.setParameter("name", "Setup code base");
        query.executeUpdate();
        jpaUtil.flush();
        // TODO [Exercise]: Explain result
        Assertions.assertThrows(EntityNotFoundException.class, () -> jpaUtil.refresh(project), "Explain why throw EntityNotFoundException");
    }

    @Test
    void testEntityState2() throws ElcaBusinessException {
        String projectName = "Boostup program";
        var project = projectService.getProjectByName(projectName)
            .orElseThrow(() -> new ElcaBusinessException("Project " + projectName + " cannot be found"));
        Assertions.assertEquals(1, project.getTasks().size(), "This state: project has 1 task");
        var query = jpaUtil.getEntityManager().createNativeQuery("delete from task where name = :name");
        query.setParameter("name", "Setup code base");
        query.executeUpdate();
        jpaUtil.flush();
        // TODO [Exercise]: Explain result
        Assertions.assertEquals(1, projectService.getProjectByName(projectName)
                .orElseThrow(() -> new ElcaBusinessException("Project " + projectName + " cannot be found")).getTasks()
                .size(),
            "This state: project has 1 task -> explain why");
    }

    @Test
    void testEntityState3() throws ElcaBusinessException {
        String projectName = "Boostup program";
        var project = projectService.getProjectByName(projectName)
            .orElseThrow(() -> new ElcaBusinessException("Project " + projectName + " cannot be found"));
        jpaUtil.detach(project);
        Assertions.assertEquals(1, project.getTasks().size(), "This state: project has 1 task");
        var query = jpaUtil.getEntityManager().createNativeQuery("delete from task where name = :name");
        query.setParameter("name", "Setup code base");
        query.executeUpdate();
        jpaUtil.flush();
        // TODO [Exercise]: Explain result
        Assertions.assertEquals(0, projectService.getProjectByName(projectName)
                .orElseThrow(() -> new ElcaBusinessException("Project " + projectName + " cannot be found")).getTasks()
                .size(),
            "This state: project has 0 task -> explain why");
    }


    @Test
    void testEntityState4() throws ElcaBusinessException {
        String projectName = "Boostup program";
        var project = projectService.getProjectByName(projectName)
            .orElseThrow(() -> new ElcaBusinessException("Project " + projectName + " cannot be found"));
        jpaUtil.detach(project);
        project.setName("Boostup program v2");
        projectService.getProjectByName(projectName);
        // TODO [Exercise]: Explain result
        Assertions.assertThrows(NonUniqueObjectException.class, () -> jpaUtil.getEntityManager().unwrap(Session.class).update(project),
            "Explain why throw NonUniqueObjectException");
    }

    @Test
    void testEntityState5() throws ElcaBusinessException {
        String projectName = "Boostup program";
        var project = projectService.getProjectByName(projectName)
            .orElseThrow(() -> new ElcaBusinessException("Project " + projectName + " cannot be found"));
        jpaUtil.detach(project);
        project.setName("Boostup program v2");
        project.setId(0L);
        projectService.save(project);
        projectService.save(project);
        jpaUtil.flush();

        // TODO [Exercise]: Explain result
        List<ProjectEntity> projectEntities = projectService.findAll();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(projectEntities),
            "All projects");
        // TODO [Exercise]: please correct the expected number and explain this result
        Assertions.assertEquals(0, projectEntities.size(),
            "How many projects in DB? Explain result");
    }
}
