package vn.elca.training.repository.impl;

import org.springframework.stereotype.Repository;
import vn.elca.training.common.repository.impl.AbstractRepositoryImpl;
import vn.elca.training.entity.SecteurEntity;

/**
 *
 * @author vlp
 */
@Repository
public class ISecteurRepositoryImpl extends AbstractRepositoryImpl<SecteurEntity> {

    @Override
    protected Class<SecteurEntity> getDomainClass() {
        return SecteurEntity.class;
    }
    
    
}
