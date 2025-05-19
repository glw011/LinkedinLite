package dao;

import model.ModelManager;
import model.Picture;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        ModelManager.initModelManager();



        System.out.println(PictureDAO.getImgPath(6));
        System.out.println(PictureDAO.getImgPath(7));

        Picture pfpObj = PictureDAO.getImgObj(6);
        Picture bannerObj = PictureDAO.getImgObj(7);

        System.out.println("pfp type: " + pfpObj.getPath());
        System.out.println("banner type: " + bannerObj.getPath());
    }
}
