package ch.elca.boostup;

import ch.elca.boostup.entity.DeveloperEntity;
import ch.elca.boostup.entity.TaskEntity;
import ch.elca.boostup.entity.TaskLogWorkEntity;
import ch.elca.boostup.exception.ElcaBusinessException;
import ch.elca.boostup.repository.DeveloperRepository;
import ch.elca.boostup.repository.ProjectRepository;
import ch.elca.boostup.repository.TaskRepository;
import ch.elca.boostup.services.TaskService;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class PerformanceOptimizeTest extends HibernateApplicationTests {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private TaskService taskService;

    @Test
    @Sql("classpath:test_data.sql")
    void testPerformanceByTransformResult() {
        // Solve N + 1 issue by custom query + transform result
        Assertions.assertEquals(2, projectRepository.findByFinishingDate(LocalDate.of(2021, 12, 31)).size());
    }


    @Test
    @Sql("classpath:test_data.sql")
    void testPerformanceByEntityGraph() {
        // Solve N + 1 issue by entity graph
        Assertions.assertEquals(2, projectRepository.findByFinishingDateByEntityGraph(LocalDate.of(2021, 12, 31)).size());
    }

    /**
     * [Exercise] Turn on batch insert/update and monitor result
     */
    @Test
    void testPerformanceByBatchInsert() throws ElcaBusinessException {
        var project = projectRepository.findByName("Boostup program")
            .orElseThrow(() -> new ElcaBusinessException("Project Boostup program cannot be found"));
        var task = project.getTasks().stream().findAny()
            .orElseThrow(() -> new ElcaBusinessException("Project Boostup program is missing task"));
        var dev = developerRepository.findById(10001L)
            .orElseThrow(() -> new ElcaBusinessException("Developer not found with id 10001"));
        for (int i  = 0; i < 1000; i++) {
            initTaskLogWork(i, task, dev);
        }
        taskService.save(task);
        jpaUtil.flush();
    }

    private TaskLogWorkEntity initTaskLogWork(int spendTime, TaskEntity task, DeveloperEntity dev) {
        var logWork = new TaskLogWorkEntity();
        logWork.setTask(task);
        logWork.setSpendTime(spendTime);
        logWork.setAuthor(dev);
        logWork.initAuditData();
        task.getLogWorks().add(logWork);
        return logWork;
    }

    @Test
    @Sql("classpath:test_data.sql")
    void testPerformanceBySecondLevelCache() {
        // TODO [Exercise] config second level cache and add UT for it
    }
}
