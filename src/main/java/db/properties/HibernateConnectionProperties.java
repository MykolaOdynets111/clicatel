package db.properties;



import java.util.Properties;

import static util.PropertiesReader.getProperty;

/**
 * Class Hibernate properties builder, then would be used by HibernateFactory
 * <p>
 *
 * @author ksichenko
 */

public class HibernateConnectionProperties {

    private Properties properties;

    private static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
    private static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
    private static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
    private static final String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PERSISTENCE_LOCK_TIMEOUT = "javax.persistence.lock.timeout";
    private static final String PERSISTENCE_QUERY_TIMEOUT = "javax.persistence.query.timeout";
    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String FORMAT_SQL = "hibernate.format_sql";
    private static final String HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS = "hibernate.current_session_context_class";


    public Properties getMySplProperties() {
        properties = new Properties();
        properties.setProperty(HIBERNATE_CONNECTION_URL, getProperty("mysql.hibernate.connection.url.value"));
        properties.setProperty(HIBERNATE_CONNECTION_USERNAME, getProperty("mysql.hibernate.connection.username.value"));
        properties.setProperty(HIBERNATE_CONNECTION_PASSWORD, getProperty("mysql.hibernate.connection.password.value"));
        properties.setProperty(HIBERNATE_CONNECTION_DRIVER_CLASS, getProperty("mysql.hibernate.connection.driver_class.value"));
        properties.setProperty(HIBERNATE_DIALECT, getProperty("mysql.hibernate.dialect.value"));
        properties.setProperty(PERSISTENCE_LOCK_TIMEOUT, getProperty("mysql.javax.persistence.lock.timeout.value"));
        properties.setProperty(PERSISTENCE_QUERY_TIMEOUT, getProperty("mysql.javax.persistence.query.timeout.value"));
        properties.setProperty(SHOW_SQL, getProperty("mysql.hibernate.show_sql.value"));
        properties.setProperty(FORMAT_SQL, getProperty("mysql.hibernate.format_sql.value"));
        //such configuration for getting current session
        properties.setProperty(HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS, getProperty("mysql.hibernate.current_session_context_class.value"));

        return properties;
    }

    public Properties getPostgresSplProperties() {
        properties = new Properties();
        properties.setProperty(HIBERNATE_CONNECTION_URL, getProperty("postgres.hibernate.connection.url.value"));
        properties.setProperty(HIBERNATE_CONNECTION_USERNAME, getProperty("postgres.hibernate.connection.username.value"));
        properties.setProperty(HIBERNATE_CONNECTION_PASSWORD, getProperty("postgres.hibernate.connection.password.value"));
        properties.setProperty(HIBERNATE_CONNECTION_DRIVER_CLASS, getProperty("postgres.hibernate.connection.driver_class.value"));
        properties.setProperty(HIBERNATE_DIALECT, getProperty("postgres.hibernate.dialect.value"));
        properties.setProperty(PERSISTENCE_LOCK_TIMEOUT, getProperty("postgres.javax.persistence.lock.timeout.value"));
        properties.setProperty(PERSISTENCE_QUERY_TIMEOUT, getProperty("postgres.javax.persistence.query.timeout.value"));
        properties.setProperty(SHOW_SQL, getProperty("postgres.hibernate.show_sql.value"));
        properties.setProperty(FORMAT_SQL, getProperty("postgres.hibernate.format_sql.value"));
        //such configuration for getting current session
        properties.setProperty(HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS, getProperty("postgres.hibernate.current_session_context_class.value"));

        return properties;
    }
}
