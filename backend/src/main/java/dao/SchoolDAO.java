package dao;

import model.ModelManager;
import model.School;
import util.DBConnection2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Data Access Object for the School model.
 * Provides methods to insert, update, retrieve, and delete school records.
 */
public class SchoolDAO {

    public static School getSchoolById(int id){return ModelManager.getSchool(id);}

    public static int getSchoolIdByName(String name){return ModelManager.getSchoolIdByName(name);}

    public static School getSchoolByName(String name){return ModelManager.getSchoolByName(name);}

    public static List<String> getAllSchoolsList(){return Arrays.asList(ModelManager.getAllSchoolsList());}

    public static List<School> getAllSchoolsObjList(){return ModelManager.getAllSchoolsObjects();}

    public static List<String> searchSchoolsByName(String schoolName) throws SQLException{
        String sql =
                "SELECT " +
                    "Schools.name AS school_name " +
                "FROM " +
                    "Schools " +
                "WHERE " +
                    "LOWER(Schools.name) LIKE ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            String qName = "%" + schoolName.toLowerCase() + "%";
            pstmt.setString(1, qName);

            ResultSet rs = pstmt.executeQuery();
            LinkedList<String> nameLL = new LinkedList<>();

            while(rs.next()){
                nameLL.add(rs.getString("school_name"));
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
