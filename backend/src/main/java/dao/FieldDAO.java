package dao;

import model.FoS;
import model.ModelManager;

import java.util.Arrays;
import java.util.List;

public class FieldDAO {
    public static FoS getFieldById(int id){
        return ModelManager.getFoS(id);
    }

    public static int getFieldIdByName(String name){return ModelManager.getFoSIdByName(name);}

    public static FoS getFieldByName(String name){
        return ModelManager.getFoSByName(name);
    }

    public static List<String> getAllFieldsList(){return Arrays.asList(ModelManager.getAllFoSList());}

    public static List<FoS> getAllFieldsObjList(){return ModelManager.getAllFoSObjects();}
}
