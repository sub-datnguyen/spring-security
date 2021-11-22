package ch.elca.boostup.repository;

import ch.elca.boostup.entity.TaskEntity;
import java.util.List;

public interface TaskRepositoryCustom {
    List<TaskEntity> findTaskByProjectId(Long projectId);
}
