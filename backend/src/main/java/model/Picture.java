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

    public int getID(){return this.imgId;}

    public int getOwnerId(){return this.ownerId;}

    public String getPath(){return this.path;}

    public BufferedImage getImage(){return this.image;}

}
