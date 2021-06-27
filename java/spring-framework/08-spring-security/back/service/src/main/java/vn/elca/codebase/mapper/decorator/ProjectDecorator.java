package vn.elca.codebase.mapper.decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import vn.elca.codebase.dto.ProjectServiceDto;
import vn.elca.codebase.entity.Project;
import vn.elca.codebase.mapper.ProjectMapper;

import java.util.stream.Collectors;

public abstract class ProjectDecorator implements ProjectMapper {
    
    @Autowired
    @Qualifier("delegate")
    private ProjectMapper delegate;
    
    @Override
    public ProjectServiceDto toProjectServiceDto(Project project) {
        ProjectServiceDto result = delegate.toProjectServiceDto(project);
        result.setTaskNames(project.getTasks().stream()
                                   .map(task -> task.getName()).collect(Collectors.joining(", ")));
        return result;
    }
}