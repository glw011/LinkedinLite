package util;

import java.util.Arrays;
//import java.util.LinkedList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection2 {
    private static final boolean DEBUG = true;

    private static final String DB_USER = (System.getenv("DB_USER") != null) ? System.getenv("DB_USER") : "dbuser";
    private static final String DB_PASS = (System.getenv("DB_PASS") != null) ? System.getenv("DB_PASS") : "CSC403";
    private static final String DB_HOST = (System.getenv("DB_HOST") != null) ? System.getenv("DB_HOST") : "localhost";
    private static final String DB_PORT = (System.getenv("DB_PORT") != null) ? System.getenv("DB_PORT") : "3306";
    private static final String DB_NAME = (System.getenv("DB_NAME") != null) ? System.getenv("DB_NAME") : "lldb";

    private static final String DB_URL = String.format("jdbc:mysql://%s:%s/%s?useSSL=false;AUTO_SERVER=TRUE", DB_HOST, DB_PORT, DB_NAME);

    private Connection connection;
    private Statement statement;

    public DBConnection2(){
        this.connection = null;
        this.statement = null;
    }


    public Connection getConnection() throws SQLException{
        if(this.connection == null){
            try{
                this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                if(DEBUG) System.out.println("Connection to DB Successful");
            }
            //TODO: Add loop to retry connection 2 or 3 times?
            catch(SQLException e){
                if(DEBUG) System.out.println("Connection to DB Failed");
                System.err.println(e);
                System.err.println(Arrays.toString(e.getStackTrace()));
            }
        }
        return this.connection;
    }

    public Statement getStatement() throws SQLException{      //TODO: Try/Catch reattempt in catch
        if(this.connection == null){getConnection();}
        if(this.statement == null){this.statement = this.connection.createStatement();}
        return this.statement;
    }

    public ResultSet queryDB(String query) throws SQLException{
        return getStatement().executeQuery(query);
    }

    public void closeDBConnection() throws SQLException {
        if (this.statement != null) this.statement.close();
        if (this.connection != null) this.connection.close();
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

}
*/


