package dao;

import model.ModelManager;
import model.Org;
import model.Student;
import model.UserType;
import util.DBConnection2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class OrgDAO extends UserDAO {

    public static int addOrg(String name, String email, String pass, String schoolName) throws SQLException {
        if (addUser(email, pass, UserType.ORG)) {
            int userId = ModelManager.getUserId(email);
            int schoolId = ModelManager.getSchoolIdByName(schoolName);

            String usrSql = "UPDATE Users SET school_id = ? WHERE user_id = ?";

            String orgSql = "UPDATE Orgs SET name = ? WHERE org_id = ?";

            try (PreparedStatement usrPstmt = DBConnection2.getPstmt(usrSql);
                 PreparedStatement orgPstmt = DBConnection2.getPstmt(orgSql)) {
                usrPstmt.setInt(1, schoolId);
                usrPstmt.setInt(2, userId);

                orgPstmt.setString(1, name);
                orgPstmt.setInt(2, userId);

                return ((usrPstmt.executeUpdate() > 0) && (orgPstmt.executeUpdate() > 0)) ? userId:-1;
            }
        }
        return -1;
    }

    public static Org getOrgById(int orgId) throws SQLException {
        Org orgObj;

        String sqlStr =
                "SELECT DISTINCT " +
                        "Orgs.org_id as id, " +
                        "Orgs.name as name, " +
                        "User_Verify.email as email, " +
                        "Users.bio as bio, " +
                        "Users.pfp_id as pfp_id, " +
                        "Users.school_id as school_id " +
                        "FROM " +
                        "User_Verify JOIN Users ON User_Verify.user_id = Users.user_id " +
                        "JOIN Orgs ON Orgs.org_id = Users.user_id " +
                        "WHERE " +
                        "Users.user_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sqlStr)) {
            pstmt.setInt(1, orgId);

            ResultSet results = pstmt.executeQuery();
            orgObj = new Org(
                    results.getInt("id"),
                    results.getString("email"),
                    results.getString("name"),
                    ModelManager.getSchool(results.getInt("school_id"))
            );

            orgObj.setBio(results.getString("bio"));
            orgObj.setProfilePicId(results.getInt("pfp_id"));

            orgObj.setInterestList(getAllUserInterests(orgObj.getID()));
            orgObj.setMembersList(getAllMembers(orgObj.getID()));
            orgObj.setFollowingList(getAllFollowedUsers(orgObj.getID()));
            orgObj.setPostsList(getAllUserPosts(orgObj.getID()));
            orgObj.setOwnedImgsList(getAllOwnedImages(orgObj.getID()));
            orgObj.setBannerImgId(getBannerImgId(orgObj.getID()));
        }
        return orgObj;
    }

    public static HashMap<Integer, Org> getAllOrgs() throws SQLException {
        HashMap<Integer, Org> orgMap;

        String sqlStr =
                "SELECT DISTINCT " +
                        "Orgs.org_id as id, " +
                        "Orgs.org_name as name, " +
                        "User_Verify.email as email, " +
                        "Users.bio as bio, " +
                        "Users.pfp_id as pfp_id, " +
                        "Users.school_id as school_id " +
                        "FROM " +
                        "User_Verify JOIN Users ON User_Verify.user_id = Users.user_id " +
                        "JOIN Orgs ON Orgs.org_id = Users.user_id ";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sqlStr)) {
            orgMap = new HashMap<>();
            ResultSet results = pstmt.executeQuery();

            while (results.next()) {
                int currId = results.getInt("id");
                Org orgObj = new Org(
                        results.getInt("id"),
                        results.getString("email"),
                        results.getString("name"),
                        ModelManager.getSchool(results.getInt("school_id"))
                );

                orgObj.setBio(results.getString("bio"));
                orgObj.setProfilePicId(results.getInt("pfp_id"));

                orgObj.setInterestList(getAllUserInterests(orgObj.getID()));
                orgObj.setMembersList(getAllMembers(orgObj.getID()));
                orgObj.setFollowingList(getAllFollowedUsers(orgObj.getID()));
                orgObj.setPostsList(getAllUserPosts(orgObj.getID()));
                orgObj.setOwnedImgsList(getAllOwnedImages(orgObj.getID()));
                orgObj.setBannerImgId(getBannerImgId(orgObj.getID()));

                orgMap.putIfAbsent(currId, orgObj);
            }
            results.close();
        }
        return orgMap;
    }

    public static LinkedList<Integer> getAllMembers(int orgId) throws SQLException {
        String sql = "SELECT student_id FROM Org_Membership WHERE org_id = ?";
        LinkedList<Integer> memberLst;

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, orgId);
            ResultSet members = pstmt.executeQuery();
            memberLst = new LinkedList<>();

            while (members.next()) {
                memberLst.add(members.getInt("student_id"));
            }

            members.close();
        }
        return memberLst;
    }

    public static boolean addMember(int orgId, int stdId) throws SQLException {
        String sql = "INSERT INTO Org_Membership (student_id, org_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, stdId);
            pstmt.setInt(2, orgId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public static boolean delMember(int orgId, int stdId) throws SQLException {
        String sql = "DELETE FROM Org_Membership WHERE student_id = ? AND org_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, stdId);
            pstmt.setInt(2, orgId);

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Inserts an entry containing a student_id, org_id into Pending_Invites table representing a student
     * being invited to join an org and awaiting that student's approval
     *
     * @param orgId Unique org_id val of Org inviting the Student
     * @param stdId Unique student_id val of the Student being invited to join Org
     * @return true if successful insertion, else false
     * @throws SQLException if DB error occurs
     */
    public static boolean inviteMember(int orgId, int stdId) throws SQLException {
        String sql = "INSERT INTO Pending_Invites (student_id, org_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, stdId);
            pstmt.setInt(2, orgId);

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Gets all students who have pending requests for membership to the Org identified by an org_id
     *
     * @param orgId Unique org_id of the Org seeking all membership requests
     * @return LinkedList containing Student Objs of students who have requested membership to an org(identified by orgId)
     * @throws SQLException if DB error occurs
     */
    public static LinkedList<Student> getAllPendingRequests(int orgId) throws SQLException {
        String sql = "SELECT student_id FROM Pending_Requests WHERE org_id = ?";
        LinkedList<Student> stdLst = new LinkedList<>();

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, orgId);

            ResultSet stdIds = pstmt.executeQuery();

            while (stdIds.next()) {
                Student currStd = StudentDAO.getStudentById(stdIds.getInt("student_id"));
                stdLst.add(currStd);
            }
            stdIds.close();

            if (!stdLst.isEmpty()) return stdLst;
        }
        return null;
    }

    /**
     * Removes tuple entry in Pending_Requests and inserts into Org_Membership once approved for membership by Org
     *
     * @param orgId Unique org_id val of the Org being joined
     * @param stdId Unique student_id val of the student joining the Org
     * @return true if removal from Pending_Requests and insertion into Org_Membership are both successful, else false
     * @throws SQLException if DB error occurs
     */
    public static boolean approveMemberRequest(int orgId, int stdId) throws SQLException {
        boolean addSuccess = addMember(orgId, stdId);
        if (addSuccess) {
            String sql = "DELETE FROM Pending_Requests WHERE org_id = ? AND student_id = ?";

            try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
                pstmt.setInt(1, orgId);
                pstmt.setInt(2, stdId);

                return pstmt.executeUpdate() > 0;
            }
        }
        return false;
    }

    /**
     * Removes tuple entry in Pending_Requests and does not insert into Org_Membership when denied by Org
     *
     * @param orgId Unique org_id val of the Org whose membership was requested
     * @param stdId Unique student_id val of the student who requested joining the Org
     * @return true if removal from Pending_Requests successful, else false
     * @throws SQLException if DB error occurs
     */
    public static boolean denyMemberRequest(int orgId, int stdId) throws SQLException {
        String sql = "DELETE FROM Pending_Requests WHERE org_id = ? AND student_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, orgId);
            pstmt.setInt(2, stdId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public static LinkedList<Org> searchOrgsByName(String search) throws SQLException {
        String sql =
                "SELECT DISTINCT " +
                        "Orgs.org_id AS id, " +
                        "Orgs.org_name AS name, " +
                        "User_Verify.email AS email, " +
                        "Users.bio AS bio, " +
                        "Users.pfp_id AS pfp_id, " +
                        "Users.school_id AS school_id " +
                        "FROM Orgs " +
                        "JOIN Users ON Orgs.org_id = Users.user_id " +
                        "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                        "WHERE LOWER(Orgs.org_name) LIKE ?";

        LinkedList<Org> results = new LinkedList<>();

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setString(1, search.toLowerCase() + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Org org = new Org(
                        id,
                        rs.getString("email"),
                        rs.getString("name"),
                        ModelManager.getSchool(rs.getInt("school_id"))
                );

                org.setBio(rs.getString("bio"));
                org.setProfilePicId(rs.getInt("pfp_id"));
                org.setInterestList(getAllUserInterests(id));
                org.setMembersList(getAllMembers(id));
                org.setFollowingList(getAllFollowedUsers(id));
                org.setPostsList(getAllUserPosts(id));
                org.setOwnedImgsList(getAllOwnedImages(id));
                org.setBannerImgId(getBannerImgId(id));

                results.add(org);
            }

            rs.close();
        }

        return results;
    }

    public static LinkedList<Org> searchOrgsBySchool(String schoolName) throws SQLException {
        LinkedList<Org> resultLst = new LinkedList<>();
        ResultSet rs;
        int schoolId = SchoolDAO.getSchoolIdByName(schoolName);

        if(schoolId > 0){
            String sql =
                    "SELECT DISTINCT " +
                            "Orgs.org_id AS id, " +
                            "Orgs.org_name AS name, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Orgs JOIN Users ON Orgs.org_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN Schools ON Schools.school_id = Users.school_id " +
                        "WHERE " +
                            "Users.school_id = ?";

            try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
                pstmt.setInt(1, schoolId);

                rs = pstmt.executeQuery();
            }

        }
        else{
            String sql =
                    "SELECT DISTINCT " +
                            "Orgs.org_id AS id, " +
                            "Orgs.org_name AS name, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Orgs JOIN Users ON Orgs.org_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN Schools ON Schools.school_id = Users.school_id " +
                        "WHERE " +
                            "LOWER(Schools.name) LIKE ?";

            try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
                pstmt.setString(1, String.join("%", String.join(schoolName.toLowerCase(), "%")));

                rs = pstmt.executeQuery();
            }
        }

        while (rs.next()) {
            int id = rs.getInt("id");
            Org org = new Org(
                    id,
                    rs.getString("email"),
                    rs.getString("name"),
                    ModelManager.getSchool(rs.getInt("school_id"))
            );

            org.setBio(rs.getString("bio"));
            org.setProfilePicId(rs.getInt("pfp_id"));
            org.setInterestList(getAllUserInterests(id));
            org.setMembersList(getAllMembers(id));
            org.setFollowingList(getAllFollowedUsers(id));
            org.setPostsList(getAllUserPosts(id));
            org.setOwnedImgsList(getAllOwnedImages(id));
            org.setBannerImgId(getBannerImgId(id));

            resultLst.add(org);
        }

        rs.close();

        return resultLst;
    }

    public static LinkedList<Org> searchOrgsByInterest(String interestName) throws SQLException {
        LinkedList<Org> resultLst = new LinkedList<>();
        ResultSet rs;
        int interestId = InterestDAO.getInterestIdByName(interestName);

        if(interestId > 0){
            String sql =
                    "SELECT DISTINCT " +
                            "Orgs.org_id AS id, " +
                            "Orgs.org_name AS name, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Orgs JOIN Users ON Orgs.org_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN User_Interests ON User_Interests.user_id = Users.user_id " +
                                "JOIN Interests ON Interests.interest_id = User_Interests.interest_id " +
                        "WHERE " +
                            "User_Interests.interest_id = ?";

            try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
                pstmt.setInt(1, interestId);

                rs = pstmt.executeQuery();
            }
        }
        else{
            String sql =
                    "SELECT DISTINCT " +
                            "Orgs.org_id AS id, " +
                            "Orgs.org_name AS name, " +
                            "User_Verify.email AS email, " +
                            "Users.bio AS bio, " +
                            "Users.pfp_id AS pfp_id, " +
                            "Users.school_id AS school_id " +
                        "FROM " +
                            "Orgs JOIN Users ON Orgs.org_id = Users.user_id " +
                                "JOIN User_Verify ON Users.user_id = User_Verify.user_id " +
                                "JOIN User_Interests ON User_Interests.user_id = Users.user_id " +
                                "JOIN Interests ON Interests.interest_id = User_Interests.interest_id " +
                        "WHERE " +
                            "LOWER(Interests.interest_name) LIKE ?";

            try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
                pstmt.setString(1, String.join("%", String.join(interestName.toLowerCase(), "%")));

                rs = pstmt.executeQuery();
            }
        }

        while (rs.next()) {
            int id = rs.getInt("id");
            Org org = new Org(
                    id,
                    rs.getString("email"),
                    rs.getString("name"),
                    ModelManager.getSchool(rs.getInt("school_id"))
            );

            org.setBio(rs.getString("bio"));
            org.setProfilePicId(rs.getInt("pfp_id"));
            org.setInterestList(getAllUserInterests(id));
            org.setMembersList(getAllMembers(id));
            org.setFollowingList(getAllFollowedUsers(id));
            org.setPostsList(getAllUserPosts(id));
            org.setOwnedImgsList(getAllOwnedImages(id));
            org.setBannerImgId(getBannerImgId(id));

            resultLst.add(org);
        }

        rs.close();

        return resultLst;
    }

}



