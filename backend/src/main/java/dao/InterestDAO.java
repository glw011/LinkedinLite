package dao;

import model.Interest;
import model.ModelManager;
import util.DBConnection2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InterestDAO {
    public static Interest getInterestById(int id){return ModelManager.getInterest(id);}

    public static int getInterestIdByName(String name){return ModelManager.getInterestIdByName(name);}

    public static Interest getInterestByName(String name){return ModelManager.getInterestByName(name);}

    public static List<String> getAllInterestsList(){return Arrays.asList(ModelManager.getAllInterestsList());}

    public static List<Interest> getAllInterestsObjList(){return ModelManager.getAllInterestsObjects();}

    public static List<String> searchInterestsByName(String interestName) throws SQLException{
        String sql =
                "SELECT " +
                    "interest_name " +
                "FROM " +
                    "Interests " +
                "WHERE " +
                    "LOWER(Interests.interest_name) LIKE ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            String qName = "%" + interestName.toLowerCase() + "%";
            pstmt.setString(1, qName);

            ResultSet rs = pstmt.executeQuery();
            LinkedList<String> nameLL = new LinkedList<>();

            while(rs.next()){
                nameLL.add(rs.getString("interest_name"));
            }

            if(!nameLL.isEmpty()){
                String[] nameArr = new String[nameLL.size()];

                int i = 0;
                for(String name : nameLL){nameArr[i++] = name;}

                return Arrays.asList(nameArr);
            }
        }
        return null;
    }
}
