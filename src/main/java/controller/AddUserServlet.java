package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.User;
import dao.UserDAO;

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(password);
        u.setPhone(phone);
        u.setRole(role);

        boolean saved = new UserDAO().addUser(u);

        if(saved){
            resp.sendRedirect("admin/manage-users.jsp?msg=success");
        } else {
            resp.sendRedirect("admin/add-user.jsp?msg=error");
        }
    }
}
