package ch.elca.boostup.services.impl;

import ch.elca.boostup.entity.ProjectEntity;
import ch.elca.boostup.entity.TaskEntity;
import ch.elca.boostup.exception.ElcaBusinessException;
import ch.elca.boostup.repository.DeveloperRepository;
import ch.elca.boostup.repository.ProjectRepository;
import ch.elca.boostup.services.ProjectService;
import ch.elca.boostup.services.TaskService;
import ch.elca.boostup.util.JpaUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;
    private final DeveloperRepository developerRepository;
    private final JpaUtil jpaUtil;


    @Override
    public ProjectEntity save(ProjectEntity projectEntity) {
        return projectRepository.save(projectEntity);
    }

    @Override
    public Optional<ProjectEntity> getProjectByName(String name) {
        return projectRepository.findByName(name);
    }

    @Override
    public void createTasksForProject(ProjectEntity projectEntity, List<TaskEntity> tasks) throws ElcaBusinessException {
        for (TaskEntity task : tasks) {
            task.setProject(projectEntity);
        }
        projectEntity.getTasks().addAll(tasks);
        projectRepository.save(projectEntity);
    }

    @Override
    public void deleteProject(ProjectEntity projectEntity) {
        projectRepository.delete(projectEntity);
    }

    @Override
    public List<ProjectEntity> findAll() {
        return projectRepository.findAll();
    }

}
