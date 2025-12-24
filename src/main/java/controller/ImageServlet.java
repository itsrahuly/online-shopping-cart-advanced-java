package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.Files;

@WebServlet("/product-images/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String imageName = req.getPathInfo().substring(1);

        String imagePath = getServletContext()
                .getRealPath("/product-images/" + imageName);

        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType(getServletContext()
                .getMimeType(imageFile.getName()));

        Files.copy(imageFile.toPath(), resp.getOutputStream());
    }
}
