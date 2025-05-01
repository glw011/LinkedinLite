package service;

import dao.PostDAO;
import model.Post;
import java.util.List;

public class PostService {
    private final PostDAO postDAO = new PostDAO();

    /**
     * Adds a new post after validating the post content.
     *
     * @param post the Post object to insert
     * @return true if the post is successfully inserted; false otherwise.
     * @throws Exception if validation fails or a database error occurs.
     */
    public boolean addPost(Post post) throws Exception {
        // validate post text is provided.
        if (post.getPostText() == null || post.getPostText().trim().isEmpty()) {
            throw new IllegalArgumentException("Post text cannot be empty");
        }
        // validate that the user key is set (opt)
        if (post.getUserKey() == null || post.getUserKey().trim().isEmpty()) {
            throw new IllegalArgumentException("User identifier is required");
        }
        return postDAO.pushPost(post);
    }

    /**
     * Retrieves all posts and sorts them by timestamp in descending order.
     *
     * @return a list of Post objects sorted by most recent.
     * @throws Exception if a database error occurs.
     */
    public List<Post> getAllPosts() throws Exception {
        List<Post> posts = postDAO.getAllPosts();
        if (posts == null) {
            throw new Exception("No posts available");
        }
        //sort posts most recent first.
        posts.sort((p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp()));
        return posts;
    }

    /**
     * Updates an existing post.
     */
    public boolean updatePost(Post post) throws Exception {
        if (post.getPostId() <= 0) {
            throw new IllegalArgumentException("Post id must be valid for update");
        }
        return postDAO.updatePost(post);
    }

    /**
     * Deletes a post by its id.
     */
    public boolean deletePost(int postId) throws Exception {
        if (postId <= 0) {
            throw new IllegalArgumentException("Invalid post id");
        }
        return postDAO.deletePost(postId);
    }
}

