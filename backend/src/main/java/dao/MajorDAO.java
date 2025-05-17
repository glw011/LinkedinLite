package dao;

import model.Major;
import model.ModelManager;

import java.util.Arrays;
import java.util.List;

public class MajorDAO {
    public static Major getMajorById(int id){return ModelManager.getMajor(id);}

    public static int getMajorIdByName(String name){return ModelManager.getMajorIdByName(name);}

    public static Major getMajorByName(String name){return ModelManager.getMajorByName(name);}

    public static List<String> getAllMajorsList(){return Arrays.asList(ModelManager.getAllMajorsList());}

    public static List<Major> getAllMajorsObjList(){return ModelManager.getAllMajorsObjects();}
}
