package service;

import dao.StudentDAO;
import model.Student;
import java.util.List;
//import dao.MajorDAO;
import model.School;
import model.Major;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();

    /**
     * Retrieves all students, enriches each student by computing a display name,
     * and sorts them alphabetically by last name.
     *
     * @return a sorted list of enriched Student objects.
     * @throws Exception if no data is available or a database error occurs.
     */

    public List<Student> getAllStudents() throws Exception {
        List<Student> students = studentDAO.getAllStudents();
        if (students == null) {
            throw new Exception("No student data available.");
        }

    // DO STUFF...
    // LIKE CREATE A DISPLAY NAME OR WHATEVER
//        for (Student s : students) {
//            String displayName = s.getFname() + " " + s.getLname();
//            s.setDisplayName(displayName);
//        }
//     OR...
        // sort students by last name (case-insensitive).
//        students.sort((s1, s2) -> s1.getLname().compareToIgnoreCase(s2.getLname()));
        return students;
    }


//    public Student getStudentProfile(int studentId) throws Exception {
//        Student student = studentDAO.getStudentById(studentId);
//        if (student == null) {
//            throw new Exception("Student not found");
//        }
//
//        Major major = majorDAO.getMajorById(student.getMajorId());
//        if (major != null) {
//            student.setMajor(major);
//            student.setSchool(major.getSchool()); // Retrieve the school associated with this major.
//        }
//        return student;
//    }


    /**
     * Retrieves a student by ID after validating the input and enriching the data.
     *
     * @param id the student's ID
     * @return the corresponding enriched Student object
     * @throws Exception if the ID is invalid or the student is not found.
     */
    public Student getStudentById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Student id must be positive");
        }
        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            throw new Exception("Student with id " + id + " not found.");
        }

        return student;
    }

    /**
     * Adds a new student to the database.
     */
    public boolean addStdnt(int stdntId, String fname, String lname, String email, String hashedPass, String schoolName, int majorId) throws Exception {
        return studentDAO.addStdnt(stdntId, fname, lname, email, hashedPass, schoolName, majorId);
    }

    /**
     * Updates an existing student's record.
     */
    public boolean updateStudent(Student s) throws Exception {
        return studentDAO.updateStudent(s);
    }

    /**
     * Deletes a student record.
     */
    public boolean deleteStudent(int id) throws Exception {
        return studentDAO.deleteStudent(id);
    }
}
