package service;

import dao.SchoolDAO;
import model.School;

import java.util.List;

public class SchoolService {
    private final SchoolDAO schoolDAO = new SchoolDAO();

    public School getSchoolById(int id) throws Exception {
        if (id <= 0) throw new IllegalArgumentException("Invalid school ID");
        School school = schoolDAO.getSchoolById(id);
        if (school == null) throw new Exception("Not found");
        return school;
    }

    public List<School> getAllSchools() throws Exception {
        List<School> list = schoolDAO.getAllSchools();
        if (list.isEmpty()) throw new Exception("No schools");
        return list;
    }

    public boolean addSchool(School s) throws Exception {
        if (s.getSchoolName()==null||s.getSchoolName().trim().isEmpty())
            throw new IllegalArgumentException("Name required");
        return schoolDAO.insertSchool(s);
    }

    public boolean updateSchool(School s) throws Exception {
        if (s.getSchoolId() <= 0) throw new IllegalArgumentException("Invalid ID");
        return schoolDAO.updateSchool(s);
    }

    public boolean deleteSchool(int id) throws Exception {
        if (id <= 0) throw new IllegalArgumentException("Invalid ID");
        return schoolDAO.deleteSchool(id);
    }
}
