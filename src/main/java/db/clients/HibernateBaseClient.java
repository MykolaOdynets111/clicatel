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

import static db.engine.HibernateConnection.closeConnection;
import static db.engine.HibernateConnection.getConnectionAs;
import static java.lang.String.format;

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
            session = getConnectionAs(myCurrentSession).getCurrentSession();
            transaction = session.beginTransaction();
            T result = (T) session.get(entityClass, id);
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            e.getMessage();
            if (transaction != null) transaction.rollback();
        } finally {
            closeConnection();
        }
        throw new IllegalStateException(format("Entity: '%s' was not found !!", entityClass));
    }

    @Synchronized
    public static Serializable addEntity(final Sessions myCurrentSession,
                                         final Object entityObject) {
        try {
            session = getConnectionAs(myCurrentSession).getCurrentSession();
            transaction = session.beginTransaction();
            var result = session.save(entityObject);
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.getMessage();
        } finally {
            closeConnection();
        }
        throw new IllegalStateException(format("Entity: '%s' was not saved !!", entityObject));
    }

    @Synchronized
    public static void updateEntity(final Sessions myCurrentSession,
                                    final Object entityObject) {
        try {
            session = getConnectionAs(myCurrentSession).getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(entityObject);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            log.warning(format("Entity: '%s' was not updated because of exception: %s !!", entityObject, e.getMessage()));
        } finally {
            closeConnection();
        }
    }

    @Synchronized
    public static void deleteEntity(final Sessions myCurrentSession,
                                    final Object entityObject) {
        try {
            session = getConnectionAs(myCurrentSession).getCurrentSession();
            transaction = session.beginTransaction();
            session.delete(entityObject);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            log.warning(format("Entity: '%s' was not deleted because of exception: %s !!", entityObject, e.getMessage()));
        } finally {
            closeConnection();
        }
    }

    @Synchronized
    public static <T> List<T> getAllTableEntities(final Sessions myCurrentSession,
                                                  final Class<T> entityClass) {
        try {
            session = getConnectionAs(myCurrentSession).getCurrentSession();
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
            e.getMessage();
        } finally {
            closeConnection();
        }

        throw new IllegalStateException(format("List of Entities: '%s' was not saved !!", entityClass));
    }
}
