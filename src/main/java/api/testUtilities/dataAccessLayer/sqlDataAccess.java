package api.testUtilities.dataAccessLayer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class sqlDataAccess{

    public static String verifyDbValue(String dataBaseTable, String tableColumn, String fieldValue) {

        String dbValue = null;

        try {
            String connectionUrl = "jdbc:postgresql://pgdb.qa.za01.payd.co:5432/raas";
            Connection conn = DriverManager.getConnection(connectionUrl, "qa_appl_w_readyapi", "api_rite");
            Statement stmt = conn.createStatement();
            ResultSet rs;

            StringBuilder query;
            rs = stmt.executeQuery("SELECT * FROM " + dataBaseTable + " WHERE " + tableColumn + " = " + fieldValue + ";");
            while (rs.next()) {
                dbValue = rs.getString(tableColumn);

            }

            conn.close();


        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return dbValue;

    }

}
