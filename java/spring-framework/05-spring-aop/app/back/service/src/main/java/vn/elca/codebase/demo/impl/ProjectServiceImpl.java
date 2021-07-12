package vn.elca.codebase.demo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.codebase.demo.ProjectService;
import vn.elca.codebase.repository.ProjectRepository;

@Service
@Transactional(rollbackFor = {Throwable.class})
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    
    @Override
    public Long demoCountProjects() {
        return projectRepository.countTotalProjects();
    }
}
