package vn.elca.codebase.mapper;

import vn.elca.codebase.dto.UserDto;
import vn.elca.codebase.entity.User;
import vn.elca.codebase.mapper.common.LocalDateAndDateTimeMapper;
import vn.elca.codebase.mapper.decorator.UserDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = LocalDateAndDateTimeMapper.class)
@DecoratedWith(UserDecorator.class)
public interface UserMapper {

    UserDto toDto(User user);

    User toExistingEntity(UserDto dto, @MappingTarget User entity);

    User clone(User entity);
}
