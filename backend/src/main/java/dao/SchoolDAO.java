package dao;

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

    /**
     * Retrieves a School by its ID.
     *
     * @param id the school's unique identifier.
     * @return the School object if found, or null otherwise.
     * @throws Exception if a database error occurs.
     */
    public School getSchoolById(int id) throws Exception {
        String sql = "SELECT * FROM SCHOOL WHERE school_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    School school = new School();
                    school.setSchoolId(rs.getInt("school_id"));
                    school.setSchoolName(rs.getString("school_name"));
                    school.setCity(rs.getString("city"));
                    school.setState(rs.getString("state"));
                    school.setCountry(rs.getString("country"));
                    return school;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all School records from the database.
     *
     * @return a List of School objects.
     * @throws Exception if a database error occurs.
     */
    public List<School> getAllSchools() throws Exception {
        List<School> schools = new ArrayList<>();
        String sql = "SELECT * FROM SCHOOL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                School school = new School();
                school.setSchoolId(rs.getInt("school_id"));
                school.setSchoolName(rs.getString("school_name"));
                school.setCity(rs.getString("city"));
                school.setState(rs.getString("state"));
                school.setCountry(rs.getString("country"));
                schools.add(school);
            }
        }
        return schools;
    }

    /**
     * Inserts a new School record into the database.
     *
     * @param school the School object to insert.
     * @return true if the insert was successful; false otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean insertSchool(School school) throws Exception {
        String sql = "INSERT INTO SCHOOL (school_name, city, state, country) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, school.getSchoolName());
            stmt.setString(2, school.getCity());
            stmt.setString(3, school.getState());
            stmt.setString(4, school.getCountry());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                // get generated key (school_id) and set in the School object.
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        school.setSchoolId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Updates an existing School record.
     *
     * @param school the School object with updated data. The schoolId must be valid.
     * @return true if the update was successful; false otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean updateSchool(School school) throws Exception {
        String sql = "UPDATE SCHOOL SET school_name = ?, city = ?, state = ?, country = ? WHERE school_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, school.getSchoolName());
            stmt.setString(2, school.getCity());
            stmt.setString(3, school.getState());
            stmt.setString(4, school.getCountry());
            stmt.setInt(5, school.getSchoolId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a School record from the database by its ID.
     *
     * @param id the unique identifier of the School to delete.
     * @return true if the deletion was successful; false otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean deleteSchool(int id) throws Exception {
        String sql = "DELETE FROM SCHOOL WHERE school_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
