package service;

import dao.CommentDAO;
import dao.PictureDAO;
import dao.PostDAO;
import model.Comment;
import model.Picture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PostService {

    
    public PostService() {}

    /**
     * text‐only post
     * @return the new post_id
     */
    public int createPost(int userId, String content, List<Integer> tags) {
        try {
            return PostDAO.pushPost(userId, content, new LinkedList<Integer>(tags));
        } catch (SQLException e) {
            throw new PostServiceException("Failed to create post", e);
        }
    }

    /**
     * post with an image: first store image, then post
     * @return the new post_id
     */
    public int createPostWithImage(int userId,
                               String content,
                               List<Integer> tags,
                               BufferedImage image) {

        Objects.requireNonNull(image, "image must not be null");
        try {

            //  DAO handles image storage and post creation 
            int result = PostDAO.pushPostWithImg(
                userId,
                content,
                new LinkedList<>(tags),
                image
            );

        if (result <= 0) {
            throw new PostServiceException(
                "Image post creation failed (DAO returned " + result + ")"
            );
        }
        return result;

        } catch (SQLException e) {
            throw new PostServiceException("Error creating image post", e);
        }
    }

    /**
     * Delete post and tags by id
     */
    public boolean deletePost(int postId) {
        try {
            return PostDAO.delPost(postId);
        } catch (SQLException e) {
            throw new PostServiceException("Failed to delete post", e);
        }
    }

    /**
     * Get all posts for a given user by id
     */
    public List<Integer> getAllUserPosts(int userId) {
        try {
            return PostDAO.getAllUserPosts(userId);
        } catch (SQLException e) {
            throw new PostServiceException("Failed to fetch user posts", e);
        }
    }

    /**
     * Add a comment to a post
     * @return the new comment_id
     */
    public int addComment(int postId, int commenterId, String commentText) {
        try {
            return PostDAO.addCommentToPost(postId, commenterId, commentText);
        } catch (SQLException e) {
            throw new PostServiceException("Failed to add comment", e);
        }
    }

    /**
     * Get all comments for a post
     */
    public List<Comment> getCommentsForPost(int postId) {
        try {
            return CommentDAO.getPostCommentList(postId);
        } catch (SQLException e) {
            throw new PostServiceException("Failed to fetch comments", e);
        }
    }

    /**
     * Delete a comment by id
     */
    public boolean deleteComment(int commentId) {
        try {
            return CommentDAO.delComment(commentId);
        } catch (SQLException e) {
            throw new PostServiceException("Failed to delete comment", e);
        }
    }

    /**
     * Fetch a stored image by id
     */
    public Picture getPicture(int imgId) {
        try {
            Picture pic = PictureDAO.getImgObj(imgId);
            if (pic == null) {
                throw new PostServiceException("No image found with id " + imgId);
            }
            return pic;
        } catch (SQLException e) {
            throw new PostServiceException("Failed to fetch image", e);
        }
    }

    /**  
     * Unchecked exception for any PostService errors  
     */
    public static class PostServiceException extends RuntimeException {
        public PostServiceException(String msg) {
            super(msg);
        }
        public PostServiceException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
