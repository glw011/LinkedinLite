package dao;

import model.Post;
import util.DBConnection;
import util.DBConnection2;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class PostDAO {


    /**
     * Inserts a new post into the POST table.
     *
     * @param post A Post object containing the post details.
     * @return true if the post was successfully inserted; false otherwise.
     * @throws SQLException if a database error occurs.
     */
    public static boolean insertPost(int userId, String content, LinkedList<Integer> tags) throws SQLException {

        String sql = "INSERT INTO Posts (owner_id, content) VALUES (?, ?)";


        try (PreparedStatement stmt = DBConnection2.getPrepStatement(sql, new String[] {"post_id"})) {
            stmt.setInt(1, userId);
            stmt.setString(2, content);

            int postId = stmt.executeUpdate();

            if ((postId > 0)&&(!tags.isEmpty())) {
                String tagSql = "INSERT INTO Post_Tags (post_id, interest_id) VALUES (?, ?)";
                try(PreparedStatement pstmt = DBConnection2.getPrepStatement(tagSql)){
                    for(int tag : tags){
                        pstmt.setInt(1, postId);
                        pstmt.setInt(2, tag);
                    }
                }
            }
            return postId > 0;
        }
    }


    /**
     * Retrieves all posts from the POST table.
     *
     * @return a List of Post objects.
     * @throws Exception if a database error occurs.
     */
    public static LinkedList<Post> getAllPosts() throws SQLException {
        LinkedList<Post> posts = new LinkedList<>();
        String sql = "SELECT * FROM Posts";

        String tagSql = "";
        String comSql = "";

        try (PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Post post = new Post(
                        rs.getInt("post_id"),
                        rs.getInt("owner_id"),
                        rs.getString("content"),
                        rs.getTimestamp("post_date")
                );

                try(PreparedStatement comStmt = DBConnection2.getPrepStatement(comSql);
                    PreparedStatement tagStmt = DBConnection2.getPrepStatement(tagSql)){
                    // TODO: FINISH
                }

                posts.add(post);
            }
        }
        return posts;
    }

    /**
     * Updates an existing post in the POST table.
     *
     * @param post a Post object containing the new data. The post must have a valid postId.
     * @return true if the update was successful, false otherwise.
     * @throws Exception if a database error occurs.
     */
    public static boolean updatePost(int postId, String content) throws SQLException {
        String sql = "UPDATE Posts SET content = ? WHERE post_id = ?";

        try (PreparedStatement stmt = DBConnection2.getPrepStatement(sql)) {
            stmt.setString(1, content);
            stmt.setInt(2, postId);

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a post from the POST table based on its post_id.
     *
     * @param postId the identifier of the post to delete.
     * @return true if the deletion was successful, false otherwise.
     * @throws Exception if a database error occurs.
     */
    public static boolean delPost(int postId) throws SQLException {
        String sql = "DELETE FROM Posts WHERE post_id = ?";

        try (PreparedStatement stmt = DBConnection2.getPrepStatement(sql)) {
            stmt.setInt(1, postId);
            return stmt.executeUpdate() > 0;
        }
    }

    public static LinkedList<Integer> getAllUserPosts(int userId) throws SQLException{
        LinkedList<Integer> postLst = new LinkedList<>();
        String sql = "SELECT post_id FROM Posts WHERE owner_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPrepStatement(sql)){
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                postLst.add(rs.getInt("post_id"));
            }
        }
        return postLst;
    }
}
