// src/main/java/api/PostServlet.java
package api;

import com.google.gson.Gson;
import model.Post;
import service.PostService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/api/posts/*")
public class PostServlet extends HttpServlet {
    private final PostService postService = new PostService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            List<Post> posts = postService.getAllPosts();
            resp.getWriter().print(gson.toJson(posts));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Failed to fetch posts\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            AddPostRequest r = gson.fromJson(req.getReader(), AddPostRequest.class);
            boolean ok = postService.addPost(r.userId, r.content, new LinkedList<>(r.tags));
            resp.setStatus(ok ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String path = req.getPathInfo();
        if (path == null || path.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Post ID required\"}");
            return;
        }
        int postId = Integer.parseInt(path.substring(1));
        try {
            UpdatePostRequest r = gson.fromJson(req.getReader(), UpdatePostRequest.class);
            boolean ok = postService.updatePost(postId, r.content);
            resp.getWriter().write("{\"status\":\"" + (ok ? "updated" : "failed") + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String path = req.getPathInfo();
        if (path == null || path.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Post ID required\"}");
            return;
        }
        int postId = Integer.parseInt(path.substring(1));
        try {
            boolean ok = postService.deletePost(postId);
            resp.getWriter().write("{\"status\":\"" + (ok ? "deleted" : "failed") + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Delete failed\"}");
        }
    }

    private static class AddPostRequest {
        int userId;
        String content;
        java.util.List<Integer> tags = new java.util.ArrayList<>();
    }
    private static class UpdatePostRequest {
        String content;
    }
}
