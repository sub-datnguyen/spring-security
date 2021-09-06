package ch.elca.boostup.repository.impl;

import static ch.elca.boostup.entity.QTaskEntity.taskEntity;

import ch.elca.boostup.entity.TaskEntity;
import ch.elca.boostup.repository.TaskRepositoryCustom;
import java.util.List;

public class TaskRepositoryCustomImpl extends AbstractRepository<TaskEntity> implements TaskRepositoryCustom {

    @Override
    public List<TaskEntity> findTaskByProjectId(Long projectId) {
        return selectFrom(taskEntity)
            .where(taskEntity.project.id.eq(projectId))
            .fetch();
    }
}
