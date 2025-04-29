
package dao;

import java.sql.*;

import model.Interest;
import model.ModelManager;
import model.Student;
import model.UserType;
import dao.PostDAO;
import util.DBConnection;
import util.DBConnection2;
import java.util.*;

public class StudentDAO extends UserDAO{

    /**
     * add student to db
     */
    public boolean addStdnt(String fname, String email, String hashedPass, String schoolName, String majorName) throws SQLException {
        if(addUser(email, hashedPass, UserType.STUDENT)){
            int userId = ModelManager.getUserId(email);
            int schoolId = ModelManager.getSchoolIdByName(schoolName);
            int majorId = ModelManager.getMajorIdByName(majorName);

            String usrSql = "UPDATE Users SET school_id = ? WHERE user_id = ?";

            String stdSql = "UPDATE Students SET major_id = ?, fname = ? WHERE student_id = ?";

            try (PreparedStatement usrPstmt = DBConnection2.getPrepStatement(usrSql);
                PreparedStatement stdPstmt = DBConnection2.getPrepStatement(stdSql))
            {
                usrPstmt.setInt(1, schoolId);
                usrPstmt.setInt(2, userId);

                stdPstmt.setInt(1, majorId);
                stdPstmt.setString(2, fname);
                stdPstmt.setInt(3, userId);

                return (usrPstmt.executeUpdate() > 0)&&(stdPstmt.executeUpdate() > 0);
            }

        }
        return false;
    }
    // TODO: FIX
    public Student getStudentById(int id) throws SQLException {
        return null;
    }

    //TODO: FIX
    public LinkedList<Student> getAllStudents() throws SQLException{
        LinkedList<Student> studentList;
        String skillSql = "";
        String intrstSql = "";
        String orgSql = "";
        String follows = "";

        String sqlStr =
            "SELECT DISTINCT" +
                "Students.student_id as id, " +
                "Students.fname as fname, " +
                "Students.lname as lname, " +
                "User_Verify.email as email, " +
                "Users.bio as bio, " +
                "Users.pfp_id as pfp_id, " +
                "Majors.major_name as major_name, " +
                "Majors.major_id as major_id, " +
                "Users.school_id as school_id " +
            "FROM " +
                "User_Verify JOIN Users ON User_Verify.user_id = Users.user_id " +
                    "JOIN Students ON Students.student_id = Users.user_id " +
                    "JOIN Majors ON Students.major_id = Majors.major_id";

        try(Statement stmt = DBConnection2.getStatement()){
            studentList = new LinkedList<>();
            ResultSet results = stmt.executeQuery(sqlStr);

            while(results.next()){
                int currId = results.getInt("id");
                Student currStudent = new Student(
                    currId,
                    results.getString("email"),
                    results.getString("fname"),
                    results.getString("lname"),
                    ModelManager.getSchool(results.getInt("school_id"))
                );
                LinkedList<Integer> currLst;

                currLst = getAllInterests(currId);
                LinkedList<Interest> intrstLst = new LinkedList<>();
                for(Integer intrstId : currLst){
                    intrstLst.add(ModelManager.getInterest(intrstId));
                }

                currLst = getAllFollowedUsers(currId);




                // TODO: Query Skills, Interests, Orgs, Follows
            }
        }


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

    public boolean deleteStudent(int id) throws SQLException {

        String sql = "DELETE FROM STUDENT WHERE stdnt_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

}










//package dao;
//
//import model.Student;
//import util.DBConnection;
//
//import java.sql.*;
//import java.util.*;
//
//public class StudentDAO {
//
//    public List<Student> getAllStudents() throws Exception {
//        List<Student> students = new ArrayList<>();
//        String sql = "SELECT * FROM STUDENT";
//
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                Student s = new Student();
//                s.setStdntId(rs.getInt("stdnt_id"));
//                s.setFname(rs.getString("fname"));
//                s.setLname(rs.getString("lname"));
//                s.setMajorId(rs.getInt("major_id"));
//                students.add(s);
//            }
//        }
//        return students;
//    }
//
//    public Student getStudentById(int id) throws Exception {
//        String sql = "SELECT * FROM STUDENT WHERE stdnt_id = ?";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                Student s = new Student();
//                s.setStdntId(rs.getInt("stdnt_id"));
//                s.setFname(rs.getString("fname"));
//                s.setLname(rs.getString("lname"));
//                s.setMajorId(rs.getInt("major_id"));
//                return s;
//            }
//        }
//        return null;
//    }
//
//    public boolean insertStudent(Student s) throws Exception {
//        String sql = "INSERT INTO STUDENT (fname, lname, major_id) VALUES (?, ?, ?)";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, s.getFname());
//            stmt.setString(2, s.getLname());
//            stmt.setInt(3, s.getMajorId());
//            return stmt.executeUpdate() > 0;
//        }
//    }
//
//    public boolean updateStudent(Student s) throws Exception {
//        String sql = "UPDATE STUDENT SET fname = ?, lname = ?, major_id = ? WHERE stdnt_id = ?";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, s.getFname());
//            stmt.setString(2, s.getLname());
//            stmt.setInt(3, s.getMajorId());
//            stmt.setInt(4, s.getStdntId());
//            return stmt.executeUpdate() > 0;
//        }
//    }
//

//}
