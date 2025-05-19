package util;

import dao.PictureDAO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class InitDefaults {
    public static void main(String[] args) throws SQLException {
        String dirPath = String.format("%s/Resources/", System.getProperty("user.dir"));

        try{
            File defaultPfp = new File(String.format("%s%s",dirPath, "default_pfp.png"));
            BufferedImage pfpImg = ImageIO.read(defaultPfp);

            if(pfpImg != null){
                int defaultPfpId = PictureDAO.addNewImg(pfpImg, 0);
                System.out.println("Default PfP: id=" + defaultPfpId);
            }

            File defaultBanner = new File(String.format("%s%s", dirPath, "default_banner.png"));
            BufferedImage bannerImg = ImageIO.read(defaultBanner);

            if(bannerImg != null){
                int defaultBannerId = PictureDAO.addNewImg(bannerImg, 0);
                System.out.println("Default Banner: id=" + defaultBannerId);
            }
        }
        catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
