// src/main/java/service/StudentService.java
package service;

import dao.StudentDAO;
import model.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class StudentService {
    private static final StudentDAO studentDAO = new StudentDAO();

    /**
     * Returns all students, sorted by last name.
     */
    public static List<Student> getAllStudents() throws SQLException {
        HashMap<Integer, Student> map = StudentDAO.getAllStudents();
        List<Student> list = new ArrayList<>(map.values());

        // Null-safe sort on last name
        list.sort(Comparator.comparing(
                Student::getLname,
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        ));

        return list;
    }

    /**
     * Returns a single student by ID.
     */
    public Student getStudentById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        Student s = StudentDAO.getStudentById(id);
        if (s == null) {
            throw new SQLException("Student not found");
        }
        return s;
    }

    /**
     * Creates a new student.
     */
    public int addStudent(
            String fname,
            String email,
            String hashedPass,
            String schoolName,
            String majorName
    ) throws SQLException {
        if (fname == null || fname.isBlank()
                || email == null || email.isBlank()
                || hashedPass == null || hashedPass.isBlank()) {
            throw new IllegalArgumentException("Missing required fields");
        }

        int stdId = StudentDAO.addStdnt(fname, email, hashedPass, schoolName, majorName);
        try{
            if(stdId > 0){
                return stdId;
            }
            else{
                throw new SQLException("Student registration failed for name = "+fname);
            }

        }
        catch(SQLException e){
            throw new SQLException("Student registration failed for name = "+fname);
        }
    }

    /**
     * Updates an existing student.
     */
    public boolean updateStudent(Student s) throws SQLException {
        if (s == null || s.getID() <= 0) {
            throw new IllegalArgumentException("Missing or invalid student id");
        }
        return studentDAO.updateStudent(s);
    }

    /**
     * Deletes a student (and related data).
     */
    public boolean deleteStudent(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return StudentDAO.deleteStudent(id);
    }
}
