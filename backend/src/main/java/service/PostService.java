package service;

import dao.PostDAO;
import model.Post;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PostService {
    /**
     * Creates a new post.
     * @param userId  ID of the poster
     * @param content non‐empty text
     * @param tags    list of interest IDs
     * @return true if inserted
     * @throws SQLException on DB error
     */
    public boolean addPost(int userId, String content, LinkedList<Integer> tags) throws SQLException {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Post content cannot be empty");
        }
        return PostDAO.pushPost(userId, content, tags) > 0;
    }

    /**
     * Fetches all posts, sorted by date descending.
     */
    public List<Post> getAllPosts() throws SQLException {
        List<Post> posts = PostDAO.getAllPosts();
        posts.sort(Comparator.comparing(Post::getPostDate).reversed());
        return posts;
    }

    /**
     * Updates a post’s content.
     */
    public boolean updatePost(int postId, String content) throws SQLException {
        if (postId <= 0) {
            throw new IllegalArgumentException("Invalid post ID");
        }
        return PostDAO.updatePost(postId, content);
    }

    /**
     * Deletes a post.
     */
    public boolean deletePost(int postId) throws SQLException {
        if (postId <= 0) {
            throw new IllegalArgumentException("Invalid post ID");
        }
        return PostDAO.delPost(postId);
    }
}
