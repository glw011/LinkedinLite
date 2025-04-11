package dao;

import model.Post;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {


    /**
     * Inserts a new post into the POST table.
     *
     * @param post A Post object containing the post details.
     * @return true if the post was successfully inserted; false otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean insertPost(Post post) throws Exception {

        String sql = "INSERT INTO POST (post_text, user_key, image_file_name, timestamp) VALUES (?, ?, ?, ?)";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // bvind values from the post object into the SQL params.
            stmt.setString(1, post.getPostText());
            stmt.setString(2, post.getUserKey());
            stmt.setString(3, post.getImageFileName());

            // set the timestamp; if null -> current time.
            if (post.getTimestamp() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(post.getTimestamp()));
            } else {
                stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            }

            //  INSERT statement.
            int affectedRows = stmt.executeUpdate();

            // ff the insertion affected at least one row -> success.
            if (affectedRows > 0) {
                // get auto-generated key if needed
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // set the generated postId in the Post model.
                        post.setPostId(generatedKeys.getInt(1));
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * Retrieves all posts from the POST table.
     *
     * @return a List of Post objects.
     * @throws Exception if a database error occurs.
     */
    public List<Post> getAllPosts() throws Exception {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM POST";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setPostText(rs.getString("post_text"));
                post.setUserKey(rs.getString("user_key"));
                post.setImageFileName(rs.getString("image_file_name"));

                //  SQL Timestamp to LocalDateTime if available
                Timestamp timestamp = rs.getTimestamp("timestamp");
                if (timestamp != null) {
                    post.setTimestamp(timestamp.toLocalDateTime());
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
    public boolean updatePost(Post post) throws Exception {
        String sql = "UPDATE POST SET post_text = ?, user_key = ?, image_file_name = ?, timestamp = ? WHERE post_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, post.getPostText());
            stmt.setString(2, post.getUserKey());
            stmt.setString(3, post.getImageFileName());

            // set  timestamp; null if timestamp null
            if (post.getTimestamp() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(post.getTimestamp()));
            } else {
                stmt.setTimestamp(4, null);
            }
            stmt.setInt(5, post.getPostId());

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
    public boolean deletePost(int postId) throws Exception {
        String sql = "DELETE FROM POST WHERE post_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postId);
            return stmt.executeUpdate() > 0;
        }
    }
}
