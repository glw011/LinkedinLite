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

    /**
     * Adds a new school to the database.
     * @param school the School object containing the new school information.
     * @return true if the school was successfully inserted; false otherwise.
     * @throws Exception if the school data is invalid or a database error occurs.
     */
    public boolean addSchool(School school) throws Exception {
        if (school == null || school.getSchoolName() == null || school.getSchoolName().trim().isEmpty()) {
            throw new IllegalArgumentException("School data is invalid.");
        }
        return schoolDAO.insertSchool(school);
    }

    /**
     * Updates an existing school record.
     * @param school the School object containing updated information. Must have a valid schoolId.
     * @return true if the update is successful; false otherwise.
     * @throws Exception if the provided data is invalid or a database error occurs.
     */
    public boolean updateSchool(School school) throws Exception {
        if (school == null || school.getSchoolId() <= 0) {
            throw new IllegalArgumentException("Invalid school data.");
        }
        return schoolDAO.updateSchool(school);
    }

    /**
     * Deletes a school record by its ID.
     * @param id the unique identifier of the school to delete
     * @return true if deletion is successful; false otherwise.
     * @throws Exception if the provided id is invalid or a database error occurs.
     */
    public boolean deleteSchool(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid school ID: " + id);
        }
        return schoolDAO.deleteSchool(id);
    }
}
