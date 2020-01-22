package db.engine;

import db.enums.Sessions;
import db.properties.HibernateConnectionProperties;
import lombok.extern.java.Log;
import lombok.var;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Entity;

import static java.lang.String.format;

/**
 * Class factory, is following factory pattern and give appropriate connection configuration based on sql dialog as a result
 * <p>
 *
 * @author ksichenko
 */

@Log
public class HibernateFactory {

    public SessionFactory getSessionFactory(final Sessions session) {
        switch (session) {
            case MY_SQL:
                return buildMySqlSession();
            case POSTGRES_SQL:
                return buildPostgresSqlSession();
            default:
                throw new IllegalArgumentException(format("Incorrect session: '%s' was requested !!", session));
        }
    }

    private SessionFactory buildMySqlSession() {
        var configuration = new Configuration().addProperties(new HibernateConnectionProperties().getMySplProperties());
        var scanner = new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        scanner
                .findCandidateComponents("db.entities.mysql")
                .forEach(a -> {
                    try {
                        configuration.addAnnotatedClass(Class.forName(a.getBeanClassName()));
                    } catch (ClassNotFoundException e) {
                        log.warning("Entity classes are not found !!");
                    }
                });

        return configuration.buildSessionFactory();
    }

    private SessionFactory buildPostgresSqlSession() {
        var configuration = new Configuration().addProperties(new HibernateConnectionProperties().getPostgresSplProperties());
        var scanner = new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        scanner
                .findCandidateComponents("db.entities.postrges")
                .forEach(a -> {
                    try {
                        configuration.addAnnotatedClass(Class.forName(a.getBeanClassName()));
                    } catch (ClassNotFoundException e) {
                        log.warning("Entity classes are not found !!");
                    }
                });

        return configuration.buildSessionFactory();
    }
}
