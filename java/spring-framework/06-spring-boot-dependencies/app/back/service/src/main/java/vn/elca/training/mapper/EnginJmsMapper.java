package vn.elca.training.mapper;

import vn.elca.training.dto.EnginJmsDto;
import vn.elca.training.engine.Engin;
import vn.elca.training.entity.CategorieExploitationErEntity;
import vn.elca.training.entity.EnginEntity;
import vn.elca.training.entity.SecteurEntity;
import vn.elca.training.mapper.common.LocalDateAndDateTimeMapper;
import org.mapstruct.Context;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {LocalDateAndDateTimeMapper.class})
@DecoratedWith(EnginJmsMapperDecorator.class)
public interface EnginJmsMapper {
    EnginJmsDto toEnginDto(Engin engin);
    
    void updateEnginEntityFromEnginDto(EnginJmsDto enginJmsDto, @MappingTarget EnginEntity enginEntity,
                                       @Context String synchroDrive,
                                       @Context SecteurEntity secteurEntity,
                                       @Context CategorieExploitationErEntity categorieExploitationErEntity);
}
