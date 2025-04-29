package model;

import java.util.HashMap;
import java.sql.Timestamp;

public class Post{
    private int postId;
    private User owner;
    private String content;
    private Picture postImg;
    private Timestamp postDate;
    private HashMap<Integer, Interest> tags;
    private HashMap<Integer, Comment> comments;

    public Post(int id, User owner, String content, Picture img, Timestamp date){
        this.postId = id;
        this.owner = owner;
        this.content = content;
        this.postImg = img;
        this.postDate = date;

        this.tags = new HashMap<>();
        this.comments = new HashMap<>();
    }

    public int getID(){return this.postId;}

    // TODO: Add remaining getter/setter methods
}
