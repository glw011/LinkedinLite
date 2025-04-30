
package dao;

import java.sql.*;

import model.*;
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

    public Student getStudentById(int stdId) throws SQLException {
        Student studentObj;

        String sqlStr =
            "SELECT DISTINCT" +
                "Students.student_id as id, " +
                "Students.fname as fname, " +
                "Students.lname as lname, " +
                "User_Verify.email as email, " +
                "Users.bio as bio, " +
                "Users.pfp_id as pfp_id, " +
                "Students.major_id as major_id, " +
                "Users.school_id as school_id " +
            "FROM " +
                "User_Verify JOIN Users ON User_Verify.user_id = Users.user_id " +
                "JOIN Students ON Students.student_id = Users.user_id " +
            "WHERE " +
                "Users.user_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sqlStr)){
            ResultSet results = pstmt.executeQuery();
            studentObj = new Student(
                    results.getInt("id"),
                    results.getString("email"),
                    results.getString("fname"),
                    results.getString("lname"),
                    ModelManager.getSchool(results.getInt("school_id"))
            );

            studentObj.setBio(results.getString("bio"));
            studentObj.setMajor(results.getInt("major_id"));
            studentObj.setProfilePic(results.getInt("pfp_id"));

            studentObj.setSkillList(getAllSkills(studentObj.getID()));
            studentObj.setInterestList(getAllInterests(studentObj.getID()));
            studentObj.setOrgList(getAllOrgs(studentObj.getID()));
            studentObj.setFollowingList(getAllFollowedUsers(studentObj.getID()));
            studentObj.setPostsList(getAllUserPosts(studentObj.getID()));
            studentObj.setOwnedImgsList(getAllOwnedImages(studentObj.getID()));
        }
        return studentObj;
    }

    public HashMap<Integer, Student> getAllStudents() throws SQLException{
        HashMap<Integer, Student> studentMap;

        String sqlStr =
            "SELECT DISTINCT" +
                "Students.student_id as id, " +
                "Students.fname as fname, " +
                "Students.lname as lname, " +
                "User_Verify.email as email, " +
                "Users.bio as bio, " +
                "Users.pfp_id as pfp_id, " +
                "Students.major_id as major_id, " +
                "Users.school_id as school_id " +
            "FROM " +
                "User_Verify JOIN Users ON User_Verify.user_id = Users.user_id " +
                    "JOIN Students ON Students.student_id = Users.user_id ";

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sqlStr)){
            studentMap = new HashMap<>();
            ResultSet results = pstmt.executeQuery();

            while(results.next()){
                int currId = results.getInt("id");
                Student currStudent = new Student(
                    currId,
                    results.getString("email"),
                    results.getString("fname"),
                    results.getString("lname"),
                    ModelManager.getSchool(results.getInt("school_id"))
                );

                currStudent.setBio(results.getString("bio"));
                currStudent.setMajor(results.getInt("major_id"));
                currStudent.setProfilePic(results.getInt("pfp_id"));

                currStudent.setSkillList(getAllSkills(currId));
                currStudent.setInterestList(getAllInterests(currId));
                currStudent.setOrgList(getAllOrgs(currId));
                currStudent.setFollowingList(getAllFollowedUsers(currId));
                currStudent.setPostsList(getAllUserPosts(currId));
                currStudent.setOwnedImgsList(getAllOwnedImages(currId));

                studentMap.putIfAbsent(currId, currStudent);
            }
            results.close();
        }
        return studentMap;
    }

    // TODO: Nah fam, once you're in, you're in for life. Ain't no escape
    public boolean deleteStudent(int id) throws SQLException {
        return false;
    }

    public LinkedList<Integer> getAllSkills(int stdId) throws SQLException{
        String sql = "SELECT skill_id FROM Student_Skills WHERE student_id = ?";
        LinkedList<Integer> skillLst;

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)){
            pstmt.setInt(1, stdId);
            ResultSet skills = pstmt.executeQuery();
            skillLst = new LinkedList<>();

            while(skills.next()){
                skillLst.add(skills.getInt("skill_id"));
            }

            skills.close();
        }
        return skillLst;
    }

    public LinkedList<Integer> getAllOrgs(int stdId) throws SQLException{
        String sql = "SELECT org_id FROM Org_Membership WHERE student_id = ?";
        LinkedList<Integer> orgLst;

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)){
            pstmt.setInt(1, stdId);
            ResultSet orgs = pstmt.executeQuery();
            orgLst = new LinkedList<>();

            while(orgs.next()){
                orgLst.add(orgs.getInt("org_id"));
            }

            orgs.close();
        }
        return orgLst;
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
