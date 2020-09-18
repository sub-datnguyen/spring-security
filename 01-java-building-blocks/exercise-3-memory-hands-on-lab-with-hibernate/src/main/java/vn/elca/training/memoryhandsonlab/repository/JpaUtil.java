package vn.elca.training.memoryhandsonlab.repository;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Component
public class JpaUtil {
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private DataSource dataSource;
    
    public StatelessSession openStatelessSession() {
        return emf.unwrap(SessionFactory.class).openStatelessSession(DataSourceUtils.getConnection(dataSource));
    }
    
    public static <T> BooleanBuilder in(SimpleExpression<T> path, Collection<T> list) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!list.isEmpty()) {
            for (List<T> chunk : Lists.partition(Lists.newArrayList(list), 1000)) {
                builder.or(path.in(chunk));
            }
            return builder;
        } else {
            return builder.and(path.in(list));
        }
    }
}
