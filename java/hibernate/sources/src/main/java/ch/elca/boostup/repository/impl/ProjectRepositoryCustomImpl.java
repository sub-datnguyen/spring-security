package ch.elca.boostup.repository.impl;

import static ch.elca.boostup.entity.QProjectEntity.projectEntity;

import ch.elca.boostup.entity.ProjectEntity;
import ch.elca.boostup.repository.ProjectRepositoryCustom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryCustomImpl extends AbstractRepository<ProjectEntity> implements ProjectRepositoryCustom {
    @Override
    public Optional<ProjectEntity> findByName(String name) {
        return findOne(selectFrom(projectEntity)
            .leftJoin(projectEntity.tasks).fetchJoin()
            .where(projectEntity.name.eq(name)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProjectEntity> findByFinishingDate(LocalDate finishingDate) {
        // TODO [Exercise] implement this method. Requirement in java doc
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProjectEntity> findByFinishingDateByEntityGraph(LocalDate finishingDate) {
        // TODO [Exercise] implement this method. Requirement in java doc
        return null;
    }
}
