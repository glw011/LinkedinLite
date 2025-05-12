package model;

import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private int ownerId;
    private int postId;
    private String content;
    private Timestamp timestamp;


    public Comment(int commentId, int ownerId, int postId, String content, Timestamp timestamp){
        this.commentId = commentId;
        this.ownerId = ownerId;
        this.postId = postId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getCommentId(){return this.commentId;}

    public int getOwnerId(){return this.ownerId;}

    public int getPostId(){return this.postId;}

    public String getContent(){return this.content;}

    public Timestamp getTimestamp(){return this.timestamp;}
}
