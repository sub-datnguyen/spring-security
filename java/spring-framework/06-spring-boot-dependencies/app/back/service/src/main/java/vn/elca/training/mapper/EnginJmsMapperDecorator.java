package vn.elca.training.mapper;

import vn.elca.training.dto.EnginJmsDto;
import vn.elca.training.entity.CategorieExploitationErEntity;
import vn.elca.training.entity.EnginEntity;
import vn.elca.training.entity.SecteurEntity;
import vn.elca.training.mapper.common.LocalDateAndDateTimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class EnginJmsMapperDecorator implements EnginJmsMapper {

    @Autowired
    @Qualifier("delegate")
    private EnginJmsMapper delegate;

    @Autowired
    private LocalDateAndDateTimeMapper localDateAndDateTimeMapper;

    @Override
    public void updateEnginEntityFromEnginDto(EnginJmsDto enginJmsDto, EnginEntity enginEntity,
                                              String synchroDrive, SecteurEntity secteurEntity,
                                              CategorieExploitationErEntity categorieExploitationErEntity) {
        delegate.updateEnginEntityFromEnginDto(enginJmsDto, enginEntity, synchroDrive, secteurEntity, categorieExploitationErEntity);
        enginEntity.setSynchroDrive(localDateAndDateTimeMapper.dateAndTimeToLocalDateTime(synchroDrive));
        enginEntity.setLocalisationSecteur(secteurEntity);
        enginEntity.setCategorieExploitationEr(categorieExploitationErEntity);
    }

}
