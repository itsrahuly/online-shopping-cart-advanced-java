package controller;

import java.io.IOException;
import java.util.*;
import com.google.gson.Gson;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DashboardData")
public class DashboardDataServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        OrderDAO orderDao = new OrderDAO();
        ProductDAO productDao = new ProductDAO();

        Map<String, Object> data = new HashMap<>();
        data.put("ordersByMonth", orderDao.getOrdersByMonth());
        data.put("categoryData", productDao.getCategoryWiseCount());

        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(data));
        
        UserDAO userDao = new UserDAO();

        req.setAttribute("productCount", productDao.getProductCount());
        req.setAttribute("userCount", userDao.getUserCount());
        req.setAttribute("orderCount", orderDao.getOrderCount());

        // NEW Stats
        req.setAttribute("pendingCount", orderDao.getPendingOrders());
        req.setAttribute("deliveredCount", orderDao.getDeliveredOrders());
        req.setAttribute("cancelCount", orderDao.getCancelledOrders());

        req.getRequestDispatcher("admin/dashboard.jsp").forward(req, resp);

    }
}
