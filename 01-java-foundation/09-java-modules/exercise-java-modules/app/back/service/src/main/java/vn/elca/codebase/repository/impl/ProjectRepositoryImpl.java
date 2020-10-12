package vn.elca.codebase.repository.impl;

import org.springframework.stereotype.Repository;
import vn.elca.codebase.entity.Project;
import vn.elca.codebase.entity.QProject;
import vn.elca.codebase.repository.ProjectCustomRepository;

@Repository
public class ProjectRepositoryImpl extends AbstractCodeBaseRepositoryImpl<Project> implements ProjectCustomRepository {
    @Override
    public Long countTotalProjects() {
        return selectFrom(QProject.project).fetchCount();
    }
}
