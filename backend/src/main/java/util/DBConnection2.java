package Backend;

import java.util.Arrays;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

// TODO: Rename to reflect responsibilities (Maintaining connection to DB and executing queries passed to method)
public class DBConnection2 {
    public static final boolean DEBUG = true;
    private static final String DBUSER = "dbuser";
    private static final String DBPASS = "CSC403";
    private static final String DATABASE = "jdbc:mysql://localhost/lldb;AUTO_SERVER=TRUE";

    private Connection connection;
    private Statement statement;

    public DBConnection2(){
        this.connection = null;
        this.statement = null;
    }


    public Connection getConnection(){
        if(this.connection == null){
            try{
                this.connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASS);
                if(DEBUG) System.out.println("Connection to DB Successful");
            }
            //TODO: Retry connection
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

    public void closeDBConnection() throws SQLException{
        if(this.statement != null) this.statement.close();
        if(this.connection != null) this.connection.close();
    }

    // TODO: Move to DBO?
    public void addNewUser(String email, String hashPass, UserType userType){
        String sqlQuery = String.format(
                "INSERT INTO Users (email, pass_hash, type) VALUES ('%s', '%s', '%s')",
                email, hashPass, userType.val
        );

        try{
            ResultSet results = queryDB(sqlQuery);
            results.close();
        }
        catch(SQLException e){
            if(DEBUG) System.out.println("Failed to add new user");
            System.err.println("Insert Failed: Error Code - "+e.getErrorCode());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
    }

    // TODO: Move to DBO and implement as part of populateUsersList()
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
