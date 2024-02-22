package vn.loto.rest01.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServletConnect {
    private static Connection connection;
    private SQLServletConnect(){

    }

    public static Connection getInstance() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://localhost:1433;database=sdbm;encrypt=true;trustServerCertificate=true;";
            String user = "dev";
            String password = "abc@123456";
            connection = DriverManager.getConnection(dbURL, user, password);
            return connection;
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
