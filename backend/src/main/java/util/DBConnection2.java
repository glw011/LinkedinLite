package util;

import java.sql.*;
import java.util.Arrays;

/**
 * Responsible for maintaining connection to DB and returning statement objects which allow querying of the DB
 * when called.
 */
public class DBConnection2 {
    private static final boolean DEBUG = true;
    // Constants used for connection to DB
    private static final String DB_USER = (System.getenv("DB_USER") != null) ? System.getenv("DB_USER") : "dbuser";
    private static final String DB_PASS = (System.getenv("DB_PASS") != null) ? System.getenv("DB_PASS") : "CSC403";
    private static final String DB_HOST = (System.getenv("DB_HOST") != null) ? System.getenv("DB_HOST") : "localhost";
    private static final String DB_PORT = (System.getenv("DB_PORT") != null) ? System.getenv("DB_PORT") : "3306";
    private static final String DB_NAME = (System.getenv("DB_NAME") != null) ? System.getenv("DB_NAME") : "lldb";

    private static final String DB_URL = String.format("jdbc:mysql://%s:%s/%s?useSSL=false;AUTO_SERVER=TRUE", DB_HOST, DB_PORT, DB_NAME);

    private static Connection connection = null;
    private static Statement statement = null;

    /**
     * Returns the current connection object to caller if one has already been made. If not, establishes a new
     * connection obj and returns that to caller.
     *
     * @return Connection object allowing sql queries to query data from the DB
     * @throws SQLException if DB error occurred
     */
    public static Connection getConnection() throws SQLException{
        if(connection == null){
            try{
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                if(DEBUG) System.out.println("Connection to DB Successful");
            }
            catch(SQLException e){
                if(DEBUG) System.out.println("Connection to DB Failed");
                System.err.println(e);
                System.err.println(Arrays.toString(e.getStackTrace()));
            }
        }
        return connection;
    }

    /**
     * Creates a general statement object for performing DB queries using the current connection to DB if it's active,
     * or creating new connection if not, and then returns the statement to caller
     *
     * @return General purpose statement object for performing DB queries
     * @throws SQLException if DB error occurred
     */
    public static Statement getStatement() throws SQLException{
        if(connection == null){getConnection();}
        return connection.createStatement();
    }

    /**
     * Creates prepared statement object for performing DB queries using the current connection to DB if it's active,
     * or creating new connection if not, and then returns the prepared statement to caller
     *
     * @param sqlStr The String containing properly formatted sql query that will be executed by the DB
     * @return prepared statement object that allows variables to be used for values in the query to prevent SQL injection
     * @throws SQLException if DB error occurred
     */
    public static PreparedStatement getPrepStatement(String sqlStr) throws SQLException{
        if(connection == null){getConnection();}
        return connection.prepareStatement(sqlStr);
    }

    /**
     * Creates prepared statement object for performing DB queries which return the value of the column indicated by colNames
     * using the current connection to DB if it's active, or creating new connection if not, and then
     * returns the prepared statement to caller
     *
     * @param sqlStr The String containing properly formatted sql query that will be executed by the DB
     * @param colNames The String containing the name of the column in the table being queried for which the resulting
     *                 value should be returned when the query is executed. Useful for having the unique ids assigned
     *                 to tuples when inserted into the DB be returned as a result of the query.
     * @return prepared statement object that allows variables to be used for values in the query to prevent SQL injection
     * @throws SQLException if DB error occurred
     */
    public static PreparedStatement getPrepStatement(String sqlStr, String[] colNames) throws SQLException{
        if(connection == null){getConnection();}
        return connection.prepareStatement(sqlStr, colNames);
    }

    /**
     * Takes a String containing a query which is executed and the result returned to caller
     *
     * @param query String consisting of a properly formatted SQL query, which will be executed to obtain the desired result
     * @return ResultSet object containing the tuples which result from the query that was executed
     * @throws SQLException if DB error occurred
     */
    public static ResultSet queryDB(String query) throws SQLException{
        return getStatement().executeQuery(query);
    }

    /**
     * Closes the current connection and statement to the DB if either is active
     *
     * @throws SQLException if DB error occurred
     */
    public static void closeDBConnection() throws SQLException {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }
}


