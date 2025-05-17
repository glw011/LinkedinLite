package dao;

import model.FoS;
import model.ModelManager;
import model.Skill;

import java.util.Arrays;
import java.util.List;

public class SkillDAO {
    public static Skill getSkillById(int id){
        return ModelManager.getSkill(id);
    }

    public static int getSkillIdByName(String name){return ModelManager.getSkillIdByName(name);}

    public static Skill getSkillByName(String name){
        return ModelManager.getSkillByName(name);
    }

    public static List<String> getAllSkillsList(){return Arrays.asList(ModelManager.getAllSkillsList());}

    public static List<Skill> getAllSkillsObjList(){return ModelManager.getAllSkillsObjects();}
}
