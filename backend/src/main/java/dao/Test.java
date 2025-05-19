package dao;

import model.ModelManager;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        ModelManager.initModelManager();

        if(StudentDAO.addStdnt("Garrett", "glw011@latech.edu", "CSC403", "Louisiana Tech University", "Computer Science")){
            int stdId = ModelManager.getUserId("glw011@latech.edu");

            int pfpId = PictureDAO.getProfileImgId(stdId);
            int bannerId = PictureDAO.getBannerImgId(stdId);

            System.out.println("pfp img_id = "+pfpId);
            System.out.println("banner img_id = "+bannerId);
        }
    }
}
