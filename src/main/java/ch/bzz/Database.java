package ch.bzz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                Config.get("DB_URL"),
                Config.get("DB_USER"),
                Config.get("DB_PASSWORD"));
    }
}
