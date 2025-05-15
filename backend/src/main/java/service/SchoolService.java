package service;

import dao.SchoolDAO;
import model.School;

import java.util.List;

public class SchoolService {
    private final SchoolDAO schoolDAO = new SchoolDAO();

    /**
     * Retrieves a School by its ID after validating the input.
     * @param id the unique identifier of the school
     * @return the School object if found
     * @throws Exception if the id is invalid or the school cannot be found
     */
    public School getSchoolById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid school ID: " + id);
        }
        School school = schoolDAO.getSchoolById(id);
        if (school == null) {
            throw new Exception("School with ID " + id + " not found.");
        }
        return school;
    }

    /**
     * Retrieves all schools from the database.
     * @return a List of School objects.
     * @throws Exception if no schools are available or a database error occurs.
     */
    public List<School> getAllSchools() throws Exception {
        List<School> schools = schoolDAO.getAllSchools();
        if (schools == null || schools.isEmpty()) {
            throw new Exception("No schools available.");
        }
        return schools;
    }

}
