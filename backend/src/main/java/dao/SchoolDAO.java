package dao;

import model.ModelManager;
import model.School;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
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
}
