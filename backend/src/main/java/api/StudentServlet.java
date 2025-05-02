// src/main/java/api/StudentServlet.java
package api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Student;
import service.StudentService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/students/*")
public class StudentServlet extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final Gson gson = new Gson();

    /**
     * Create a new student.
     * Expects JSON:
     *   { "fname":"…", "email":"…", "hashedPass":"…",
     *     "schoolName":"…", "majorName":"…" }
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        // Require JSON
        if (req.getContentType() == null ||
                !req.getContentType().startsWith("application/json")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Content-Type must be application/json\"}");
            return;
        }

        try (PrintWriter out = resp.getWriter()) {
            // Parse into a Map to pull only the fields we need
            Type mapType = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> data = gson.fromJson(req.getReader(), mapType);

            String fname       = data.get("fname");
            String email       = data.get("email");
            String hashedPass  = data.get("hashedPass");
            String schoolName  = data.get("schoolName");
            String majorName   = data.get("majorName");

            boolean created = studentService.addStudent(
                    fname, email, hashedPass, schoolName, majorName
            );

            if (created) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                out.write("{\"status\":\"created\"}");
            } else {
                // service returned false (should be rare)
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"error\":\"Failed to add student\"}");
            }
        }
        catch (IllegalArgumentException iae) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter()
                    .write("{\"error\":\"" + iae.getMessage() + "\"}");
        }
        catch (SQLException sqle) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter()
                    .write("{\"error\":\"Database error: " + sqle.getMessage() + "\"}");
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter()
                    .write("{\"error\":\"Unexpected error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        try (PrintWriter out = resp.getWriter()) {
            if (pathInfo != null && pathInfo.length() > 1) {
                // GET /api/students/{id}
                int id = Integer.parseInt(pathInfo.substring(1));
                Student student = studentService.getStudentById(id);
                out.print(gson.toJson(student));
            } else {
                // GET /api/students
                List<Student> all = studentService.getAllStudents();
                out.print(gson.toJson(all));
            }
        }
        catch (NumberFormatException nfe) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid student id\"}");
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Failed to load student(s)\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        if (req.getContentType() == null ||
                !req.getContentType().startsWith("application/json")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter()
                    .write("{\"error\":\"Content-Type must be application/json\"}");
            return;
        }

        try {
            Student student = gson.fromJson(req.getReader(), Student.class);

            if (student == null || student.getStdntId() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter()
                        .write("{\"error\":\"Missing or invalid student id\"}");
                return;
            }

            boolean updated = studentService.updateStudent(student);
            if (updated) {
                resp.getWriter().write("{\"status\":\"updated\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"Could not update student\"}");
            }
        }
        catch (IllegalArgumentException iae) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter()
                    .write("{\"error\":\"" + iae.getMessage() + "\"}");
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Update failed\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter()
                    .write("{\"error\":\"Missing student id\"}");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            boolean deleted = studentService.deleteStudent(id);
            if (deleted) {
                resp.getWriter().write("{\"status\":\"deleted\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter()
                        .write("{\"error\":\"Could not delete student\"}");
            }
        }
        catch (NumberFormatException nfe) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter()
                    .write("{\"error\":\"Invalid student id\"}");
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter()
                    .write("{\"error\":\"Delete failed\"}");
        }
    }
}
