package ch.elca.boostup.repository;

import ch.elca.boostup.entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long>,
    QuerydslPredicateExecutor<DeveloperEntity> {
}
