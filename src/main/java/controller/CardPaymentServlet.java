package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.OrderDAO;
import util.DBConnection;

@WebServlet("/CardPaymentServlet")
public class CardPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login.jsp?msg=Login Required");
            return;
        }

        // User Details
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String totalStr = req.getParameter("total");

        if (totalStr == null) totalStr = "0";
        double total = Double.parseDouble(totalStr);

        // Card Details
        String card = req.getParameter("cardNumber");
        String holder = req.getParameter("holderName");
        String month = req.getParameter("expMonth");
        String year = req.getParameter("expYear");
        String cvv = req.getParameter("cvv");

        DBConnection db = new DBConnection();

        // 1️⃣ Validate Card
        if (!db.validateCard(card, holder, month, year, cvv)) {

            resp.sendRedirect(req.getContextPath() 
                + "/user/card-payment.jsp?msg=Invalid+Card+Details"
                + "&fullname=" + fullname
                + "&phone=" + phone
                + "&address=" + address
                + "&total=" + totalStr);

            return;
        }

        // 2️⃣ Check Balance
        int balance = db.getCardBalance(card);

        if (balance < total) {

            resp.sendRedirect(req.getContextPath() 
                + "/user/card-payment.jsp?msg=Insufficient+Balance"
                + "&fullname=" + fullname
                + "&phone=" + phone
                + "&address=" + address
                + "&total=" + totalStr);

            return;
        }

        // 3️⃣ Deduct Balance
        db.updateCardBalance(card, balance - (int) total);

        // 4️⃣ Save Order
        OrderDAO orderDao = new OrderDAO();
        boolean ok = orderDao.placeOrder(
                userId, fullname, phone, address,
                "CARD", total, null, card
        );

        if (!ok) {
            resp.sendRedirect(req.getContextPath() + "/user/payment.jsp?msg=Order Failed");
            return;
        }

        // 5️⃣ Order Success Page
        req.setAttribute("name", fullname);
        req.setAttribute("payment", "CARD");
        req.setAttribute("total", totalStr);

        req.getRequestDispatcher("/user/order-success.jsp").forward(req, resp);
    }
}
