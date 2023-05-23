package ch.elca.boostup.repository.impl;

import ch.elca.boostup.entity.AbstractCodeBaseEntity;
import ch.elca.boostup.util.JpaUtil;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

public abstract class AbstractRepository<T extends AbstractCodeBaseEntity> {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    protected JpaUtil jpaUtil;

    protected <K extends AbstractCodeBaseEntity> JPAQuery<K> selectFrom(EntityPath<K> path) {
        return new JPAQuery<K>(em).from(path);
    }

    public Optional<T> findOne(JPAQuery<T> path) {
        return Optional.ofNullable(path.fetchOne());
    }

    protected Query createNativeQuery(String queryString) {
        return this.em.createNativeQuery(queryString);
    }
}
