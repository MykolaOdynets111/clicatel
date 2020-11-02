/**
 *  The sqlDataAccess class is a handler class in order to be able the verify database entries and validate data quality
 *  Author: Juan-Claude Botha
 * https://confluence.clickatell.com/display/BIG/Sql+Data+Access+Layer
 */

package api.testUtilities.sqlDataAccessLayer;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import api.testUtilities.propertyConfigWrapper.configWrapper;

public class sqlDataAccess{

    private static int resultSet;

    // verifyPostgreDb is a simple sql lookup with a keyword driven approach
    public static String verifyPostgreDb(String dataBaseTable, String columnName, String operator, String fieldValue) throws IOException {

        String dbValue = "null";
        System.out.println("***************  verifyPostgreDb  ******************  ");

        try{
            Properties properties = configWrapper.loadPropertiesFile("config.properties");
            String connectionUrl = properties.getProperty("POSTGRESQL_CONNECTION_URL");
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(connectionUrl, properties.getProperty("RAAS_USER"),properties.getProperty("RAAS_PWD"));
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery("SELECT * FROM " + dataBaseTable + " WHERE " + columnName + " " + operator + " '" + fieldValue + "';");
            while (resultSet.next()){
                dbValue = resultSet.getString(columnName);

            }

            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return dbValue;
    }

    // verifyPostgreCustomSql is used in order to create custom sql queries for more advanced operations eg. update, insert get multiple results etc.
    public static String verifyPostgreCustomSql(String sqlQuery, String columnName)
    {
        Properties properties = configWrapper.loadPropertiesFile("config.properties");
        String dbValue = "null";
        //System.out.println("***************  verifyPostgreCustomSql  ******************  ");
        try {
            String connectionUrl = properties.getProperty("POSTGRESQL_CONNECTION_URL");
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(connectionUrl, properties.getProperty("RAAS_USER"), properties.getProperty("RAAS_PWD"));
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                dbValue = resultSet.getString(columnName);
                System.out.println("***************  verifyPostgreCustomSql dbValue  ******************  " + dbValue);

            }
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return dbValue;

    }

    // verifyMySQLDb is a simple sql lookup with a keyword driven approach
    public static String verifyMySQLDb(String dataBaseTable, String columnName, String operator, String fieldValue) throws IOException {

        String dbValue = "null";
        System.out.println("***************  verifyMySQLDb  ******************  ");
        try{
            Properties properties = configWrapper.loadPropertiesFile("config.properties");
            String connectionUrl = properties.getProperty("MYSQL_CONNECTION_URL");
            Class.forName(properties.getProperty("MYSQL_DRIVER"));
            Connection connection = DriverManager.getConnection(connectionUrl, properties.getProperty("CTX_USER"),properties.getProperty("CTX_PWD"));
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery("SELECT * FROM " + dataBaseTable + " WHERE " + columnName + " " + operator + " '" + fieldValue + "';");
            while (resultSet.next()){
                dbValue = resultSet.getString(columnName);

            }

            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return dbValue;
    }

    // verifyMySQLCustomSql is used in order to create custom sql queries for more advanced operations eg. update, insert get multiple results etc.
    public static String verifyMySQLCustomSql(String sqlQuery, String columnName)
    {
        Properties properties = configWrapper.loadPropertiesFile("config.properties");
        String dbValue = "null";
        System.out.println("***************  verifyMySQLCustomSql  ******************  ");

        try {
            String connectionUrl = properties.getProperty("MYSQL_CONNECTION_URL");
            Class.forName(properties.getProperty("MYSQL_DRIVER"));
            Connection connection = DriverManager.getConnection(connectionUrl, properties.getProperty("CTX_USER"), properties.getProperty("CTX_PWD"));
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                dbValue = resultSet.getString(columnName);

            }

            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
    }

        return dbValue;

    }

    // verifyMySQLCustomSql is used in order to create custom sql queries for more advanced operations eg. executeUpdate()
    // @return either (1) the row count for SQL Data Manipulation Language (DML) statements
    //              or (2) 0 for SQL statements that return nothing
    public static String verifyMySQLUpdateSql(String sqlQuery, String columnName)
    {
        Properties properties = configWrapper.loadPropertiesFile("config.properties");
        String dbValue = "null";
        System.out.println("***************  verifyMySQLUpdateSql  ******************  ");

        try {
            String connectionUrl = properties.getProperty("MYSQL_CONNECTION_URL");
            Class.forName(properties.getProperty("MYSQL_DRIVER"));
            Connection connection = DriverManager.getConnection(connectionUrl, properties.getProperty("CTX_USER"), properties.getProperty("CTX_PWD"));
            Statement statement = connection.createStatement();

            resultSet = statement.executeUpdate(sqlQuery);
        //    while (resultSet == 1){
                //dbValue = resultSet;

         //   }
            System.out.println("** sqlQuery : " + sqlQuery);
            System.out.println("** resultSet : " + resultSet);
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return dbValue;

    }

}
