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

public class OrgDAO extends UserDAO{

    public static boolean addOrg(String name, String email, String hashedPass, String schoolName) throws SQLException {
        if(addUser(email, hashedPass, UserType.ORG)){
            int userId = ModelManager.getUserId(email);
            int schoolId = ModelManager.getSchoolIdByName(schoolName);

            String usrSql = "UPDATE Users SET school_id = ? WHERE user_id = ?";

            String orgSql = "UPDATE Orgs SET name = ? WHERE org_id = ?";

            try (PreparedStatement usrPstmt = DBConnection2.getPstmt(usrSql);
                 PreparedStatement orgPstmt = DBConnection2.getPstmt(orgSql))
            {
                usrPstmt.setInt(1, schoolId);
                usrPstmt.setInt(2, userId);

                orgPstmt.setString(1, name);
                orgPstmt.setInt(2, userId);

                return (usrPstmt.executeUpdate() > 0)&&(orgPstmt.executeUpdate() > 0);
            }

        }
        return false;
    }

    public static Org getOrgById(int orgId) throws SQLException {
        Org orgObj;

        String sqlStr =
            "SELECT DISTINCT" +
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

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sqlStr)){
            pstmt.setInt(1, orgId);

            ResultSet results = pstmt.executeQuery();
            orgObj = new Org(
                    results.getInt("id"),
                    results.getString("email"),
                    results.getString("name"),
                    ModelManager.getSchool(results.getInt("school_id"))
            );

            orgObj.setBio(results.getString("bio"));
            orgObj.setProfilePic(results.getInt("pfp_id"));

            orgObj.setInterestList(getAllInterests(orgObj.getID()));
            orgObj.setMembersList(getAllMembers(orgObj.getID()));
            orgObj.setFollowingList(getAllFollowedUsers(orgObj.getID()));
            orgObj.setPostsList(getAllUserPosts(orgObj.getID()));
            orgObj.setOwnedImgsList(getAllOwnedImages(orgObj.getID()));
        }
        return orgObj;
    }

    // TODO: Same as getAllStudents; Need to implement system to allow for more complex queries without needing 100x functions
    public static HashMap<Integer, Org> getAllOrgs() throws SQLException{
        HashMap<Integer, Org> orgMap;

        String sqlStr =
            "SELECT DISTINCT" +
                "Orgs.org_id as id, " +
                "Orgs.name as name, " +
                "User_Verify.email as email, " +
                "Users.bio as bio, " +
                "Users.pfp_id as pfp_id, " +
                "Users.school_id as school_id " +
            "FROM " +
                "User_Verify JOIN Users ON User_Verify.user_id = Users.user_id " +
                "JOIN Orgs ON Orgs.org_id = Users.user_id ";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sqlStr)){
            orgMap = new HashMap<>();
            ResultSet results = pstmt.executeQuery();

            while(results.next()){
                int currId = results.getInt("id");
                Org orgObj = new Org(
                        results.getInt("id"),
                        results.getString("email"),
                        results.getString("name"),
                        ModelManager.getSchool(results.getInt("school_id"))
                );

                orgObj.setBio(results.getString("bio"));
                orgObj.setProfilePic(results.getInt("pfp_id"));

                orgObj.setInterestList(getAllInterests(orgObj.getID()));
                orgObj.setMembersList(getAllMembers(orgObj.getID()));
                orgObj.setFollowingList(getAllFollowedUsers(orgObj.getID()));
                orgObj.setPostsList(getAllUserPosts(orgObj.getID()));
                orgObj.setOwnedImgsList(getAllOwnedImages(orgObj.getID()));

                orgMap.putIfAbsent(currId, orgObj);
            }
            results.close();
        }
        return orgMap;
    }

    public static LinkedList<Integer> getAllMembers(int orgId) throws SQLException{
        String sql = "SELECT student_id FROM Org_Membership WHERE org_id = ?";
        LinkedList<Integer> memberLst;

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, orgId);
            ResultSet members = pstmt.executeQuery();
            memberLst = new LinkedList<>();

            while(members.next()){
                memberLst.add(members.getInt("student_id"));
            }

            members.close();
        }
        return memberLst;
    }

    // TODO: Need to add a membership approval table to store students who have applied to join an org but not yet accepted
    //  also would need 3 additional functions like approveMember(stdId), rejectMember(stdId), getPendingMembers(orgId)

    public static boolean addMember(int orgId, int stdId) throws SQLException{
        String sql = "INSERT INTO Org_Membership (student_id, org_id) VALUES (?, ?)";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, stdId);
            pstmt.setInt(2, orgId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public static boolean delMember(int orgId, int stdId) throws SQLException{
        String sql = "DELETE FROM Org_Membership WHERE student_id = ? AND org_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, stdId);
            pstmt.setInt(2, orgId);

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Inserts an entry containing a student_id, org_id into Pending_Invites table representing a student
     * being invited to join an org and awaiting that student's approval
     * @param orgId Unique org_id val of Org inviting the Student
     * @param stdId Unique student_id val of the Student being invited to join Org
     * @return true if successful insertion, else false
     * @throws SQLException if DB error occurs
     */
    public static boolean inviteMember(int orgId, int stdId) throws SQLException{
        // TODO: Needs implementation (DB table needs column indicating whether invite or request)
        return false;
    }

    /**
     * Gets all students who have pending requests for membership to the Org identified by an org_id
     * @param orgId Unique org_id of the Org seeking all membership requests
     * @return HashMap containing unique student_id vals of Students awaiting approval as keys, with their Student Obj as vals
     * @throws SQLException if DB error occurs
     */
    public static HashMap<Integer, Student> getAllPendingRequests(int orgId) throws SQLException{
        // TODO: Needs implementation
        return null;
    }

    /**
     * Removes tuple entry in Pending_Requests and inserts into Org_Membership once approved for membership by Org
     * @param orgId Unique org_id val of the Org being joined
     * @param stdId Unique student_id val of the student joining the Org
     * @return true if removal from Pending_Requests and insertion into Org_Membership are both successful, else false
     * @throws SQLException if DB error occurs
     */
    public static boolean approveMemberRequest(int orgId, int stdId) throws SQLException{
        // TODO: Needs implementation
        return false;
    }

    /**
     * Removes tuple entry in Pending_Requests and does not insert into Org_Membership when denied by Org
     * @param orgId Unique org_id val of the Org whose membership was requested
     * @param stdId Unique student_id val of the student who requested joining the Org
     * @return true if removal from Pending_Requests successful, else false
     * @throws SQLException if DB error occurs
     */
    public static boolean denyMemberRequest(int orgId, int stdId) throws SQLException{
        // TODO: Needs implementation
        return false;
    }

}
