package db.engine;

import db.enums.Sessions;
import org.hibernate.SessionFactory;

/**
 * Class connector, is following singleton pattern and give thread saf connection as a result
 * <p>
 *
 * @author ksichenko
 */

public class HibernateConnection {

    private static final ThreadLocal<SessionFactory> myConnection = new ThreadLocal<>();

    private static SessionFactory getInitializeConnection(final Sessions session) {
        return new HibernateFactory().getSessionFactory(session);
    }

    public static SessionFactory getDBConnectionAs(final Sessions session) {
        if (myConnection.get() == null) {
            myConnection.set(getInitializeConnection(session));
        }
        return myConnection.get();
    }

    public static void closeDBConnection() {
        if (myConnection.get() != null) {
            myConnection.get().close();
            myConnection.remove();
        }
    }
}
