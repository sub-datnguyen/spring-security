package vn.elca.training.repository;

import vn.elca.training.entity.EnginEntity;

import java.util.Optional;

/**
 * @author vlp
 */
public interface IEnginRepositoryCustom {

    Optional<EnginEntity> getEnginByNumeroIdentification(Long numeroIdentification);
}
