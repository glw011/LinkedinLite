package dao;

import model.ModelManager;
import model.Picture;
import model.School;
import model.UserType;
import util.DBConnection2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.imageio.ImageIO;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;

public class UserDAO {

    // PBKDF2 stuff
    private static final String  PBKDF2_ALGO    = "PBKDF2WithHmacSHA256";
    private static final int     SALT_BYTES     = 16;         // 128-bit salt
    private static final int     ITERATIONS     = 100_000;
    private static final int     DERIVED_KEY_BITS = 256;

    /**
     * Authenticates a user based on email and **raw** password.
     * Returns a token string if successful, or null if not.
     */
    public String authUser(String email, String rawPass) throws SQLException {
        String sql = "SELECT user_id, pass_hash FROM User_Verify WHERE email = ?";

        try (Connection conn = DBConnection2.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;  // user nonexistent
                }
                int userId = rs.getInt("user_id");
                String stored = rs.getString("pass_hash");      // format: salt:hash

                // split and decode
                String[] parts = stored.split(":", 2);
                byte[] salt = Base64.getDecoder().decode(parts[0]);
                byte[] hash = Base64.getDecoder().decode(parts[1]);

                // recompute PBKDF2 on rawPass using same salt/params
                byte[] testHash = pbkdf2(rawPass.toCharArray(), salt);

                if (!MessageDigest.isEqual(hash, testHash)) {
                    return null;  // password incorrecty
                }

                // success
                return "token_" + userId + "_" + System.currentTimeMillis();
            }
        }
    }

    /**
     * Adds a new user by hashing the raw password with PBKDF2 and storing salt:hash.
     */
    public static boolean addUser(String email, String rawPass, UserType userType) throws SQLException {
        // generate salt
        byte[] salt = new byte[SALT_BYTES];
        try {
            SecureRandom.getInstanceStrong().nextBytes(salt);
        } catch (Exception e) {
            throw new RuntimeException("SecureRandom not available", e);
        }

        // derive PBKDF2 hash
        byte[] hash = pbkdf2(rawPass.toCharArray(), salt);

        // encode salt and hash as Base64
        String stored = Base64.getEncoder().encodeToString(salt)
                + ":" +
                Base64.getEncoder().encodeToString(hash);

        String sql = "INSERT INTO User_Verify (email, pass_hash, type) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = DBConnection2.getPstmt(sql, new String[]{"user_id"})) {
            stmt.setString(1, email);
            stmt.setString(2, stored);
            stmt.setString(3, userType.getStr());

            int newId = stmt.executeUpdate();
            boolean success = newId > 0;
            if (success) {
                ModelManager.mapNewUser(email, newId, userType);
            }
            return success;

        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
            e.printStackTrace();
            return false;
        }
    }

    // PBKDF2 Helper
    private static byte[] pbkdf2(char[] pass, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(pass, salt, ITERATIONS, DERIVED_KEY_BITS);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGO);
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("PBKDF2 error", e);
        }
    }

    /**
     * Set the bio for the profile of a user(identified by userId) by updating the 'bio' column of the Users table
     *
     * @param userId the unique user_id of the user whose bio is being set
     * @param bio the String containing the bio which a user has set
     * @return true if the Users table was successfully updated, else false
     * @throws SQLException if DB error occurred
     */
    public static boolean setBio(int userId, String bio) throws SQLException {
        String sql = "UPDATE Users SET bio = ? WHERE user_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setString(1, bio);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Gets the bio associated with a user(identified by userId) from the Users table
     *
     * @param userId unique user_id of the user whose bio is being retrieved
     * @return the string consisting of the bio associated with the user
     * @throws SQLException if DB error occurred
     */
    public static String getBio(int userId) throws SQLException{
        String sql = "SELECT bio FROM Users WHERE user_id = ?";
        String bio;

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);
            ResultSet bioRs = pstmt.executeQuery();

            bio = bioRs.getString("bio");

            bioRs.close();
        }
        return bio;
    }

    /**
     * Adds a post containing only text and no image which was created by a user(identified by userId) to the Posts table
     *
     * @param userId Unique user_id for the user creating the new post
     * @param postText String consisting of the text portion of the post being added
     * @param tagList LinkedList of integer vals where each val is the interest_id of the tag assigned to the post
     * @return Positive non-zero int of the unique post_id assigned to the newly created post or -1 if insertion failed
     * @throws SQLException if DB error occurred
     */
    public static int addPost(int userId, String postText, LinkedList<Integer> tagList) throws SQLException {
        return PostDAO.pushPost(userId, postText, tagList);
    }


    /**
     * Adds a post containing both text and an image which was created by a user(identified by userId) to the Posts table
     *
     * @param userId Unique user_id for the user creating the new post
     * @param postText String consisting of the text portion of the post being added
     * @param tagList LinkedList of integer vals where each val is the interest_id of the tag assigned to the post
     * @param postImg BufferedImage file object being uploaded with the post
     * @return Positive non-zero int of the unique post_id assigned to the newly created post or -1 if insertion failed
     * @throws SQLException if DB error occurred
     */
    public static int addPost(int userId, String postText, LinkedList<Integer> tagList, BufferedImage postImg) throws SQLException {
        return PostDAO.pushPostWithImg(userId, postText, tagList, postImg);
    }

    /**
     * Deletes a post(identified by postId) by removing its entry from the Posts table
     *
     * @param postId unique post_id for the post being deleted
     * @return true if the entry was successfully removed from Posts table
     * @throws SQLException if DB error occurred
     */
    public static boolean delPost(int postId) throws SQLException {
        String tagsSql = "DELETE FROM Post_Tags WHERE post_id = ?";
        String postSql = "DELETE FROM POST WHERE post_id = ?";
        try(PreparedStatement postPstmt = DBConnection2.getPstmt(postSql);
            PreparedStatement tagsPstmt = DBConnection2.getPstmt(tagsSql))
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
     * Gets all posts a user(identified by userId) has ever created
     *
     * @param userId unique user_id of the user whose posts are being retrieved
     * @return LinkedList of integer vals where each val is a unique post_id for a post created by userId
     * @throws SQLException if DB error occurred
     */
    public static LinkedList<Integer> getAllUserPosts(int userId) throws SQLException{
        return PostDAO.getAllUserPosts(userId);
    }

    public static LinkedList<Integer> getAllRecentPosts(int userId) throws SQLException{
        return PostDAO.getAllRecentUserPosts(userId);
    }

    /**
     * Inserts an entry into User_Interests table representing that a user(identified by userId) has indicated a
     * particular interest(identified by interestId)
     *
     * @param userId unique user_id of the user who has indicated the interest
     * @param interestId unique interest_id for the interest which the user indicated
     * @return true if the entry successfully inserted into User_Interests, else false
     * @throws SQLException if DB error occurred
     */
    public static boolean addInterest(int userId, int interestId) throws SQLException {
        String sql = "INSERT INTO User_Interests (user_id, interest_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, interestId);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Removes the entry from the User_Interests table representing a user(identified by userId) is no longer
     * has a particular interest(indicated by interestId)
     *
     * @param userId unique user_id for user who no longer has the interest
     * @param interestId unique interest_id for the interest userId no longer has
     * @return true if the entry is successfully deleted from User_Interests table, else false
     * @throws SQLException if DB error occurred
     */
    public static boolean delInterest(int userId, int interestId) throws SQLException {
        String sql = "DELETE FROM User_Interests WHERE user_id = ? AND interest_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, interestId);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Gets the list of all interests which a user(identified userId) has indicated
     *
     * @param userId unique user_id for the user whose list of interests is being retrieved
     * @return LinkedList of integer vals where each val is the unique interest_id that user has indicated
     * @throws SQLException if DB error occurred
     */
    public static LinkedList<Integer> getAllInterests(int userId) throws SQLException{
        String sql = "SELECT interest_id FROM User_Interests WHERE entity_id = ?";
        LinkedList<Integer> intrstLst;

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
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
     * Inserts tuple pair into Follows table so the user(identified by myUserId) follows the user(identified by userIdToFollow)
     *
     * @param myUserId unique user_id of the user wishing to follow userIdToFollow
     * @param userIdToFollow unique user_id of the user being followed by myUserId
     * @return true if tuple pair indicating myUserId is following userIdToFollow was successfully inserted into Follows
     *         table, else false
     * @throws SQLException if DB error occurred
     */
    public static boolean followUser(int myUserId, int userIdToFollow) throws SQLException {
        String sql = "INSERT INTO Follows (user_id, following_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, myUserId);
            pstmt.setInt(2, userIdToFollow);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Removes the tuple pair from Follows table for the user(identified by userId) following the user(identified by userIdToUnfollow)
     *
     * @param myUserId unique user_id of the user who no longer wishes to follow the user userIdToUnfollow
     * @param userIdToUnfollow unique user_id of the user who will no longer be followed by userId
     * @return true if the tuple was successfully deleted from Follows table, else false
     * @throws SQLException if DB error occurred
     */
    public static boolean unfollowUser(int myUserId, int userIdToUnfollow) throws SQLException {
        String sql = "DELETE FROM Follows WHERE entity_id = ? AND following_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(1, myUserId);
            pstmt.setInt(2, userIdToUnfollow);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Gets list of all users that are being followed by a user(identified by userId)
     *
     * @param userId unique user_id of the user whose list is being retrieved
     * @return LinkedList of integer vals where each int is the unique user_id of a user the given user is following
     * @throws SQLException if DB error occurred
     */
    public static LinkedList<Integer> getAllFollowedUsers(int userId) throws SQLException{
        String sql = "SELECT following_id FROM Follows WHERE user_id = ?";
        LinkedList<Integer> followsLst;

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
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
     * Gets list of all users that are following a user(identified by userId)
     *
     * @param userId unique user_id of the user whose list is being retrieved
     * @return LinkedList of integer vals where each int is the unique user_id of a user following the given user
     * @throws SQLException if DB error occurred
     */
    public static LinkedList<Integer> getAllUsersFollowing(int userId) throws SQLException{
        String sql = "SELECT user_id FROM Follows WHERE following_id = ?";
        LinkedList<Integer> followrLst;

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);
            followrLst = new LinkedList<>();

            ResultSet followrs = pstmt.executeQuery();

            while(followrs.next()){
                followrLst.add(followrs.getInt("user_id"));
            }
        }
        return followrLst;
    }

    /**
     * Updates the school_id entry for the user(identified by userId) in the Users table with the id of the new school
     *
     * @param userId unique user_id for the user whose school_id is being updated
     * @param schoolId unique school_id for the school the user is setting as their new school
     * @return true if the update to the Users table was successful, else false
     * @throws SQLException if DB error occurred
     */
    public static boolean setSchool(int userId, int schoolId) throws SQLException{
        String sql = "UPDATE Users SET school_id = ? WHERE user_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql);){
            pstmt.setInt(1, schoolId);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Gets the school id of a user(identified by userId) from the Users table which is used to return the School
     * object from ModelManager
     *
     * @param userId unique user_id of the user whose school is being retrieved
     * @return School object containing the data for that school
     * @throws SQLException if DB error occurred
     */
    public static School getSchool(int userId) throws SQLException{
        String sql = "SELECT school_id FROM Users WHERE user_id = ?";
        int schoolId;
        School school = null;

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                schoolId = rs.getInt("school_id");
                school = ModelManager.getSchool(schoolId);
            }
            rs.close();
        }
        return school;
    }

    public static LinkedList<Integer> getAllOwnedImages(int userId) throws SQLException{
        String sql = "SELECT img_id FROM Pictures WHERE owner_id = ?";
        LinkedList<Integer> imgLst = new LinkedList<>();

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);
            ResultSet imgIds = pstmt.executeQuery();
            while(imgIds.next()){
                imgLst.add(imgIds.getInt("img_id"));
            }
            if(!imgLst.isEmpty()) return imgLst;
        }
        return null;
    }

    /**
     * gets profile image for a user(identified by userid) and creates Picture obj for the image
     *
     * @param userId unique user_id for the user whose profile img is being retrieved
     * @return Picture object containing the image data (including location of the image)
     * @throws SQLException if DB error occurred
     */
    public static BufferedImage getProfileImg(int userId) throws SQLException, IOException{
        String sql = "SELECT DISTINCT Pictures.img_url as img_url FROM Users JOIN Pictures ON Users.pfp_id = Pictures.img_id WHERE Users.user_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                String imgUrl = rs.getString("img_url");
                File imgFile = new File(imgUrl);
                if((imgFile.exists())&&(imgFile.canRead())){
                    return ImageIO.read(imgFile);
                }
            }
        }
        return null;
    }

    public static boolean setProfileImg(int userId, int imgId) throws SQLException{
        String sql = "UPDATE Users SET pfp_id = ? WHERE user_id = ?";
        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, imgId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public static int getProfileImgId(int userId) throws SQLException{
        String sql = "SELECT pfp_id FROM Users WHERE user_id = ?";
        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                int imgId = rs.getInt("pfp_id");
                if(imgId > 0) return imgId;
            }
        }
        return -1;
    }

    public static Picture getProfileImgObj(int userId) throws SQLException{
        int pfpId = getProfileImgId(userId);
        if(pfpId > 0){
            try{
                return PictureDAO.getImgObj(pfpId);
            }

            catch(IOException e){
                System.err.println(e.getMessage());
                e.printStackTrace(System.err);
            }
        }
        return null;
    }
}




