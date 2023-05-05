package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConection {
    private static final String USERNAME = "samanthaugr";
    private static final String PASSWORD = "samugr2023";
    private static final String URL = "jdbc:mariadb://localhost:3306/baseugrd";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}