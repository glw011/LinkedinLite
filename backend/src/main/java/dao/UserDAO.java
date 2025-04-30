package dao;

import java.sql.*;
import model.UserType;
import model.ModelManager;
import dao.PostDAO;
import util.DBConnection;
import util.DBConnection2;
import java.util.*;

public class UserDAO {

    /**
     * Authenticates a user based on email and hashed password.
     * Returns a token string ("hkey") if successful, or null if not.
     * temp token using id and timestamp
     */
    public String authUser(String email, String hashedPass) throws SQLException {
        // TODO: Should we just simplify this to return user_id to frontend after authorize and they keep track of and pass
        //  it back to us anytime user wants to take any type of user action?? Lets us pretend to ensure no one is changing someone
        //  else's data (assuming they can manage it throughout each user session) and makes it easy to update any necessary rows?
        String sql = "SELECT user_id FROM User_Verify WHERE email = ? AND pass_hash = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, hashedPass);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    // PLACEHOLDER
                    return "token_" + userId + "_" + System.currentTimeMillis();
                } else {
                    return null;
                }
            }
        }
    }


    public static boolean addUser(String email, String hashedPass, UserType userType) throws SQLException {

        String sql = "INSERT INTO User_Verify (email, pass_hash, type) VALUES ( ?, ?, ?)";
        Connection conn = DBConnection.getConnection();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setString(2, hashedPass);
            stmt.setString(3, userType.getStr());

            boolean success = (stmt.executeUpdate() > 0);
            if(success){
                String idQry = String.format("SELECT user_id FROM User_Verify WHERE email = %s", email);
                Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery(idQry);

                while(results.next()){
                    int currId = results.getInt("user_id");
                    ModelManager.mapNewUser(email, currId, userType);
                }
                results.close();
            }
            return success;
        } catch(SQLException e) {
            System.err.println(e.getErrorCode());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    /**
     * set bio for an authenticated user.
     */
    public boolean setBio(int userId, String bio) throws SQLException {
        String sql = "UPDATE Users SET bio = ? WHERE user_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)) {
            pstmt.setString(1, bio);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public String getBio(int userId) throws SQLException{
        String sql = "SELECT bio FROM Users WHERE user_id = ?";
        String bio;

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)){
            pstmt.setInt(1, userId);
            ResultSet bioRs = pstmt.executeQuery();

            bio = bioRs.getString("bio");

            bioRs.close();
        }
        return bio;
    }

    /**
     * adds post
     * assume token contains the stdmtID and POST table exists
     * columns ==  post_owner, post_txt, and timestamp (with NOW() used for the current timestamp).
     */
    public boolean addPost(int userId, String postText, LinkedList<Integer> tagList) throws SQLException {
        // TODO: Handle how image uploads are handled when uploaded with a post
        String sqlQry = "SELECT NOW()";
        Statement stmt = DBConnection2.getStatement();
        ResultSet currTime = stmt.executeQuery(sqlQry);
        currTime.next();
        Timestamp timestamp = currTime.getTimestamp("NOW()");

        String sql = "INSERT INTO Posts (owner_id, content, post_date) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, postText);
            pstmt.setTimestamp(3, timestamp);
            boolean postSuccess = pstmt.executeUpdate() > 0;

            if(postSuccess){
                sqlQry = "SELECT * FROM Posts WHERE owner_id = ? AND post_date = ?";
                PreparedStatement nxtPstmt = DBConnection2.getPrepStatement(sqlQry);
                nxtPstmt.setInt(1, userId);
                nxtPstmt.setTimestamp(2, timestamp);

                ResultSet post = nxtPstmt.executeQuery();
                post.next();
                int postId = post.getInt("post_id");
                post.close();

                // TODO: Create PostManager obj which holds hashmap containing Post objects made in last 5-7 days as vals, post_id as key??
                String tagSql = "INSERT INTO Post_Tags (post_id, interest_id) VALUES (?, ?)";
                PreparedStatement tagPstmt = DBConnection2.getPrepStatement(tagSql);
                for (Integer tag : tagList) {
                    tagPstmt.setInt(1, postId);
                    tagPstmt.setInt(2, tag);
                    tagPstmt.executeUpdate();
                }
            }
            return postSuccess;
        }
    }

    /**
     * deletes given p[ost by ID
     */
    public boolean delPost(int postId) throws SQLException {
        String tagsSql = "DELETE FROM Post_Tags WHERE post_id = ?";
        String postSql = "DELETE FROM POST WHERE post_id = ?";
        try(PreparedStatement postPstmt = DBConnection2.getPrepStatement(postSql);
            PreparedStatement tagsPstmt = DBConnection2.getPrepStatement(tagsSql))
        {
            postPstmt.setInt(1, postId);
            tagsPstmt.setInt(1, postId);

            return (postPstmt.executeUpdate() > 0)&&(tagsPstmt.executeUpdate() > 0);
        }
        catch(SQLException e){
            System.err.println(e.getErrorCode());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        return false;
    }
    /**
     * Receives user_id int as arg and returns LinkedList of all postIds owned by given userId to caller
     */
    public LinkedList<Integer> getAllUserPosts(int userId) throws SQLException{
        // TODO: Needs to be implemented
        return null;
    }

    /**
     * Receives user_id int as arg and returns LinkedList of all postIds owned by given userId having a timestamp
     * that is within past 7 days (can be more or less??) to caller
     */
    public LinkedList<Integer> getAllRecentPosts(int userId) throws SQLException{
        // TODO: Needs to be implemented
        return null;
    }

    /**
     * adds an interest to user's profile.
     */
    public boolean addInterest(int userId, int interestId) throws SQLException {
        String sql = "INSERT INTO User_Interests (user_id, interest_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, interestId);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * removes an interest from user's profile.
     */
    public boolean delInterest(int userId, int interestId) throws SQLException {
        String sql = "DELETE FROM User_Interests WHERE user_id = ? AND interest_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, interestId);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Receives user_id int as arg and returns LinkedList of all interestIds associated with given userId to caller
     */
    public LinkedList<Integer> getAllInterests(int userId) throws SQLException{
        String sql = "SELECT interest_id FROM User_Interests WHERE user_id = ?";
        LinkedList<Integer> intrstLst;

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)){
            pstmt.setInt(1, userId);
            ResultSet intrsts = pstmt.executeQuery();
            intrstLst = new LinkedList<>();

            while(intrsts.next()){
                intrstLst.add(intrsts.getInt("interest_id"));
            }

            intrsts.close();
        }
        return intrstLst;
    }

    /**
     * sets user identified by myUserId to follow user identified by userIdToFollow
     * inserts new row into Follows table with columns user_id=myUserId and following_id=userIdToFollow
     */
    public boolean followUser(int myUserId, int userIdToFollow) throws SQLException {
        String sql = "INSERT INTO Follows (user_id, following_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)) {
            pstmt.setInt(1, myUserId);
            pstmt.setInt(2, userIdToFollow);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * sets user identified by myUserId to unfollow user identified by userIdToUnfollow
     * Deletes row in Follows table where columns user_id=myUserId and following_id=userIdToUnfollow
     */
    public boolean unfollowUser(int myUserId, int userIdToUnfollow) throws SQLException {
        String sql = "DELETE FROM Follows WHERE entity_id = ? AND following_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)) {
            pstmt.setInt(1, myUserId);
            pstmt.setInt(2, userIdToUnfollow);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Receives user_id int as arg and returns to caller a LinkedList of all ids being followed by passed user_id
     */
    public LinkedList<Integer> getAllFollowedUsers(int userId) throws SQLException{
        String sql = "SELECT following_id FROM Follows WHERE user_id = ?";
        LinkedList<Integer> followsLst;

        try (PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)) {
            pstmt.setInt(1, userId);
            followsLst = new LinkedList<>();

            ResultSet follows = pstmt.executeQuery();

            while(follows.next()){
                followsLst.add(follows.getInt("following_id"));
            }
        }
        return followsLst;
    }

    /**
     * Receives user_id int as arg and returns to caller a LinkedList of all ids that are following passed user_id
     */
    public LinkedList<Integer> getAllUsersFollowing(int userId) throws SQLException{
        String sql = "SELECT user_id FROM Follows WHERE following_id = ?";
        LinkedList<Integer> followrLst;

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)){
            pstmt.setInt(1, userId);
            followrLst = new LinkedList<>();

            ResultSet followrs = pstmt.executeQuery();

            while(followrs.next()){
                followrLst.add(followrs.getInt("user_id"));
            }
        }
        return followrLst;
    }

    public boolean setSchool(int userId, int schoolId) throws SQLException{
        String sql = "UPDATE Users SET school_id = ? WHERE user_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql);){
            pstmt.setInt(1, schoolId);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public Integer getSchool(int userId) throws SQLException{
        String sql = "SELECT school_id FROM Users WHERE user_id = ?";
        int schoolId;

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)){
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            schoolId = rs.getInt("school_id");

            rs.close();
        }
        return schoolId;
    }

    public LinkedList<Integer> getAllOwnedImages(int userId){
        // TODO: Needs to be implemented
        return null;
    }

    public boolean setProfileImg(int userId, int imgId){
        // TODO: Needs to be implemented
        return false;
    }

    public Integer getProfileImg(int userId) throws SQLException{
        String sql = "SELECT school_id FROM Users WHERE user_id = ?";
        int pfpId;

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)){
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            pfpId = rs.getInt("pfp_id");

            rs.close();
        }
        return pfpId;
    }

}




