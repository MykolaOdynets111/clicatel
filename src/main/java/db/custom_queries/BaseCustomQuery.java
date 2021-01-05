package db.custom_queries;

import static util.readers.PropertiesReader.getProperty;

public class BaseCustomQuery {

    public static String dBSuffix = getProperty("mysql.hibernate.connection.url.value").split("/")[3];
    public static String dBSuffixPostgres = getProperty("postgres.hibernate.connection.url.value").split("/")[3];
}
