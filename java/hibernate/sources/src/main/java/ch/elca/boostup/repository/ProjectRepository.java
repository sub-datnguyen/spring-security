package ch.elca.boostup.repository;

import ch.elca.boostup.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long>, ProjectRepositoryCustom,
    QuerydslPredicateExecutor<ProjectEntity> {

}
