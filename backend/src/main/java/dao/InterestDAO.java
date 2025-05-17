package dao;

import model.Interest;
import model.ModelManager;

import java.util.Arrays;
import java.util.List;

public class InterestDAO {
    public static Interest getInterestById(int id){
        return ModelManager.getInterest(id);
    }

    public static int getInterestIdByName(String name){return ModelManager.getInterestIdByName(name);}

    public static Interest getInterestByName(String name){
        return ModelManager.getInterestByName(name);
    }

    public static List<String> getAllInterestsList(){return Arrays.asList(ModelManager.getAllInterestsList());}

    public static List<Interest> getAllInterestsObjList(){return ModelManager.getAllInterestsObjects();}
}
