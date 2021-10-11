package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnector {
    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
            Properties appProps = new Properties();
            appProps.load(MySQLConnector.class.getResourceAsStream("/app.properties"));

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(appProps.getProperty("url"));
    }
}
