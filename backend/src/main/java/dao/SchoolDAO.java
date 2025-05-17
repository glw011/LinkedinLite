package dao;

import model.ModelManager;
import model.School;

import java.util.Arrays;
import java.util.List;

/**
 * Data Access Object for the School model.
 * Provides methods to insert, update, retrieve, and delete school records.
 */
public class SchoolDAO {

    public static School getSchoolById(int id){return ModelManager.getSchool(id);}

    public static int getSchoolIdByName(String name){return ModelManager.getSchoolIdByName(name);}

    public School getSchoolByName(String name){return ModelManager.getSchoolByName(name);}

    public List<String> getAllSchoolsList(){return Arrays.asList(ModelManager.getAllSchoolsList());}

    public List<School> getAllSchoolsObjList(){return ModelManager.getAllSchoolsObjects();}
}
