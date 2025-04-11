package api;

import com.google.gson.Gson;
import model.School;
import service.SchoolService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/schools/*")
public class SchoolServlet extends HttpServlet {

    private final SchoolService schoolService = new SchoolService();
    private final Gson gson = new Gson();

    /**
     * Handles GET requests.
     * If an ID is provided in the URL (e.g., /api/schools/123), returns that School.
     * Otherwise, returns all schools.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        try (PrintWriter out = resp.getWriter()) {
            if (pathInfo != null && pathInfo.length() > 1) {
                // get specific school by id from URL /api/schools/{id}
                String idStr = pathInfo.substring(1);
                try {
                    int id = Integer.parseInt(idStr);
                    School school = schoolService.getSchoolById(id);
                    if (school != null) {
                        out.print(gson.toJson(school));
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print("{\"error\":\"School not found.\"}");
                    }
                } catch (NumberFormatException nfe) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Invalid school id.\"}");
                }
            } else {
                // return all schools
                List<School> schools = schoolService.getAllSchools();
                out.print(gson.toJson(schools));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Failed to load school(s).\"}");
        }
    }

    /**
     * Handles POST requests to add a new school.
     * Expects Content-Type "application/json" with a JSON payload representing a School.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        if (req.getContentType() == null || !req.getContentType().startsWith("application/json")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Content-Type must be application/json.\"}");
            return;
        }
        try {
            School school = gson.fromJson(req.getReader(), School.class);
            if (school == null || school.getSchoolName() == null || school.getSchoolName().trim().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"School name is required.\"}");
                return;
            }
            boolean success = schoolService.addSchool(school);
            if (success) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"status\":\"School created.\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"Failed to create school.\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"School creation failed.\"}");
        }
    }

    /**
     * Handles PUT requests to update an existing school.
     * Expects a JSON payload with updated school details.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        if (req.getContentType() == null || !req.getContentType().startsWith("application/json")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Content-Type must be application/json.\"}");
            return;
        }
        try {
            School school = gson.fromJson(req.getReader(), School.class);
            if (school == null || school.getSchoolId() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Valid school ID is required for update.\"}");
                return;
            }
            boolean success = schoolService.updateSchool(school);
            if (success) {
                resp.getWriter().write("{\"status\":\"School updated.\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"Failed to update school.\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"School update failed.\"}");
        }
    }

    /**
     * Handles DELETE requests to remove a school.
     * Expects the school id as part of the URL (e.g., /api/schools/123).
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing school id.\"}");
            return;
        }
        try {
            String idStr = pathInfo.substring(1);
            int id = Integer.parseInt(idStr);
            boolean success = schoolService.deleteSchool(id);
            if (success) {
                resp.getWriter().write("{\"status\":\"School deleted.\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"Failed to delete school.\"}");
            }
        } catch (NumberFormatException nfe) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid school id.\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"School deletion failed.\"}");
        }
    }
}
