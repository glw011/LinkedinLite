package dao;

import model.ModelManager;
import model.Org;
import model.UserType;
import util.DBConnection2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class OrgDAO extends UserDAO{

    public boolean addOrg(String name, String email, String hashedPass, String schoolName) throws SQLException {
        if(addUser(email, hashedPass, UserType.ORG)){
            int userId = ModelManager.getUserId(email);
            int schoolId = ModelManager.getSchoolIdByName(schoolName);

            String usrSql = "UPDATE Users SET school_id = ? WHERE user_id = ?";

            String orgSql = "UPDATE Orgs SET name = ? WHERE org_id = ?";

            try (PreparedStatement usrPstmt = DBConnection2.getPrepStatement(usrSql);
                 PreparedStatement orgPstmt = DBConnection2.getPrepStatement(orgSql))
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

    public Org getOrgById(int orgId) throws SQLException {
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

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sqlStr)){
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
    public HashMap<Integer, Org> getAllOrgs() throws SQLException{
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

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sqlStr)){
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

    public LinkedList<Integer> getAllMembers(int orgId) throws SQLException{
        String sql = "SELECT student_id FROM Org_Membership WHERE org_id = ?";
        LinkedList<Integer> memberLst;

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)){
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

}
