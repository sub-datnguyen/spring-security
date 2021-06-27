package vn.elca.codebase.common.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CommonWebMapper.class})
public interface ProjectWebMapper {
}
