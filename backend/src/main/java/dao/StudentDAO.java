
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Student;
import model.UserType
import dao.PostDAO;
import util.DBConnection;

import java.sql.Statement;
import java.util.*;

public class StudentDAO extends UserDAO{

    /**
     * add student to  db.
     * assuming STUDENT table has columns for fname, lname, email,
     * password, school_name, and major.
     */
    public boolean addStdnt(String fname, String email, String hashedPass, String schoolName, int majorId) throws Exception {
        if(UserDAO.addUser(email, hashedPass, UserType.STUDENT)){
            String sql = "UPDATE Students SET fname = ?, major_id = ? WHERE student_id = ?";

            try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()){

            }

        }

        String sql = "INSERT INTO STUDENT (stdntId, fname, lname, email, password, school_name, majorId) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(stdntId));
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, email);
            stmt.setString(4, hashedPass);
            stmt.setString(5, schoolName);
            stmt.setString(6, String.valueOf(majorId));

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * adds an interest to user's profile.
     * assume ENTITY_INTERESTS table with entity_id and intrest_id columns.
     */
    public boolean addInterest(String hkey, int interestId) throws Exception {

        String[] parts = hkey.split("_");

        if (parts.length < 2) {
            return false;
        }

        int studentId = Integer.parseInt(parts[1]);

        String sql = "INSERT INTO ENTITY_INTERESTS (entity_id, interest_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, interestId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * removes an interest from user's profile.
     */
    public boolean delInterest(String hkey, int interestId) throws Exception {

        String[] parts = hkey.split("_");

        if (parts.length < 2) {
            return false;
        }

        int studentId = Integer.parseInt(parts[1]);

        String sql = "DELETE FROM ENTITY_INTERESTS WHERE entity_id = ? AND interest_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, interestId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * follows another entity
     * inserts into a FOLLOW table with columns entity_id and following_id.
     */
    public boolean followEnt(String hkey, int entityToFollowId) throws Exception {

        String[] parts = hkey.split("_");

        if (parts.length < 2) {
            return false;
        }

        int studentId = Integer.parseInt(parts[1]);

        String sql = "INSERT INTO FOLLOW (entity_id, following_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entityToFollowId);
            stmt.setInt(2, studentId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * unfollow entity.
     */
    public boolean unfollowEnt(String hkey, int entityToUnfollowId) throws Exception {

        String[] parts = hkey.split("_");

        if (parts.length < 2) {
            return false;
        }

        int studentId = Integer.parseInt(parts[1]);

        String sql = "DELETE FROM FOLLOW WHERE entity_id = ? AND following_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entityToUnfollowId);
            stmt.setInt(2, studentId);
            return stmt.executeUpdate() > 0;
        }
    }

    public Student getStudentById(int id) throws Exception {

        String sql = "SELECT * FROM STUDENT WHERE stdnt_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student s = new Student();
                s.setStdntId(rs.getInt("stdnt_id"));
                s.setFname(rs.getString("fname"));
                s.setLname(rs.getString("lname"));
                s.setMajorId(rs.getInt("major_id"));
                return s;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() throws Exception {

        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM STUDENT";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Student s = new Student();
                s.setStdntId(rs.getInt("stdnt_id"));
                s.setFname(rs.getString("fname"));
                s.setLname(rs.getString("lname"));
                s.setSchoolId(rs.getInt("school_id"));
                s.setMajorId(rs.getInt("major_id"));
                s.setEmail(rs.getString("email"));
                s.setHashedPass(rs.getString("password"));
                s.setSchoolName(rs.getString("school_name"));

                students.add(s);
            }
        }
        return students;
    }
    public boolean deleteStudent(int id) throws Exception {

        String sql = "DELETE FROM STUDENT WHERE stdnt_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean updateStudent(Student s) throws Exception {

        String sql = "UPDATE STUDENT SET fname = ?, lname = ?, major_id = ? WHERE stdnt_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, s.getStdntId());
            stmt.setString(2, s.getFname());
            stmt.setString(3, s.getLname());
            stmt.setInt(4, s.getSchoolId());
            stmt.setInt(5, s.getMajorId());
            stmt.setString(6, s.getEmail());
            stmt.setString(7, s.getHashedPass());
            stmt.setString(8, s.getSchoolName());
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
