package ch.elca.boostup.services.impl;

import ch.elca.boostup.entity.TaskEntity;
import ch.elca.boostup.repository.TaskRepository;
import ch.elca.boostup.services.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<TaskEntity> findByProjectId(Long projectId) {
        return taskRepository.findTaskByProjectId(projectId);
    }

    @Override
    public void save(TaskEntity task) {
        taskRepository.save(task);
    }
}
