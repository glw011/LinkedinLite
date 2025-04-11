package model;

import java.time.LocalDateTime;

public class Post {

    private int postId;              // unique identifier for post (primary key)
    private String postText;         //  main content
    private String userKey;          // identifier for user who created  post
    private String imageFileName;    // nmame of image file associated with post (optional)
    private LocalDateTime timestamp; //  date/time post was created

    // Default constructor
    public Post() {
    }

    // Parameterized constructor
    public Post(int postId, String postText, String userKey, String imageFileName, LocalDateTime timestamp) {
        this.postId = postId;
        this.postText = postText;
        this.userKey = userKey;
        this.imageFileName = imageFileName;
        this.timestamp = timestamp;
    }

    // getters and setters

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postText='" + postText + '\'' +
                ", userKey='" + userKey + '\'' +
                ", imageFileName='" + imageFileName + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
