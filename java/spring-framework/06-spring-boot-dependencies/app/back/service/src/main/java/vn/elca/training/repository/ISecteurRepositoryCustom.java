package vn.elca.training.repository;

import vn.elca.training.entity.SecteurEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author vlp
 */
public interface ISecteurRepositoryCustom {
    Optional<SecteurEntity> getSecteurByCodeEntiteSecteur(String secteurCode);

}
