package controller;

import java.io.IOException;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        UserDAO dao = new UserDAO();

        // Duplicate email check
        if (dao.isEmailExist(email)) {
            resp.sendRedirect("user/register.jsp?msg=Email Already Exists!");
            return;
        }

        boolean ok = dao.registerUser(name, email, password, phone, address);

        if (ok) {
            resp.sendRedirect("user/login.jsp?msg=Registration Successful! Please Login.");
        } else {
            resp.sendRedirect("user/register.jsp?msg=Registration Failed! Try Again.");
        }
    }
}
