package model;

public class Picture{
    private int imgId;
    private User owner;
    private Date uploadDate;
    private String url;

    public Picture(int id, User owner, String url, Date date){
        this.imgId = id;
        this.owner = owner;
        this.url = url;
        this.uploadDate = date;
    }

    public int getID(){return this.imgId;}
}
