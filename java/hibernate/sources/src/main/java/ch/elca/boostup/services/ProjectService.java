package ch.elca.boostup.services;

import ch.elca.boostup.entity.ProjectEntity;
import ch.elca.boostup.entity.TaskEntity;
import ch.elca.boostup.exception.ElcaBusinessException;
import java.util.List;
import java.util.Optional;

/**
 * @author tcb
 */
public interface ProjectService {

    ProjectEntity save(ProjectEntity projectEntity);

    Optional<ProjectEntity> getProjectByName(String name);

    void createTasksForProject(ProjectEntity projectEntity, List<TaskEntity> tasks) throws ElcaBusinessException;

    void deleteProject(ProjectEntity projectEntity);

    List<ProjectEntity> findAll();
}
