package model;

import java.sql.Timestamp;
import java.util.LinkedList;

public class Post{
    private int postId;
    private int owner;
    private String content;
    private Picture postImg;
    private Timestamp postDate;
    private LinkedList<Integer> tags;
    private LinkedList<Integer> comments;

    public Post(int id, int ownerId, String content, Timestamp date){
        this.postId = id;
        this.owner = ownerId;
        this.content = content;
        this.postDate = date;

        this.tags = new LinkedList<>();
        this.comments = new LinkedList<>();
    }

    public int getID(){return this.postId;}
    public int getOwnerID(){return this.owner;}

    public String getContent(){return this.content;}
    public void setContent(String newContent){this.content = newContent;}

    public Timestamp getPostDate(){return this.postDate;}

    public LinkedList<Integer> getTags(){return this.tags;}
    public void setTagList(LinkedList<Integer> tagList){
        this.tags = tagList;
    }

    public LinkedList<Integer> getComments(){return this.comments;}
    public void setCommentsList(LinkedList<Integer> comments){
        this.comments = comments;
    }

    // TODO: Add image getter/setter methods
}
