package db.clients;

import db.enums.Sessions;
import lombok.Synchronized;
import lombok.extern.java.Log;
import lombok.var;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.List;

import static db.engine.HibernateConnection.getDBConnectionAs;
import static java.lang.String.format;
import static util.listeners.AllureCustomListener.attachQuery;

/**
 * Class DB based client, consist functionality which following CRUD methodology
 * <p>
 * getEntityById() - GET
 * addEntity() - CREATE
 * updateEntity() - UPDATE
 * deleteEntity() - DELETE
 * getAllTableEntities() - custom realization to get all rows for appropriate table
 * <p>
 * Such methods can be used directly or via client or control wrappers
 * <p>
 *
 * @author ksichenko
 */

@Log
public class HibernateBaseClient {

    private static Session session = null;
    private static Transaction transaction = null;

    @Synchronized
    public static <T> T getEntityById(final Sessions myCurrentSession,
                                      final Serializable id,
                                      final Class<T> entityClass) {
        try {
            session = getDBConnectionAs(myCurrentSession).openSession();
            transaction = session.beginTransaction();
            T result = session.get(entityClass, id);
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            log.warning(format("The entity wasn't gotten by id !! \n The issue is: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.warning(format("The cause of issue is: %s", e.getCause()));
            }
        } finally {
            session.close();
        }
        throw new IllegalStateException(format("Entity: '%s' was not found !!", entityClass));
    }

    @Synchronized
    public static Serializable addEntity(final Sessions myCurrentSession,
                                         final Object entityObject) {
        try {
            session = getDBConnectionAs(myCurrentSession).openSession();
            transaction = session.beginTransaction();
            var result = session.save(entityObject);
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            log.warning(format("The entity wasn't added !! \n The issue is: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.warning(format("The cause of issue is: %s", e.getCause()));
            }
        } finally {
            session.close();
        }
        throw new IllegalStateException(format("Entity: '%s' was not saved !!", entityObject));
    }

    @Synchronized
    public static void updateEntity(final Sessions myCurrentSession,
                                    final Object entityObject) {
        try {
            session = getDBConnectionAs(myCurrentSession).openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(entityObject);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            log.warning(format("The entity wasn't updated !! \n The issue is: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.warning(format("The cause of issue is: %s", e.getCause()));
            }
        } finally {
            session.close();
        }
    }

    @Synchronized
    public static void deleteEntity(final Sessions myCurrentSession,
                                    final Object entityObject) {
        try {
            session = getDBConnectionAs(myCurrentSession).openSession();
            transaction = session.beginTransaction();
            session.delete(entityObject);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            log.warning(format("The entity wasn't deleted !! \n The issue is: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.warning(format("The cause of issue is: %s", e.getCause()));
            }
        } finally {
            session.close();
        }
    }


    //use method carefully, just when really need - in case of big data might caused a failures due to timeout
    @Synchronized
    public static <T> List<T> getAllTableEntities(final Sessions myCurrentSession,
                                                  final Class<T> entityClass) {
        try {
            session = getDBConnectionAs(myCurrentSession).openSession();
            transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            var criteriaBuilderQuery = criteriaBuilder.createQuery(entityClass);
            var root = criteriaBuilderQuery.from(entityClass);
            criteriaBuilderQuery.select(root);
            var createQueryResult = session.createQuery(criteriaBuilderQuery);
            var listOfEntityTable = createQueryResult.getResultList();

            transaction.commit();
            return listOfEntityTable;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            log.warning(format("The entities weren't gotten !! \n The issue is: %s", e.getMessage()));
            if (e.getCause() != null && transaction.isActive()) {
                log.warning(format("The cause of issue is: %s", e.getCause()));
            }
        } finally {
            session.close();
        }
        throw new IllegalStateException(format("List of Entities: '%s' was not saved !!", entityClass));
    }

    @Synchronized
    public static void executeCustomQuery(final Sessions myCurrentSession,
                                          final String query) {
        try {
            session = getDBConnectionAs(myCurrentSession).openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            log.warning(format("The custom query wasn't executed !! \n The issue is: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.warning(format("The cause of issue is: %s", e.getCause()));
            }
            throw new IllegalStateException(format("Query: '%s' hasn't been executed !!", query));
        } finally {
            session.close();
        }
    }

    @Synchronized
    public static String executeCustomQueryAndReturnValue(final Sessions myCurrentSession,
                                                          final String query) {
        try {
            session = getDBConnectionAs(myCurrentSession).openSession();
            transaction = session.beginTransaction();
            String value = session.createSQLQuery(query).getSingleResult().toString();
            transaction.commit();
            return value;
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            log.warning(format("The custom query wasn't executed !! \n The issue is: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.warning(format("The cause of issue is: %s", e.getCause()));
            }
        } finally {
            session.close();
        }
        throw new IllegalStateException(format("No results found using query: '%s' !!", query));
    }

    @Synchronized
    public static List<String> executeCustomQueryAndReturnValues(final Sessions myCurrentSession,
                                                                 final String query) {
        attachQuery(query);
        try {
            session = getDBConnectionAs(myCurrentSession).openSession();
            transaction = session.beginTransaction();
            List<String> value = session.createSQLQuery(query).getResultList();
            transaction.commit();
            return value;
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            log.warning(format("The custom query wasn't executed !! \n The issue is: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.warning(format("The cause of issue is: %s", e.getCause()));
            }
        } finally {
            session.close();
        }
        throw new IllegalStateException(format("No results found using query: '%s' !!", query));
    }

    @Synchronized
    public static <T> List<T> executeCustomQueryAndReturnValues(final Sessions myCurrentSession,
                                                                final String query,
                                                                final Class<T> entityClass) {
        attachQuery(query);
        try {
            session = getDBConnectionAs(myCurrentSession).openSession();
            transaction = session.beginTransaction();
            List<T> value = session.createNativeQuery(query, entityClass).list();
            transaction.commit();
            return value;
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            log.warning(format("The custom query wasn't executed !! \n The issue is: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.warning(format("The cause of issue is: %s", e.getCause()));
            }
        } finally {
            session.close();
        }
        throw new IllegalStateException(format("No results found using query: '%s' !!", query));
    }
}
