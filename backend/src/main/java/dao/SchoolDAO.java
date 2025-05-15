package dao;

import model.ModelManager;
import model.School;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data Access Object for the School model.
 * Provides methods to insert, update, retrieve, and delete school records.
 */
public class SchoolDAO {

    public School getSchoolById(int id) throws SQLException {
        return ModelManager.getSchool(id);
    }

    public String[] getAllSchoolList() throws SQLException{
        return ModelManager.getAllSchoolList();
    }

    public boolean deleteSchool(int id) {
        return false;
    }

    public boolean updateSchool(School school) {
        return false;
    }

    public boolean insertSchool(School school) {
        return false;
    }

    public List<School> getAllSchools() {
        return Collections.emptyList();
    }
}
