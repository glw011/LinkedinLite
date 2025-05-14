package dao;

import model.Picture;
import util.DBConnection2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class PictureDAO {

    public static int addNewImg(BufferedImage image, int ownerId) throws SQLException, IOException{
        String filename = String.format("%s.bmp", getNewUrl(ownerId));
        String path = String.format("%s/Disk/%s",System.getProperty("user.dir"), filename);

        String sql = "INSERT INTO Pictures (owner_id, img_url) VALUES (?, ?)";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql, new String[] {"img_id"})){
            pstmt.setInt(1, ownerId);
            pstmt.setString(2, path);

            int imgId = pstmt.executeUpdate();

            if(imgId > 0){
                try{
                    boolean writeSuccess = writeImgToDisk(image, path);

                    if(!writeSuccess){
                        return -1;
                    }

                    return imgId;
                }
                catch(Exception e){
                    e.printStackTrace(System.err);
                }
            }
        }
        return -1;
    }

    public static Picture getImgObj(int imgId) throws SQLException, IOException{
        BufferedImage image;
        Picture imgObj = null;

        String sql = "SELECT * FROM Pictures WHERE img_id = ?";
        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, imgId);

            ResultSet img = pstmt.executeQuery();

            if(img.next()){
                String path = img.getString("img_url");
                int ownerId = img.getInt("owner_id");

                image = readImgFromDisk(path);

                imgObj = new Picture(imgId, ownerId, path, image);
            }
        }
        return imgObj;
    }

    private static boolean writeImgToDisk(BufferedImage image, String path) throws IOException{
        File imgFile = new File(path);
        return ImageIO.write(image, "BMP", imgFile);
    }

    private static BufferedImage readImgFromDisk(String imgPath) throws IOException{
        BufferedImage image = null;
        File imgFile = new File(imgPath);

        try{
            image = ImageIO.read(imgFile);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
        return image;
    }

    private static String getNewUrl(int ownerId){
        return String.format("%d_%d", ownerId, Instant.now().toEpochMilli());
    }




}
