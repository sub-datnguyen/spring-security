package ch.elca.boostup;

import ch.elca.boostup.entity.ProjectEntity;
import ch.elca.boostup.exception.ElcaBusinessException;
import ch.elca.boostup.repository.ProjectRepository;
import ch.elca.boostup.services.ProjectService;
import ch.elca.boostup.services.TaskService;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class HibernateIntecepterTest extends HibernateApplicationTests {

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * [Exercise] Implement Hibernate interceptor to set value for audit field automatically when insert/update entity.
     */
    @Test
    void testInterceptor() {
        var project = initProject("ELCA PIMTOOL");
        var updatedProject = projectRepository.save(project);
        Assertions.assertNotNull(project.getIntertedDate());
        Assertions.assertNotNull(project.getIntertedUser());
        Assertions.assertNotNull(project.getUpdatedDate());
        Assertions.assertNotNull(project.getUpdatedUser());
    }

    private ProjectEntity initProject(String name) {
        var project = new ProjectEntity();
        project.setVersion(1);
        project.setName(name);
        project.setFinishingDate(LocalDate.now().plusMonths(12));
        return project;
    }

    /**
     *
     * [Exercise] Implement Hibernate Listener to turn on updated flag automatically when insert/update entity.
     */
    @Test
    void testListener() {
        var project = initProject("ELCA TRAINING");
        var updatedProject = projectRepository.save(project);
        // TODO [Exercise] After create hibernate listener to update updated field, adapt test to make it pass (don't set updated flag manually)
        Assertions.assertTrue(updatedProject.isUpdated());
    }
}
