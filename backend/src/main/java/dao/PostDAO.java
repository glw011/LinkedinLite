package dao;

import model.Comment;
import model.Post;
import util.DBConnection2;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class PostDAO {


    /**
     * Inserts a new post created by a user(identified by userId) into Posts table.
     *
     * @param userId unique id of the user making the post
     * @param content the content of the post
     * @param tags LinkedList of the tags associated with the post
     * @return true if the post was successfully inserted; false otherwise.
     * @throws SQLException if a database error occurs.
     */
    public static boolean pushPost(int userId, String content, LinkedList<Integer> tags) throws SQLException {

        String sql = "INSERT INTO Posts (owner_id, content) VALUES (?, ?)";


        try (PreparedStatement stmt = DBConnection2.getPstmt(sql, new String[] {"post_id"})) {
            stmt.setInt(1, userId);
            stmt.setString(2, content);

            int postId = stmt.executeUpdate();

            if ((postId > 0)&&(!tags.isEmpty())) {
                String tagSql = "INSERT INTO Post_Tags (post_id, interest_id) VALUES (?, ?)";
                try(PreparedStatement pstmt = DBConnection2.getPstmt(tagSql)){
                    for(int tag : tags){
                        pstmt.setInt(1, postId);
                        pstmt.setInt(2, tag);
                    }
                }
            }
            return postId > 0;
        }
    }

    public static boolean addCommentToPost(int postId, int comtOwnerId, String content){
        // TODO: Needs implementation once Post_Comments table is added to DB (next sprint)
        return false;
    }


    /**
     * Retrieves all posts from the POST table.
     *
     * @return a List of Post objects.
     * @throws SQLException if a database error occurs.
     */
    public static LinkedList<Post> getAllPosts() throws SQLException {
        LinkedList<Post> posts = new LinkedList<>();
        String sql = "SELECT * FROM Posts";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Post post = new Post(
                        rs.getInt("post_id"),
                        rs.getInt("owner_id"),
                        rs.getString("content"),
                        rs.getTimestamp("post_date")
                );

                post.setTagList(getPostTagsById(post.getID()));
                post.setCommentsList(getPostCommentsById(post.getID()));

                posts.add(post);
            }
        }
        return posts;
    }

    /**
     * Updates an existing post in the POST table.
     *
     * @param postId unique id of the post being updated
     * @param content new content of post being updated
     * @return true if the update was successful, false otherwise.
     * @throws SQLException if a database error occurs.
     */
    public static boolean updatePost(int postId, String content) throws SQLException {
        String sql = "UPDATE Posts SET content = ? WHERE post_id = ?";

        try (PreparedStatement stmt = DBConnection2.getPstmt(sql)) {
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
     * @throws SQLException if a database error occurs.
     */
    public static boolean delPost(int postId) throws SQLException {
        String sql = "DELETE FROM ? WHERE post_id = ?";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql)) {
            pstmt.setInt(2, postId);

            // TODO: Need to delete all comments from Comments table
            //pstmt.setString(1, "Post_Comments");
            boolean comtSuccess = true;//pstmt.executeUpdate() > 0;

            // Not sure if this will work but want to test if a PreparedStatement can be changed and then queried again
            pstmt.setString(1, "Post_Tags");
            boolean tagSuccess = pstmt.executeUpdate() > 0;

            pstmt.setString(1, "Posts");

            if(!(comtSuccess&&tagSuccess)){
                String fail = (!tagSuccess) ? "tags" : "comments";
                if(!comtSuccess&&!tagSuccess) fail = "tags AND comments";
                String errmsg = String.format("Deletion of %s for post_id=%d FAILED", fail, postId);
                System.err.println(errmsg);
            }
            return pstmt.executeUpdate() > 0;
        }
    }

    public static LinkedList<Integer> getAllUserPosts(int userId) throws SQLException{
        LinkedList<Integer> postLst = new LinkedList<>();
        String sql = "SELECT post_id FROM Posts WHERE owner_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                postLst.add(rs.getInt("post_id"));
            }
        }
        return postLst;
    }

    public static LinkedList<Post> getAllUserPostObj(int userId) throws SQLException{
        LinkedList<Post> postLst = new LinkedList<>();
        String sql = "SELECT * FROM Posts WHERE owner_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Post post = new Post(
                        rs.getInt("post_id"),
                        rs.getInt("owner_id"),
                        rs.getString("content"),
                        rs.getTimestamp("post_date")
                );

                post.setCommentsList(getPostCommentsById(post.getID()));
                post.setTagList(getPostTagsById(post.getID()));
                postLst.add(post);
            }
        }
        return postLst;
    }

    public static Post getPostById(int postId) throws SQLException{
        Post post = null;
        String sql = "SELECT * FROM Posts WHERE post_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                post = new Post(
                        rs.getInt("post_id"),
                        rs.getInt("owner_id"),
                        rs.getString("content"),
                        rs.getTimestamp("post_date")
                );
            }
        }
        return post;
    }

    public static LinkedList<Integer> getPostTagsById(int postId) throws SQLException{
        LinkedList<Integer> tagLst;
        String sql = "SELECT interest_id FROM Post_Tags WHERE post_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();

            tagLst = new LinkedList<>();

            while(rs.next()){
                tagLst.add(rs.getInt("interest_id"));
            }
            rs.close();
        }
        return tagLst;
    }

    public static LinkedList<Post> getRecentPostsByInterest(int interestId) throws SQLException{
        LinkedList<Post> postLst = new LinkedList<>();
        String sql =
                "SELECT DISTINCT " +
                        "Post_Tags.interest_id as interest_id, " +
                        "Posts.post_id as post_id, " +
                        "Posts.owner_id as owner_id, " +
                        "Posts.content as content, " +
                        "Posts.img_id as img_id, " +
                        "Posts.post_date as post_date, " +
                    "FROM " +
                        "Post_Tags JOIN Posts ON Post_Tags.post_id = Posts.post_id " +
                    "ORDER BY " +
                        "Posts.post_date ASC " +
                    "WHERE " +
                        "Post_Tags.interest_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, interestId);

            ResultSet posts = pstmt.executeQuery();

            while(posts.next()){
                int postId = posts.getInt("post_id");
                Post currPost = new Post(postId, posts.getInt("owner_id"), posts.getString("content"), posts.getTimestamp("post_date"));

                int imgId = posts.getInt("img_id");
                if(imgId > 0){
                    try{
                        currPost.setPostImage(PictureDAO.getImgObj(imgId));
                    }
                    catch(IOException e){
                        System.err.println(e.getMessage());
                        e.printStackTrace(System.err);
                    }
                }

                currPost.setCommentsList(getPostCommentsById(postId));
                currPost.setTagList(getPostTagsById(postId));

                postLst.add(currPost);
            }
            posts.close();

            return postLst;
        }
    }

    public static LinkedList<Post> getRecentPostsByInterest(LinkedList<Integer> interestIdLst) throws SQLException{
        LinkedList<Post> postLst = new LinkedList<>();
        String sql =
                "SELECT DISTINCT " +
                        "Post_Tags.interest_id as interest_id, " +
                        "Posts.post_id as post_id, " +
                        "Posts.owner_id as owner_id, " +
                        "Posts.content as content, " +
                        "Posts.img_id as img_id, " +
                        "Posts.post_date as post_date, " +
                    "FROM " +
                        "Post_Tags JOIN Posts ON Post_Tags.post_id = Posts.post_id " +
                    "ORDER BY " +
                        "Posts.post_date ASC " +
                    "WHERE ";

        String condition = "Post_Tags.interest_id = ?";
        int n = interestIdLst.size();
        String currConditions = "";
        for(int i=0; i < n; i++){
            if(i == (n-1)) currConditions += condition;
            else{
                currConditions = String.join(currConditions, String.format("%s OR ", condition));
            }
        }

        sql = String.join(sql, currConditions);

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            int i = 1;
            for(int interestId : interestIdLst){
                pstmt.setInt(i, interestId);
            }

            ResultSet posts = pstmt.executeQuery();

            while(posts.next()){
                int postId = posts.getInt("post_id");
                Post currPost = new Post(postId, posts.getInt("owner_id"), posts.getString("content"), posts.getTimestamp("post_date"));

                int imgId = posts.getInt("img_id");
                if(imgId > 0){
                    try{
                        currPost.setPostImage(PictureDAO.getImgObj(imgId));
                    }
                    catch(IOException e){
                        System.err.println(e.getMessage());
                        e.printStackTrace(System.err);
                    }
                }

                currPost.setCommentsList(getPostCommentsById(postId));
                currPost.setTagList(getPostTagsById(postId));

                postLst.add(currPost);
            }
            posts.close();

            return postLst;
        }
    }

    public static LinkedList<Comment> getPostCommentsById(int postId) throws SQLException{
        LinkedList<Comment> comtLst;
        String sql = "SELECT * FROM Post_Comments WHERE post_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();

            comtLst = new LinkedList<>();

            while(rs.next()){
                Comment comt = new Comment(
                        rs.getInt("comment_id"),
                        rs.getInt("owner_id"),
                        rs.getInt("post_id"),
                        rs.getString("content"),
                        rs.getTimestamp("timestamp")
                );
                comtLst.add(comt);
            }
        }
        return null;
    }
}
