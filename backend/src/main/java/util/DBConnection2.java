package util;

import java.sql.*;
import java.util.Arrays;

public class DBConnection2 {
    private static final boolean DEBUG = true;

    private static final String DB_USER = (System.getenv("DB_USER") != null) ? System.getenv("DB_USER") : "dbuser";
    private static final String DB_PASS = (System.getenv("DB_PASS") != null) ? System.getenv("DB_PASS") : "CSC403";
    private static final String DB_HOST = (System.getenv("DB_HOST") != null) ? System.getenv("DB_HOST") : "localhost";
    private static final String DB_PORT = (System.getenv("DB_PORT") != null) ? System.getenv("DB_PORT") : "3306";
    private static final String DB_NAME = (System.getenv("DB_NAME") != null) ? System.getenv("DB_NAME") : "lldb";

    private static final String DB_URL = String.format("jdbc:mysql://%s:%s/%s?useSSL=false;AUTO_SERVER=TRUE", DB_HOST, DB_PORT, DB_NAME);

    private static Connection connection = null;
    private static Statement statement = null;


    public static Connection getConnection() throws SQLException{
        if(connection == null){
            try{
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                if(DEBUG) System.out.println("Connection to DB Successful");
            }
            //TODO: Add loop to retry connection 2 or 3 times?
            catch(SQLException e){
                if(DEBUG) System.out.println("Connection to DB Failed");
                System.err.println(e);
                System.err.println(Arrays.toString(e.getStackTrace()));
            }
        }
        return connection;
    }

    public static Statement getStatement() throws SQLException{      //TODO: Try/Catch reattempt in catch
        if(connection == null){getConnection();}
        return connection.createStatement();
    }

    public static PreparedStatement getPrepStatement(String sqlStr) throws SQLException{
        if(connection == null){getConnection();}
        return connection.prepareStatement(sqlStr);
    }

    public static ResultSet queryDB(String query) throws SQLException{
        return getStatement().executeQuery(query);
    }

    public static void closeDBConnection() throws SQLException {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }

/*
    // TODO: Remove once logic doesn't need to be recycled somewhere
    public LinkedList<Student> getAllStudents() throws SQLException{
        LinkedList<Student> studentList = new LinkedList<Student>();
        String sqlStr =
                "SELECT DISTINCT" +
                    "Students.student_id as id," +
                    "Students.lname as lname," +
                    "Students.fname as fname," +
                    "Majors.major_name as major," +
                    "Majors.major_id as major_id," +
                    "Colleges.college_name as college," +
                    "Colleges.college_id as college_id," +
                    "Schools.name as school," +
                    "Schools.school_id as school_id," +
                    "Users.email as email," +
                    "Entities.bio as bio," +
                    "Entities.pfp_id as pfp_id" +
                "FROM" +
                    "Users JOIN Entities ON Users.user_id = Entities.entity_id" +
                        "JOIN Students ON Users.user_id = Students.student_id" +
                        "JOIN Majors ON Students.major_id = Majors.major_id" +
                        "JOIN Colleges ON Majors.college_id = Colleges.college_id" +
                        "JOIN Schools ON Colleges.school_id = Schools.school_id";

        ResultSet results = queryDB(sqlStr);

        while(results.next()){
            Student currStudent = new Student(
                    results.getInt("id"),
                    results.getAsciiStream("email").toString(),
                    results.getAsciiStream("fname").toString(),
                    results.getAsciiStream("lname").toString(),
                    DAOManager.getSchool(results.getInt("school_id"))
            );
            // TODO: Complete getAllStudents() method
            //currStudent.setMajor(DBO.majors.get(results.getInt("major_id")));
        }

        results.close();
        return studentList;
    }

*/
}


