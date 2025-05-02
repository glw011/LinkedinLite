
package api;

import com.google.gson.Gson;
import model.School;
import service.SchoolService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/schools/*")
public class SchoolServlet extends HttpServlet {
    private final SchoolService schoolService = new SchoolService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String path = req.getPathInfo();
        try (PrintWriter out = resp.getWriter()) {
            if (path == null || path.equals("/")) {
                List<School> all = schoolService.getAllSchools();
                out.print(gson.toJson(all));
            } else {
                int id = Integer.parseInt(path.substring(1));
                School s = schoolService.getSchoolById(id);
                out.print(gson.toJson(s));
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid school ID\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        if (req.getContentType() == null ||
                !req.getContentType().startsWith("application/json")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"JSON body required\"}");
            return;
        }
        try {
            School s = gson.fromJson(req.getReader(), School.class);
            boolean ok = schoolService.addSchool(s);
            resp.setStatus(ok ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{}");
        } catch (IllegalArgumentException iae) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + iae.getMessage() + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        if (req.getContentType() == null ||
                !req.getContentType().startsWith("application/json")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"JSON body required\"}");
            return;
        }
        try {
            School s = gson.fromJson(req.getReader(), School.class);
            boolean ok = schoolService.updateSchool(s);
            resp.getWriter().write("{\"status\":\"" + (ok ? "updated" : "failed") + "\"}");
        } catch (IllegalArgumentException iae) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + iae.getMessage() + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String path = req.getPathInfo();
        if (path == null || path.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"School ID required\"}");
            return;
        }

        try {
            int id = Integer.parseInt(path.substring(1));
            boolean ok = schoolService.deleteSchool(id);
            resp.getWriter().write("{\"status\":\"" + (ok ? "deleted" : "failed") + "\"}");
        } catch (IllegalArgumentException e) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
