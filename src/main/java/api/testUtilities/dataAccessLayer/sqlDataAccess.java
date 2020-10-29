package api.testUtilities.dataAccessLayer;

import java.sql.*;

public class sqlDataAccess{

    public static String verifyDbValue(String dataBaseTable, String tableColumn, String fieldValue) {

        String dbValue = null;

        try {
            String connectionUrl = "jdbc:postgresql://pgdb.qa.za01.payd.co:5432/raas";
            Connection conn = DriverManager.getConnection(connectionUrl, "kaizen_qa_admin_user", "5Of#ZAVvGa27bD");
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
