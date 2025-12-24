package controller;

import java.io.File;
import java.io.IOException;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Product;

@WebServlet("/AddProductServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,   // 2MB
        maxFileSize = 1024 * 1024 * 10,        // 10MB
        maxRequestSize = 1024 * 1024 * 50      // 50MB
)
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ---------- FORM DATA ----------
        String name = req.getParameter("name");
        String brand = req.getParameter("brand");
        String category = req.getParameter("category");
        String subcategory = req.getParameter("subcategory");
        double price = Double.parseDouble(req.getParameter("price"));
        double discountPrice = Double.parseDouble(req.getParameter("discount_price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String description = req.getParameter("description");
        String specifications = req.getParameter("specifications");

        // ---------- SKU ----------
        String sku = brand.substring(0, 2).toUpperCase() + "-"
                   + category.substring(0, 3).toUpperCase() + "-"
                   + (System.currentTimeMillis() % 10000);

        // ---------- IMAGE UPLOAD PATH (FIXED) ----------
        String uploadPath = req.getServletContext()
                .getRealPath("/product-images");

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // ---------- SAVE IMAGES ----------
        String image1 = saveImage(req.getPart("image1"), uploadPath);
        String image2 = saveImage(req.getPart("image2"), uploadPath);
        String image3 = saveImage(req.getPart("image3"), uploadPath);

        // ---------- PRODUCT OBJECT ----------
        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setCategory(category);
        product.setSubcategory(subcategory);
        product.setPrice(price);
        product.setDiscountPrice(discountPrice);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setSpecifications(specifications);
        product.setSku(sku);
        product.setImage1(image1);
        product.setImage2(image2);
        product.setImage3(image3);
        product.setStatus("Active");

        // ---------- SAVE TO DB ----------
        ProductDAO dao = new ProductDAO();
        boolean success = dao.addProduct(product);

        if (success) {
            resp.sendRedirect("admin/manage-products.jsp?msg=success");
        } else {
            resp.sendRedirect("admin/add-product.jsp?msg=error");
        }
    }

    // ---------- IMAGE SAVE HELPER ----------
    private String saveImage(Part filePart, String uploadPath)
            throws IOException {

        if (filePart != null && filePart.getSize() > 0) {
            String fileName =
                    System.currentTimeMillis() + "_"
                  + filePart.getSubmittedFileName();

            filePart.write(uploadPath + File.separator + fileName);
            return fileName;
        }
        return null;
    }
}
