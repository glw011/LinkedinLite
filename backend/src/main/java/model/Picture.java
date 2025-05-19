package model;

import java.awt.image.BufferedImage;

public class Picture{
    private int imgId;
    private int ownerId;
    private String path;
    private BufferedImage image;

    public Picture(int id, int ownerId, String path, BufferedImage image){
        this.imgId = id;
        this.ownerId = ownerId;
        this.path = path;
        this.image = image;
    }

    public int getID(){return (this.imgId > 0) ? this.imgId:-1;}

    public int getOwnerId(){return (this.ownerId >= 0) ? this.ownerId:-1;}

    public String getPath(){return (this.path != null) ? this.path:"null";}

    public BufferedImage getImage(){return this.image;}

}
