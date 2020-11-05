package vn.elca.codebase.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.elca.codebase.dto.ProjectServiceDto;
import vn.elca.codebase.entity.Project;
import vn.elca.codebase.mapper.common.LocalDateAndDateTimeMapper;
import vn.elca.codebase.mapper.decorator.ProjectDecorator;

@Mapper(componentModel = "spring", uses = LocalDateAndDateTimeMapper.class)
@DecoratedWith(ProjectDecorator.class)
public interface ProjectMapper {
    
    @Mapping(source = "id", target = "projectId")
    ProjectServiceDto toProjectServiceDto(Project project);
}
