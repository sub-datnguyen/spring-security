package vn.elca.training.common.repository.impl;

import vn.elca.training.entity.AbstractEntity;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * @author vlp
 */
public abstract class AbstractRepositoryImpl<T extends AbstractEntity> implements InitializingBean {
    
    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;
    
    @PersistenceContext
    private EntityManager em;
    
    protected EntityPath<T> path;
    protected PathBuilder<T> builder;
    protected Querydsl querydsl;
    protected JpaEntityInformation<T, String> entityInformation;
    
    @Override
    public void afterPropertiesSet() {
        if (getDomainClass() != null) {
            entityInformation = new JpaRepositoryFactory(em).getEntityInformation(getDomainClass());
            this.path = DEFAULT_ENTITY_PATH_RESOLVER.createPath(entityInformation.getJavaType());
            this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
            this.querydsl = new Querydsl(em, builder);
        }
    }
    
    protected <K> JPAQuery<K> selectFrom(EntityPath<K> path) {
        return new JPAQuery<K>(em).from(path);
    }
    
    public Optional<T> findOne(JPAQuery<T> path) {
        return Optional.ofNullable(path.fetchOne());
    }
    
    protected abstract Class<T> getDomainClass();
    
    
}
