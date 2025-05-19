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

    public static int addNewImg(BufferedImage image, int ownerId) throws SQLException, IOException {
        String filename = String.format("%s.png", getNewUrl(ownerId));
        String path = String.format("%s/Disk/%s", System.getProperty("user.dir"), filename);

        String sql = "INSERT INTO Pictures (owner_id, img_url) VALUES (?, ?)";

        try (PreparedStatement pstmt = DBConnection2.getPstmt(sql, new String[]{"img_id"})) {
            pstmt.setInt(1, ownerId);
            pstmt.setString(2, path);

            if (pstmt.executeUpdate() > 0){
                ResultSet img = pstmt.getGeneratedKeys();
                if (img.next()){
                    boolean writeSuccess = writeImgToDisk(image, path);
                    if (writeSuccess){
                        return img.getInt(1);
                    }
                    System.err.println("Failed to write image to disk.");
                }
            }
        }
        System.err.println("Failed to add image to database");
        return -1;
    }

    public static Picture getImgObj(int imgId) throws SQLException{
        BufferedImage image;
        Picture imgObj;

        String sql = "SELECT * FROM Pictures WHERE img_id = ?";
        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, imgId);

            ResultSet img = pstmt.executeQuery();

            if(img.next()){
                String path = img.getString("img_url");
                int ownerId = img.getInt("owner_id");

                try{
                    image = readImgFromDisk(path);
                    imgObj = new Picture(imgId, ownerId, path, image);

                    return imgObj;
                }
                catch(IOException e){
                    System.err.println(e.getMessage());
                    e.printStackTrace(System.err);
                }
            }
        }
        return null;
    }

    public static String getImgUrl(int imgId) throws SQLException{
        String sql = "SELECT img_url FROM Pictures WHERE img_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, imgId);

            ResultSet img = pstmt.executeQuery();

            if(img.next()){
                String imgUrl = img.getString("img_url");

                File imgChk = new File(imgUrl);

                if(imgChk.exists()) return imgUrl;
            }
        }
        return null;
    }

    public static int getProfileImgId(int userId) throws SQLException{
        String sql = "SELECT pfp_id FROM Users WHERE user_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                int imgId = rs.getInt("pfp_id");

                return (imgId > 0) ? imgId:-1;
            }
        }
        return -1;
    }

    public static int getBannerImgId(int userId) throws SQLException{
        String sql = "Select img_id FROM User_Banners WHERE user_id = ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                int imgId = rs.getInt("img_id");

                return (imgId > 0) ? imgId:-1;
            }
        }
        return -1;
    }

    public static BufferedImage getBufferedImg(int imgId) throws SQLException, IOException{
        String imgUrl = getImgUrl(imgId);

        if(imgUrl != null){
            return readImgFromDisk(imgUrl);
        }
        return null;
    }

    public static BufferedImage getBufferedImg(String imgPath) throws SQLException, IOException{
        return readImgFromDisk(imgPath);
    }

    private static boolean writeImgToDisk(BufferedImage image, String path) throws IOException{
        File imgFile = new File(path);
        return ImageIO.write(image, "png", imgFile);
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
