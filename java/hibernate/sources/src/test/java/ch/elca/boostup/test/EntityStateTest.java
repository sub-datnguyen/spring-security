package ch.elca.boostup.test;

import ch.elca.boostup.HibernateApplicationTests;
import ch.elca.boostup.entity.ProjectEntity;
import ch.elca.boostup.exception.ElcaBusinessException;
import ch.elca.boostup.repository.TaskRepository;
import ch.elca.boostup.services.ProjectService;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Transactional
class EntityStateTest extends HibernateApplicationTests {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

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
        Assertions.assertEquals(3, projectEntities.size(),
            "Explain result");
    }

    @Test
    void testEntityState6() throws ExecutionException, InterruptedException {
        var taskName = "Setup code base";
        var task = taskRepository.findByName(taskName).orElseThrow(EntityNotFoundException::new);
        var currentDeadline = task.getDeadline();
        var execution = new SimpleAsyncTaskExecutor();
        var result = execution.submit(() -> {
            transactionTemplate.executeWithoutResult((status) -> {
                var prjTask = taskRepository.findById(10001L);
                var taskEntity = prjTask.orElseThrow(EntityNotFoundException::new);
                taskEntity.setDeadline(taskEntity.getDeadline().plusDays(1));
                taskRepository.save(taskEntity);
            });
        });
        result.get();

        // TODO [Exercise]: Compare currentDeadline with value on DB -> Explain result
        Assertions.assertEquals(currentDeadline, taskRepository.findByName(taskName).orElseThrow(EntityNotFoundException::new).getDeadline());
        // TODO [Exercise][Optional -> require at week 2]: How to get newest value on DB?
    }
}
