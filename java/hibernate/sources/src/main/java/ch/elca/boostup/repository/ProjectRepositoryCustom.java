package ch.elca.boostup.repository;

import ch.elca.boostup.entity.ProjectEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryCustom {
    Optional<ProjectEntity> findByName(String name);

    /**
     * [Exercise] Use transform result with nested projection to query projects by finishing date and fetch tasks with assignee user.
     * Requirement: only 1 query without N + 1 issue
     * {@link 'https://confluence.svc.elca.ch/pages/viewpage.action?pageId=842760489'}
     */
    List<ProjectEntity> findByFinishingDate(LocalDate finishingDate);

    /**
     * [Exercise] Use entity graph to query projects by finishing date and fetch tasks with assignee user.
     * Requirement: only 1 query without N + 1 issue
     * {@link 'https://www.baeldung.com/jpa-entity-graph'}
     */
    List<ProjectEntity> findByFinishingDateByEntityGraph(LocalDate finishingDate);
}
