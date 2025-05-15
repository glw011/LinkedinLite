package service;

import dao.SchoolDAO;
import model.School;

import java.sql.SQLException;
import java.util.*;

public class SchoolService {
    private final SchoolDAO schoolDAO = new SchoolDAO();

    /**
     * Retrieves a School by its ID after validating the input.
     * @param id the unique identifier of the school
     * @return the School object if found
     * @throws IllegalArgumentException if id <= 0
     * @throws NotFoundException if no school with that ID exists
     * @throws SchoolServiceException on database errors
     */
    public School getSchoolById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid school ID: " + id);
        }
       try {
            School school = schoolDAO.getSchoolById(id);
            if (school == null) {
                throw new NotFoundException("School with ID " + id + " not found.");
            }
            return school;
        } catch (Exception e) {
            throw new SchoolServiceException("Error fetching school with ID " + id, e);
        }
    }
    

    /**
     * Retrieves all schools from the database.
     * @return a List of School objects.
     * @throws NotFoundException if no schools exist
     * @throws SchoolServiceException on database errors
     */
    public List<String> getAllSchools() {
        
        try {
            List<String> schools = schoolDAO.getAllSchoolsList();
            if (schools == null || schools.isEmpty()) {
                throw new NotFoundException("No schools available.");
            }        
            return schools;
        } catch (Exception e) {
            throw new SchoolServiceException("Error fetching all schools", e);
        }
     
    }

    
    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) { super(message); }
    }
    
    
    public static class SchoolServiceException extends RuntimeException {
        public SchoolServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
