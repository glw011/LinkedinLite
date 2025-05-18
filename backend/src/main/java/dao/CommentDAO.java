package dao;

import model.Comment;
import util.DBConnection2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class CommentDAO {
    public static int pushComment(int postId, int ownerId, String content) throws SQLException {
        String sql = "INSERT INTO Post_Comments (post_id, owner_id, content) VALUES (?, ?, ?)";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql, new String[] {"comment_id"})){
            pstmt.setInt(1, postId);
            pstmt.setInt(2, ownerId);
            pstmt.setString(3, content);

            return pstmt.executeUpdate();
        }
    }

    public static boolean updateComment(int commentId, String newContent) throws SQLException{
        String sql = "UPDATE Post_Comments SET content = ? WHERE comment_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setString(1, newContent);
            pstmt.setInt(2, commentId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public static boolean delComment(int commentId) throws SQLException{
        String sql = "DELETE FROM Post_Comments WHERE comment_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, commentId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public static Comment getCommentObj(int commentId) throws SQLException{
        String sql = "SELECT * FROM Post_Comments WHERE comment_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, commentId);

            ResultSet comment = pstmt.executeQuery();

            if(comment.next()){
                return new Comment(
                        commentId,
                        comment.getInt("owner_id"),
                        comment.getInt("post_id"),
                        comment.getString("content"),
                        comment.getTimestamp("comment_date")
                );
            }
        }
        return null;
    }

    public static Comment getCommentObj(int postId, int ownerId, String content) throws SQLException{
        String sql = "SELECT * FROM Post_Comments WHERE post_id = ? AND owner_id = ? AND content = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, postId);
            pstmt.setInt(2, ownerId);
            pstmt.setString(3, content);

            ResultSet comment = pstmt.executeQuery();

            if(comment.next()){
                return new Comment(
                        comment.getInt("comment_id"),
                        comment.getInt("owner_id"),
                        comment.getInt("post_id"),
                        comment.getString("content"),
                        comment.getTimestamp("comment_date")
                );
            }
        }
        return null;
    }

    public static LinkedList<Comment> getPostCommentList(int postId) throws SQLException{
        String sql = "SELECT * FROM Post_Comments ORDER BY comment_date ASC WHERE post_id = ?";

        LinkedList<Comment> commtLst = new LinkedList<>();

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, postId);

            ResultSet comments = pstmt.executeQuery();

            while(comments.next()){
                Comment currComment = new Comment(
                        comments.getInt("comment_id"),
                        comments.getInt("owner_id"),
                        comments.getInt("post_id"),
                        comments.getString("content"),
                        comments.getTimestamp("comment_date")
                );

                commtLst.add(currComment);
            }

            if(!commtLst.isEmpty()) return commtLst;
        }
        return null;
    }
}
