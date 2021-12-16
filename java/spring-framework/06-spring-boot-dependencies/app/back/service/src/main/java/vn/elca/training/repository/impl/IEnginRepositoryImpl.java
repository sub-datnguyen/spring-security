package vn.elca.training.repository.impl;

import vn.elca.training.common.repository.impl.AbstractRepositoryImpl;
import vn.elca.training.entity.EnginEntity;
import vn.elca.training.entity.QEnginEntity;
import vn.elca.training.repository.IEnginRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author vlp
 */
@Repository
public class IEnginRepositoryImpl extends AbstractRepositoryImpl<EnginEntity>
        implements IEnginRepositoryCustom {

    @Override
    public Optional<EnginEntity> getEnginByNumeroIdentification(Long numeroIdentification) {
        return findOne(selectFrom(QEnginEntity.enginEntity)
                .where(QEnginEntity.enginEntity.numeroIdentification.eq(numeroIdentification)));
    }

    @Override
    protected Class<EnginEntity> getDomainClass() {
        return EnginEntity.class;
    }
}
