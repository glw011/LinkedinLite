package model;

import java.sql.Timestamp;
import java.util.LinkedList;

public class Post {
    private int postId;
    private int ownerId;
    private String content;
    private Timestamp timestamp;
    private String userKey;
    private String imageFileName;
    private LinkedList<Integer> tagList = new LinkedList<>();
    private LinkedList<Comment> commentsList = new LinkedList<>();

    // no-arg for servlet: Post post = new Post();
    public Post() {}

    // used by DAO: new Post(rs.getInt("post_id"), ..., rs.getTimestamp("post_date"))
    public Post(int postId, int ownerId, String content, Timestamp timestamp) {
        this.postId = postId;
        this.ownerId = ownerId;
        this.content = content;
        this.timestamp = timestamp;
    }

    // --- getters / setters ---
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    // in model/Post.java

    /** Alias for getPostId(), so existing DAOs that call post.getID() compile. */
    public int getID() {
        return getPostId();
    }

    /** Alias for getTimestamp(), so you can do Post::getPostDate. */
    public java.sql.Timestamp getPostDate() {
        return getTimestamp();
    }


    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    // your service calls getPostText()/setPostText()
    public String getPostText() { return content; }
    public void setPostText(String content) { this.content = content; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

    // your servlet calls setUserKey()/getUserKey()
    public String getUserKey() { return userKey; }
    public void setUserKey(String userKey) { this.userKey = userKey; }

    public String getImageFileName() { return imageFileName; }
    public void setImageFileName(String imageFileName) { this.imageFileName = imageFileName; }

    public LinkedList<Integer> getTagList() { return tagList; }
    public void setTagList(LinkedList<Integer> tagList) { this.tagList = tagList; }

    public LinkedList<Comment> getCommentsList() { return commentsList; }
    public void setCommentsList(LinkedList<Comment> commentsList) { this.commentsList = commentsList; }
}
