package service;

import dao.StudentDAO;
import model.Student;

import java.sql.SQLException;
import java.util.*;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();

    /**
     * Returns all students as a sorted List.
     */
    public List<Student> getAllStudents() throws SQLException {
        HashMap<Integer, Student> map = studentDAO.getAllStudents(); // :contentReference[oaicite:9]{index=9}
        List<Student> list = new ArrayList<>(map.values());
        list.sort(Comparator.comparing(Student::getLname, String.CASE_INSENSITIVE_ORDER));
        return list;
    }

    /**
     * Returns a single student.
     */
    public Student getStudentById(int id) throws SQLException {
        if (id <= 0) throw new IllegalArgumentException("Invalid ID");
        Student s = studentDAO.getStudentById(id);    // :contentReference[oaicite:10]{index=10}
        if (s == null) throw new SQLException("Not found");
        return s;
    }

    /**
     * Adds a new student.
     */
    public boolean addStudent(
            String fname,
            String email,
            String hashedPass,
            String schoolName,
            String majorName
    ) throws SQLException {
        if (fname == null || email == null || hashedPass == null) {
            throw new IllegalArgumentException("Missing required fields");
        }
        return studentDAO.addStdnt(fname, email, hashedPass, schoolName, majorName); // :contentReference[oaicite:11]{index=11}
    }

    /**
     * Deletes a student.
     */
    public boolean deleteStudent(int id) throws SQLException {
        if (id <= 0) throw new IllegalArgumentException("Invalid ID");
        return studentDAO.deleteStudent(id);         // :contentReference[oaicite:12]{index=12}
    }
    public boolean updateStudent(Student s) throws SQLException {
        if (s.getStdntId() <= 0) {
            throw new IllegalArgumentException("Missing or invalid student id");
        }
        return studentDAO.updateStudent(s);
    }
}

