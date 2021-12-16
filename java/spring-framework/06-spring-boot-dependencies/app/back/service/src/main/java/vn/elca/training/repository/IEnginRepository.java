package vn.elca.training.repository;

import vn.elca.training.entity.EnginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author vlp
 */
@Repository
public interface IEnginRepository extends JpaRepository<EnginEntity, Long>,
        QuerydslPredicateExecutor<EnginEntity>, IEnginRepositoryCustom {

}
