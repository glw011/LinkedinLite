package dao;

import model.Major;
import model.ModelManager;
import util.DBConnection2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MajorDAO {
    public static Major getMajorById(int id){return ModelManager.getMajor(id);}

    public static int getMajorIdByName(String name){return ModelManager.getMajorIdByName(name);}

    public static Major getMajorByName(String name){return ModelManager.getMajorByName(name);}

    public static List<String> getAllMajorsList(){return Arrays.asList(ModelManager.getAllMajorsList());}

    public static List<Major> getAllMajorsObjList(){return ModelManager.getAllMajorsObjects();}

    public static List<String> searchMajorsByName(String mjrName) throws SQLException{
        String sql =
                "SELECT " +
                    "major_name " +
                "FROM " +
                    "Majors " +
                "WHERE " +
                    "LOWER(Majors.major_name) LIKE ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            String qName = "%" + mjrName.toLowerCase() + "%";
            pstmt.setString(1, qName);

            ResultSet rs = pstmt.executeQuery();
            LinkedList<String> nameLL = new LinkedList<>();

            while(rs.next()){
                nameLL.add(rs.getString("major_name"));
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
