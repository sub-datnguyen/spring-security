package ch.elca.boostup.repository;

import ch.elca.boostup.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, TaskRepositoryCustom {
    Optional<TaskEntity> findByName(String name);
}
