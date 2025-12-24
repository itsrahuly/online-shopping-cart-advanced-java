package controller;

import java.io.IOException;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("user/login.jsp?msg=Login Required");
            return;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String payment = req.getParameter("payment");
        String totalStr = req.getParameter("total");

        double total = Double.parseDouble(totalStr);

        String upiId = req.getParameter("upiId");
        String cardNumber = req.getParameter("cardNumber");

        OrderDAO dao = new OrderDAO();

        boolean ok = dao.placeOrder(
                userId, fullname, phone, address,
                payment, total, upiId, cardNumber
        );

        if (ok) {
            req.setAttribute("name", fullname);
            req.setAttribute("payment", payment);
            req.setAttribute("total", totalStr);

            req.getRequestDispatcher("user/order-success.jsp")
               .forward(req, resp);

        } else {
            resp.sendRedirect("user/payment.jsp?msg=Order Failed");
        }
    

    }
}
