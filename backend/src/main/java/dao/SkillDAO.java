package dao;

import model.ModelManager;
import model.Skill;
import util.DBConnection2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SkillDAO {
    public static Skill getSkillById(int id){return ModelManager.getSkill(id);}

    public static int getSkillIdByName(String name){return ModelManager.getSkillIdByName(name);}

    public static Skill getSkillByName(String name){return ModelManager.getSkillByName(name);}

    public static List<String> getAllSkillsList(){return Arrays.asList(ModelManager.getAllSkillsList());}

    public static List<Skill> getAllSkillsObjList(){return ModelManager.getAllSkillsObjects();}

    public static List<String> searchSkillsByName(String skillName) throws SQLException{
        String sql =
                "SELECT " +
                    "skill_name " +
                "FROM " +
                    "Skills " +
                "WHERE " +
                    "LOWER(Skills.skill_name) LIKE ?";

        try(PreparedStatement pstmt = DBConnection2.getPstmt(sql)){
            String qName = "%" + skillName.toLowerCase() + "%";
            pstmt.setString(1, qName);

            ResultSet rs = pstmt.executeQuery();
            LinkedList<String> nameLL = new LinkedList<>();

            while(rs.next()){
                nameLL.add(rs.getString("skill_name"));
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
