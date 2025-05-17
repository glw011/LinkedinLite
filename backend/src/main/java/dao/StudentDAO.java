
package dao;

import model.*;
import util.DBConnection2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class StudentDAO extends UserDAO{

    /**
     * Add data from new user of UserType.STUDENT to the DB by inserting row into the User_Verify table and Students table
     *
     * @param fname first name of the new student user
     * @param email email of the new student user
     * @param hashedPass hashed string resulting from hashing the new student user's password
     * @param schoolName name of school new student user attends
     * @param majorName name of the major of the new student user
     * @return true if data successfully inserted into both the User_Verify and Students tables, else false
     * @throws SQLException if DB error occurred
     */
    public static boolean addStdnt(String fname, String email, String hashedPass, String schoolName, String majorName) throws SQLException {
        if(addUser(email, hashedPass, UserType.STUDENT)){
            int userId = ModelManager.getUserId(email);
            int schoolId = ModelManager.getSchoolIdByName(schoolName);
            int majorId = ModelManager.getMajorIdByName(majorName);

            String usrSql = "UPDATE Users SET school_id = ? WHERE user_id = ?";
            String stdSql = "UPDATE Students SET major_id = ?, fname = ? WHERE student_id = ?";

            try (PreparedStatement usrPstmt = DBConnection2.getPstmt(usrSql);
                 PreparedStatement stdPstmt = DBConnection2.getPstmt(stdSql))
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

    public boolean updateStudent(Student s) throws SQLException {
        String sql = "UPDATE Students SET fname = ?, lname = ?, major_id = ? WHERE student_id = ?";
        try (PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)) {
            pstmt.setString(1, s.getFname());
            pstmt.setString(2, s.getLname());
            pstmt.setInt(3, s.getMajor());
            pstmt.setInt(4, s.getID());
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Get the data for a student(identified by stdId) as a Student obj
     *
     * @param stdId unique user_id/student_id of the student whose data is being retrieved
     * @return Student object containing that student's data
     * @throws SQLException if DB error occurred
     */
    public static Student getStudentById(int stdId) throws SQLException {
        Student studentObj = null;

        String sqlStr =
                "SELECT DISTINCT " +
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

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sqlStr)) {
            pstmt.setInt(1, stdId);
            ResultSet results = pstmt.executeQuery();

            if (results.next()) {
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

            results.close();
        }

        return studentObj;
    }

    /**
     * Retrieves all users who are students from database along with their relevant data and creates Student obj for each
     *
     * @return HashMap containing unique student_id of each student user as a key paired with the Student obj of that student as val
     * @throws SQLException if DB error occurred
     */
    public static HashMap<Integer, Student> getAllStudents() throws SQLException{
        HashMap<Integer, Student> studentMap;

        String sqlStr =
            "SELECT DISTINCT " +
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

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sqlStr)){
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

    /**
     * Sets last name of the student user identified by userId in the DB to the String passed as lname
     *
     * @param userId Unique user_id/student_id of the student user changing their last name
     * @param lname String containing the last name of the student user
     * @return true if last name is updated successfully, else false
     * @throws SQLException if DB error occurred
     */
    public static boolean setLName(int userId, String lname) throws SQLException{
        String sql = "UPDATE Students SET lname = ? WHERE student_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setString(1, lname);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;
        }
    }

    // Nah fam, once you're in, you're in for life. Ain't no escape from this DB
    public static boolean deleteStudent(int id) throws SQLException {
        return false;
    }

    /**
     * Adds new a new skill(identified by skillId) to the list of skills attributed to the student(identified by userId)
     * if that skill is not already attributed to that student(i.e. present in the Student_Skills table).
     *
     * @param userId unique user_id/student_id of the student user a skill is being added to
     * @param skillId unique skill_id of the skill being attributed to the student user
     * @return true if skill not previously attributed with student and was successfully added to their skills, else false
     * @throws SQLException if DB error occurred
     */
    public static boolean addSkill(int userId, int skillId) throws SQLException{
        String sql = "INSERT INTO Student_Skills (student_id, skill_id) VALUES (?, ?)";

        LinkedList<Integer> skillLst = getAllSkills(userId);
        // only add new skill if the user does not already have the skill
        if(!skillLst.contains(skillId)){
            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setInt(1, userId);
                pstmt.setInt(2, skillId);

                return pstmt.executeUpdate() > 0;
            }
        }
        return false;
    }

    /**
     * Deletes a skill(identified by skillId) associated with a student(identified by userId) from the student's list
     * of attributed skills if that skill is currently attributed with them.
     *
     * @param userId unique user_id/student_id of the student user a skill is being deleted from
     * @param skillId unique skill_id of the skill being deleted from the student's list of attributed skills
     * @return true if skill was attributed to user and successfully deleted, else false
     * @throws SQLException if DB error occurs
     */
    public static boolean delSkill(int userId, int skillId) throws SQLException{
        String sql = "DELETE FROM Student_Skills WHERE student_id = ? AND skill_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);
            pstmt.setInt(2, skillId);

            return pstmt.executeUpdate() >0;
        }
    }

    /**
     * Gets list of skill ids for all skills attributed to a student(identified by stdId)
     *
     * @param stdId unique user_id/student_id of the student whose skills are being retrieved from the DB
     * @return LinkedList of integer vals where each val in the list is the skill_id for a skill attributed to student
     * @throws SQLException if DB error occurred
     */
    public static LinkedList<Integer> getAllSkills(int stdId) throws SQLException{
        String sql = "SELECT skill_id FROM Student_Skills WHERE student_id = ?";
        LinkedList<Integer> skillLst;

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
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

    /**
     * Gets the ids of all orgs a student(identified by stdId) is currently a member of
     *
     * @param stdId unique user_id/student_id of the student whose memberships are being retrieved
     * @return LinkedList of integer vals where each val in list is the unique org_id identifying an org the student
     *  is a member of
     * @throws SQLException if DB error occurred
     */
    public static LinkedList<Integer> getAllOrgs(int stdId) throws SQLException{
        String sql = "SELECT org_id FROM Org_Membership WHERE student_id = ?";
        LinkedList<Integer> orgLst;

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
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

    /**
     * Inserts student_id and org_id into Pending_Requests table when Student requests membership in Org
     *
     * @param stdId Unique student_id val for Student requesting membership
     * @param orgId Unique org_id val for Org that Student is requesting membership into
     * @return true if insertion successful, else false
     * @throws SQLException If DB error occurs
     */
    public static boolean requestMembership(int stdId, int orgId) throws SQLException{
        String sql = "INSERT INTO Pending_Requests (org_id, student_id) VALUES (?, ?)";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, orgId);
            pstmt.setInt(2, stdId);

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Gets all Orgs a Student has pending invites to join as a member
     *
     * @param stdId Unique student_id of Student seeking all pending invites
     * @return LinkedList containing Org objs of Orgs which have invited a student(identified by stdId)
     * @throws SQLException if DB error occurs
     */
    public static LinkedList<Org> getAllPendingInvites(int stdId) throws SQLException{
        String sql = "SELECT org_id FROM Pending_Invites WHERE student_id = ?";
        LinkedList<Org> orgLst = new LinkedList<>();

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, stdId);

            ResultSet orgIds = pstmt.executeQuery();

            while(orgIds.next()){
                Org currOrg = OrgDAO.getOrgById(orgIds.getInt("org_id"));
                orgLst.add(currOrg);
            }
            orgIds.close();

            if(!orgLst.isEmpty()) return orgLst;
        }
        return null;
    }

    /**
     * Removes tuple entry in Pending_Invites and inserts it into Org_Membership once approval granted by student
     *
     * @param stdId Unique student_id val of Student invited by Org
     * @param orgId Unique org_id val of Org that invited Student to become member
     * @return true if removal from Pending_Invites and inserts it into Org_Membership, else false
     * @throws SQLException if DB error occurs
     */
    public static boolean approveOrgInvite(int stdId, int orgId) throws SQLException{
        boolean approveSuccess = OrgDAO.addMember(orgId, stdId);
        if(approveSuccess){
            String sql = "DELETE FROM Pending_Invites WHERE student_id = ? AND org_id = ?";

            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setInt(1, stdId);
                pstmt.setInt(2, orgId);

                return pstmt.executeUpdate() > 0;
            }
        }
        return false;
    }

    /**
     * Removes tuple entry in Pending_Invites and does not insert it into Org_Membership once student denies invite
     *
     * @param stdId Unique student_id val of Student invited by Org
     * @param orgId Unique org_id val of Org that invited Student to become member
     * @return true if removal from Pending_Invites successful, else false
     * @throws SQLException if DB error occurs
     */
    public static boolean denyOrgInvite(int stdId, int orgId) throws SQLException{
        String sql = "DELETE FROM Pending_Invites WHERE student_id = ? AND org_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, stdId);
            pstmt.setInt(2, orgId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public static LinkedList<Student> searchStudentsByName(String search) throws SQLException {
        String sql =
                "SELECT DISTINCT " +
                        "Students.student_id AS id, " +
                        "Students.fname AS fname, " +
                        "Students.lname AS lname, " +
                        "User_Verify.email AS email, " +
                        "Users.bio AS bio, " +
                        "Users.pfp_id AS pfp_id, " +
                        "Students.major_id AS major_id, " +
                        "Users.school_id AS school_id " +
                        "FROM Students " +
                        "JOIN Users ON Students.student_id = Users.user_id " +
                        "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                        "WHERE " +
                        "LOWER(Students.fname) LIKE ? OR " +
                        "LOWER(Students.lname) LIKE ? OR " +
                        "LOWER(CONCAT(Students.fname, ' ', Students.lname)) LIKE ?";

        LinkedList<Student> list = new LinkedList<>();

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            String pattern = search.toLowerCase() + "%";
            pstmt.setString(1, pattern); // fname
            pstmt.setString(2, pattern); // lname
            pstmt.setString(3, pattern); // full name (fname + lname)

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Student s = new Student(
                        id,
                        rs.getString("email"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        ModelManager.getSchool(rs.getInt("school_id"))
                );

                s.setBio(rs.getString("bio"));
                s.setMajor(rs.getInt("major_id"));
                s.setProfilePic(rs.getInt("pfp_id"));
                s.setSkillList(getAllSkills(id));
                s.setInterestList(getAllInterests(id));
                s.setOrgList(getAllOrgs(id));
                s.setFollowingList(getAllFollowedUsers(id));
                s.setPostsList(getAllUserPosts(id));
                s.setOwnedImgsList(getAllOwnedImages(id));

                list.add(s);
            }

            rs.close();
        }

        return list;
    }

    public static LinkedList<Student> searchStudentsBySchool(String schoolName) throws SQLException {
        LinkedList<Student> resultLst = new LinkedList<>();
        ResultSet rs;
        int schoolId = SchoolDAO.getSchoolIdByName(schoolName);

        if(schoolId > 0){
            String sql =
                    "SELECT DISTINCT " +
                            "Students.student_id AS id, " +
                            "Students.fname AS fname, " +
                            "Students.lname AS lname, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Students.major_id AS major_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Students JOIN Users ON Students.student_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN Schools ON Schools.school_id = Users.school_id " +
                        "WHERE " +
                            "Users.school_id = ?";

            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setInt(1, schoolId);

                rs = pstmt.executeQuery();
            }

        }
        else{
            String sql =
                    "SELECT DISTINCT " +
                            "Students.student_id AS id, " +
                            "Students.fname AS fname, " +
                            "Students.lname AS lname, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Students.major_id AS major_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Students JOIN Users ON Students.student_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN Schools ON Schools.school_id = Users.school_id " +
                        "WHERE " +
                            "LOWER(Schools.school_name) LIKE ?";

            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setString(1, String.join("%", String.join(schoolName.toLowerCase(), "%")));

                rs = pstmt.executeQuery();
            }
        }

        while (rs.next()) {
            int id = rs.getInt("id");
            Student std = new Student(
                    id,
                    rs.getString("email"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    ModelManager.getSchool(rs.getInt("school_id"))
            );

            std.setBio(rs.getString("bio"));
            std.setMajor(rs.getInt("major_id"));
            std.setProfilePic(rs.getInt("pfp_id"));
            std.setSkillList(getAllSkills(id));
            std.setInterestList(getAllInterests(id));
            std.setOrgList(getAllOrgs(id));
            std.setFollowingList(getAllFollowedUsers(id));
            std.setPostsList(getAllUserPosts(id));
            std.setOwnedImgsList(getAllOwnedImages(id));

            resultLst.add(std);
        }

        rs.close();

        return resultLst;
    }

    public static LinkedList<Student> searchStudentsByMajor(String majorName) throws SQLException {
        LinkedList<Student> resultLst = new LinkedList<>();
        ResultSet rs;
        int majorId = MajorDAO.getMajorIdByName(majorName);

        if(majorId > 0){
            String sql =
                    "SELECT DISTINCT " +
                            "Students.student_id AS id, " +
                            "Students.fname AS fname, " +
                            "Students.lname AS lname, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Students.major_id AS major_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Students JOIN Users ON Students.student_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                        "WHERE " +
                            "Students.major_id = ?";

            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setInt(1, majorId);

                rs = pstmt.executeQuery();
            }

        }
        else{
            String sql =
                    "SELECT DISTINCT " +
                            "Students.student_id AS id, " +
                            "Students.fname AS fname, " +
                            "Students.lname AS lname, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Students.major_id AS major_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Students JOIN Users ON Students.student_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN Majors ON Majors.major_id = Students.major_id " +
                        "WHERE " +
                            "LOWER(Majors.major_name) LIKE ?";

            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setString(1, String.join("%", String.join(majorName.toLowerCase(), "%")));

                rs = pstmt.executeQuery();
            }
        }

        while (rs.next()) {
            int id = rs.getInt("id");
            Student std = new Student(
                    id,
                    rs.getString("email"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    ModelManager.getSchool(rs.getInt("school_id"))
            );

            std.setBio(rs.getString("bio"));
            std.setMajor(rs.getInt("major_id"));
            std.setProfilePic(rs.getInt("pfp_id"));
            std.setSkillList(getAllSkills(id));
            std.setInterestList(getAllInterests(id));
            std.setOrgList(getAllOrgs(id));
            std.setFollowingList(getAllFollowedUsers(id));
            std.setPostsList(getAllUserPosts(id));
            std.setOwnedImgsList(getAllOwnedImages(id));

            resultLst.add(std);
        }

        rs.close();

        return resultLst;
    }

    public static LinkedList<Student> searchStudentsBySkill(String skillName) throws SQLException {
        LinkedList<Student> resultLst = new LinkedList<>();
        ResultSet rs;
        int skillId = SkillDAO.getSkillIdByName(skillName);

        if(skillId > 0){
            String sql =
                    "SELECT DISTINCT " +
                            "Students.student_id AS id, " +
                            "Students.fname AS fname, " +
                            "Students.lname AS lname, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Students.major_id AS major_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Students JOIN Users ON Students.student_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN Student_Skills ON Student_Skills.student_id = Students.student_id " +
                                "JOIN Skills ON Student_Skills.skill_id = Skills.skill_id " +
                        "WHERE " +
                            "Student_Skills.skill_id = ?";

            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setInt(1, skillId);

                rs = pstmt.executeQuery();
            }

        }
        else{
            String sql =
                    "SELECT DISTINCT " +
                            "Students.student_id AS id, " +
                            "Students.fname AS fname, " +
                            "Students.lname AS lname, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Students.major_id AS major_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Students JOIN Users ON Students.student_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN Student_Skills ON Student_Skills.student_id = Students.student_id " +
                                "JOIN Skills ON Student_Skills.skill_id = Skills.skill_id " +
                        "WHERE " +
                            "LOWER(Skills.skill_name) LIKE ?";

            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setString(1, String.join("%", String.join(skillName.toLowerCase(), "%")));

                rs = pstmt.executeQuery();
            }
        }

        while (rs.next()) {
            int id = rs.getInt("id");
            Student std = new Student(
                    id,
                    rs.getString("email"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    ModelManager.getSchool(rs.getInt("school_id"))
            );

            std.setBio(rs.getString("bio"));
            std.setMajor(rs.getInt("major_id"));
            std.setProfilePic(rs.getInt("pfp_id"));
            std.setSkillList(getAllSkills(id));
            std.setInterestList(getAllInterests(id));
            std.setOrgList(getAllOrgs(id));
            std.setFollowingList(getAllFollowedUsers(id));
            std.setPostsList(getAllUserPosts(id));
            std.setOwnedImgsList(getAllOwnedImages(id));

            resultLst.add(std);
        }

        rs.close();

        return resultLst;
    }

    public static LinkedList<Student> searchStudentsByInterest(String interestName) throws SQLException {
        LinkedList<Student> resultLst = new LinkedList<>();
        ResultSet rs;
        int interestId = InterestDAO.getInterestIdByName(interestName);

        if(interestId > 0){
            String sql =
                    "SELECT DISTINCT " +
                            "Students.student_id AS id, " +
                            "Students.fname AS fname, " +
                            "Students.lname AS lname, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Students.major_id AS major_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Students JOIN Users ON Students.student_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN User_Interests ON User_Interests.user_id = Students.student_id " +
                                "JOIN Interests ON Interests.interest_id = User_Interests.interest_id " +
                        "WHERE " +
                            "User_Interests.interest_id = ?";

            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setInt(1, interestId);

                rs = pstmt.executeQuery();
            }

        }
        else{
            String sql =
                    "SELECT DISTINCT " +
                            "Students.student_id AS id, " +
                            "Students.fname AS fname, " +
                            "Students.lname AS lname, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Students.major_id AS major_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Students JOIN Users ON Students.student_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN User_Interests ON User_Interests.user_id = Students.student_id " +
                                "JOIN Interests ON Interests.interest_id = User_Interests.interest_id " +
                        "WHERE " +
                            "LOWER(Interests.interest_name) LIKE ?";

            try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
                pstmt.setString(1, String.join("%", String.join(interestName.toLowerCase(), "%")));

                rs = pstmt.executeQuery();
            }
        }

        while (rs.next()) {
            int id = rs.getInt("id");
            Student std = new Student(
                    id,
                    rs.getString("email"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    ModelManager.getSchool(rs.getInt("school_id"))
            );

            std.setBio(rs.getString("bio"));
            std.setMajor(rs.getInt("major_id"));
            std.setProfilePic(rs.getInt("pfp_id"));
            std.setSkillList(getAllSkills(id));
            std.setInterestList(getAllInterests(id));
            std.setOrgList(getAllOrgs(id));
            std.setFollowingList(getAllFollowedUsers(id));
            std.setPostsList(getAllUserPosts(id));
            std.setOwnedImgsList(getAllOwnedImages(id));

            resultLst.add(std);
        }

        rs.close();

        return resultLst;
    }
}
