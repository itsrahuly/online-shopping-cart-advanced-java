<%@ page import="dao.ProductDAO, dao.UserDAO, dao.OrderDAO" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%  //session checking
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session.getAttribute("adminObj") == null) {
        response.sendRedirect("login.jsp?msg=Please login first!");
        return;
    }
%>
<%@ include file="include/header.jsp" %>
<%@ include file="include/sidebar.jsp" %>

<%
    ProductDAO productDao = new ProductDAO();
    UserDAO userDao = new UserDAO();
    OrderDAO orderDao = new OrderDAO();

    int productCount = productDao.getProductCount();
    int userCount = userDao.getUserCount();
    int orderCount = orderDao.getOrderCount();
    int pendingCount = orderDao.getPendingOrders();
    int deliveredCount = orderDao.getDeliveredOrders();
    int cancelCount = orderDao.getCancelledOrders();
%>

<h3 class="fw-bold mb-3">Admin Dashboard</h3>


<div class="row g-3">

    <div class="col-lg-3 col-md-6">
        <div class="card shadow text-center p-3 bg-primary text-white">
            <h6>Total Products</h6>
            <h3><%=productCount%></h3>
        </div>
    </div>

    <div class="col-lg-3 col-md-6">
        <div class="card shadow text-center p-3 bg-success text-white">
            <h6>Total Users</h6>
            <h3><%=userCount%></h3>
        </div>
    </div>

    <div class="col-lg-3 col-md-6">
        <div class="card shadow text-center p-3 bg-warning text-dark">
            <h6>Total Orders</h6>
            <h3><%=orderCount%></h3>
        </div>
    </div>

    <div class="col-lg-3 col-md-6">
        <div class="card shadow text-center p-3 bg-info text-dark">
            <h6>Pending Orders</h6>
            <h3><%=pendingCount%></h3>
        </div>
    </div>

    <div class="col-lg-3 col-md-6">
        <div class="card shadow text-center p-3 bg-success text-white">
            <h6>Delivered Orders</h6>
            <h3><%=deliveredCount%></h3>
        </div>
    </div>

    <div class="col-lg-3 col-md-6">
        <div class="card shadow text-center p-3 bg-danger text-white">
            <h6>Cancelled Orders</h6>
            <h3><%=cancelCount%></h3>
        </div>
    </div>

</div>

<%@ include file="include/footer.jsp" %>
