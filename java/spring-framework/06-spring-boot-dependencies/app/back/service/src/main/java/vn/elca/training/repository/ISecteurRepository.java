package vn.elca.training.repository;

import vn.elca.training.entity.SecteurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author vlp
 */
@Repository
public interface ISecteurRepository extends JpaRepository<SecteurEntity, Long>,
        QuerydslPredicateExecutor<SecteurEntity>, ISecteurRepositoryCustom {
    
}
