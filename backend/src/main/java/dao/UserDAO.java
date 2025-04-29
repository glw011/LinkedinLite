package dao;

import java.sql.*;
import model.User;
import model.UserType;
import model.ModelManager;
import model.Picture;
import model.Post;
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

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);

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
     * assumes token is in temp placeholder format as above".
     */
    public boolean setBio(int userId, String bio) throws SQLException {
        String sql = "UPDATE Users SET bio = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bio);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        }
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
    public boolean delPost(int postId) throws Exception {

        String sql = "DELETE FROM POST WHERE post_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postId);

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

    public User getUserById(int id) throws SQLException {
        return ModelManager.getUser(id);
    }

    public boolean setSchool(int userId, int schoolId) throws SQLException{
        String sql = "UPDATE Users SET school_id = ? WHERE user_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql);){
            pstmt.setInt(1, schoolId);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;
        }
    }

}




