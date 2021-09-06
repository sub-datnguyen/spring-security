package ch.elca.boostup.test;

import ch.elca.boostup.HibernateApplicationTests;
import ch.elca.boostup.entity.DeveloperEntity;
import ch.elca.boostup.entity.TaskEntity;
import ch.elca.boostup.exception.ElcaBusinessException;
import ch.elca.boostup.repository.DeveloperRepository;
import ch.elca.boostup.services.ProjectService;
import ch.elca.boostup.services.TaskService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TransactionTest extends HibernateApplicationTests {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private TaskService taskService;

    /**
     * [Exercise]
     * Config transaction to create tasks for project independently. If any task creation has error, it doesn't impact to others.
     * Requirement: Don't validate and remove invalid task. Just config transaction and handle exception correctly
     */
    @Test
    void testTransactionSeparated() throws ElcaBusinessException {
        var project = projectService.getProjectByName("Boostup program")
            .orElseThrow(() -> new ElcaBusinessException("Can not find project Boostup program"));
        var dev = developerRepository.findById(10001L)
            .orElseThrow(() -> new ElcaBusinessException("Developer not found with id " + 10001L));
        var task1 = createTaskEntity("US1", "open", dev);
        var task2 = createTaskEntity("US2", "open", dev);
        var task3 = createTaskEntity("US3", "waiting", dev);
        projectService.createTasksForProject(project, List.of(task1, task2, task3));
        var updatedProject = projectService.getProjectByName("Boostup program");
        Assertions.assertTrue(updatedProject.isPresent());
        Assertions.assertEquals(2, taskService.findByProjectId(updatedProject.get().getId()).size(),
            "Expect 2 tasks were inserted successfully");
    }

    private TaskEntity createTaskEntity(String name, String status, DeveloperEntity assignee) {
        var task = new TaskEntity();
        task.setName(name);
        task.setDeadline(LocalDate.now().plusDays(15));
        task.setStatus(status);
        task.initAuditData();
        task.setAssignee(assignee);
        return task;
    }
}
