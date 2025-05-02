// src/main/java/util/DBConnection2.java
package util;

import java.sql.*;

public class DBConnection2 {
    private static final boolean DEBUG = true;

    private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "dbuser");
    private static final String DB_PASS = System.getenv().getOrDefault("DB_PASS", "CSC403");
    private static final String DB_HOST = System.getenv().getOrDefault("DB_HOST", "db");
    private static final String DB_PORT = System.getenv().getOrDefault("DB_PORT", "3306");
    private static final String DB_NAME = System.getenv().getOrDefault("DB_NAME", "lldb");

    // Add allowPublicKeyRetrieval=true
    private static final String DB_URL = String.format(
            "jdbc:mysql://%s:%s/%s" +
                    "?useSSL=false" +
                    "&allowPublicKeyRetrieval=true" +
                    "&serverTimezone=UTC",
            DB_HOST, DB_PORT, DB_NAME
    );

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (DEBUG) {
                System.out.println("MySQL driver loaded");
                System.out.println("DB_URL = " + DB_URL);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver missing", e);
        }
    }

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            if (DEBUG) System.out.println("Connecting to DB URL: " + DB_URL);
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                if (DEBUG) System.out.println("DB connection established");
            } catch (SQLException e) {
                System.err.println("!!! DBConnection FAILED !!!");
                System.err.println("  errorCode = " + e.getErrorCode());
                System.err.println("  sqlState  = " + e.getSQLState());
                System.err.println("  message   = " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public static PreparedStatement getPrepStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public static PreparedStatement getPrepStatement(String sql, String[] cols) throws SQLException {
        return getConnection().prepareStatement(sql, cols);
    }

    public static ResultSet queryDB(String sql) throws SQLException {
        return getConnection().createStatement().executeQuery(sql);
    }

    public static void closeDBConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
