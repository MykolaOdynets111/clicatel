package db.properties;


import java.util.Properties;

import static util.readers.PropertiesReader.getProperty;

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
    private static final String HIBERNATE_CONNECTION_PROVIDER_CLASS = "hibernate.connection.provider_class";

    private static final String PULL_ACQUIRE_INCREMENT = "c3p0.acquire_increment";
    private static final String PULL_IDLE_TEST_PERIOD = "c3p0.idle_test_period";
    private static final String PULL_MAX_SIZE = "c3p0.max_size";
    private static final String PULL_MAX_STATEMENTS = "c3p0.max_statements";
    private static final String PULL_MIN_SIZE = "c3p0.min_size";
    private static final String PULL_TIMEOUT = "c3p0.timeout";

    public Properties getMySqlProperties() {
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
        //such configuration for getting pull connections
        properties.setProperty(HIBERNATE_CONNECTION_PROVIDER_CLASS, getProperty("hibernate.connection.provider_class"));
        properties.setProperty(PULL_ACQUIRE_INCREMENT, getProperty("c3p0.acquire_increment"));
        properties.setProperty(PULL_IDLE_TEST_PERIOD, getProperty("c3p0.idle_test_period"));
        properties.setProperty(PULL_MAX_SIZE, getProperty("c3p0.max_size"));
        properties.setProperty(PULL_MAX_STATEMENTS, getProperty("c3p0.max_statements"));
        properties.setProperty(PULL_MIN_SIZE, getProperty("c3p0.min_size"));
        properties.setProperty(PULL_TIMEOUT, getProperty("c3p0.timeout"));

        return properties;
    }

    public Properties getPostgresSqlProperties() {
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
        //such configuration for getting pull connections
        properties.setProperty(HIBERNATE_CONNECTION_PROVIDER_CLASS, getProperty("hibernate.connection.provider_class"));
        properties.setProperty(PULL_ACQUIRE_INCREMENT, getProperty("c3p0.acquire_increment"));
        properties.setProperty(PULL_IDLE_TEST_PERIOD, getProperty("c3p0.idle_test_period"));
        properties.setProperty(PULL_MAX_SIZE, getProperty("c3p0.max_size"));
        properties.setProperty(PULL_MAX_STATEMENTS, getProperty("c3p0.max_statements"));
        properties.setProperty(PULL_MIN_SIZE, getProperty("c3p0.min_size"));
        properties.setProperty(PULL_TIMEOUT, getProperty("c3p0.timeout"));

        return properties;
    }
}
