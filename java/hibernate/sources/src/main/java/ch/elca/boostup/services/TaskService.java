package ch.elca.boostup.services;

import ch.elca.boostup.entity.TaskEntity;
import java.util.List;

/**
 * @author tcb
 */
public interface TaskService {

    List<TaskEntity> findByProjectId(Long projectId);

    void save(TaskEntity task);
}
