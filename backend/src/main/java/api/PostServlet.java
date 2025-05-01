package api;

import com.google.gson.Gson;
import model.Post;
import service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;

@WebServlet("/api/posts")
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 2,  // 2 MB threshold before writing to disk
        maxFileSize         = 1024 * 1024 * 10, // max size 10 MB
        maxRequestSize      = 1024 * 1024 * 50  // max requestr 50 MB
)
public class PostServlet extends HttpServlet {

    // Service layer for posts
    private final PostService postService = new PostService();
    private final Gson gson = new Gson();

    /**
     * Handles HTTP POST requests to create a new post.
     * Expected inputs:
     *  - A form field "postText" (the text for the post)
     *  - A form field "userKey" representing an authentication token or identifier.
     *  - Optionally, a file upload field named "imageFile" for an image.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");

        // standard form fields
        String postText = req.getParameter("postText");
        String userKey = req.getParameter("userKey");


        if (postText == null || postText.trim().isEmpty() ||
                userKey == null || userKey.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing required fields: postText and userKey\"}");
            return;
        }

        // if image upload exists, process that thang
        String imageFileName = null;
        try {
            Part imagePart = req.getPart("imageFile");
            if (imagePart != null && imagePart.getSize() > 0) {
                // file name from the header.
                imageFileName = extractFileName(imagePart);
                // where save
                String uploadDir = req.getServletContext().getRealPath("/") + "uploads";
                File uploadDirectory = new File(uploadDir);
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }
                // save to disk
                File file = new File(uploadDirectory, imageFileName);
                try (InputStream fileContent = imagePart.getInputStream()) {
                    Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IllegalStateException ise) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Uploaded file exceeds size limits\"}");
            return;
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Failed to process uploaded file\"}");
            return;
        }

        // new Post object and populate it.
        Post post = new Post();
        post.setPostText(postText);
        post.setUserKey(userKey); //  post owner.
        post.setImageFileName(imageFileName);
        // TODO: Set additional fields like timestamp automatically in the service or via the database
        //      ** DB will set the timestamp automatically when new post is inserted if that matters for your usecase - garrett

        // delegate creation of post to service layer
        boolean success = false;
        try {
            success = postService.addPost(post);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try (PrintWriter out = resp.getWriter()) {
            if (success) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                out.write("{\"status\":\"post created\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"error\":\"Failed to create post\"}");
            }
        }
    }

    /**
     * meth to extract the file name from the content-disposition header.
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf('=') + 2, s.length() - 1);
            }
        }
        return null;
    }
}
