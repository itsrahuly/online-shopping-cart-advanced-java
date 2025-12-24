<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="include/header.jsp" %>
<%@ page import="java.util.*, model.Order" %>

<div class="container">
<button class="btn btn-outline-secondary mb-3"
        onclick="window.history.back();">
    ← Back
</button>
    <h3 class="fw-bold mb-4">My Orders</h3>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    if (orders == null || orders.isEmpty()) {
%>
    <div class="alert alert-info">
        You have not placed any orders yet.
    </div>
<%
    } else {
%>
<div class="table-responsive">
<table class="table table-bordered align-middle text-nowrap">
    <thead class="table-dark text-center">
        <tr>
            <th>Order ID</th>
            <th>Total</th>
            <th>Status</th>
            <th>Payment</th>
            <th>Address</th>
            <th>Date</th>
        </tr>
    </thead>
    <tbody class="text-center">
    <% for (Order o : orders) { %>
        <tr>
            <td>#<%= o.getId() %></td>
            <td>₹ <%= o.getTotalAmount() %></td>
            <td>
                <span class="badge rounded-pill
                <%= o.getStatus().equalsIgnoreCase("Cancelled") ? "bg-danger" :
                    o.getStatus().equalsIgnoreCase("Delivered") ? "bg-success" :
                    o.getStatus().equalsIgnoreCase("Pending") ? "bg-warning" :
                    "bg-primary" %>">
                    <%= o.getStatus() %>
                </span>
            </td>
            <td><%= o.getPaymentMethod() %></td>
            <td><%= o.getAddress() %></td>
            <td>
                <%= new java.text.SimpleDateFormat("dd MMM yyyy")
                    .format(o.getOrderDate()) %>
            </td>
        </tr>
    <% } %>
    </tbody>
</table>
</div>


<% } %>
</div>

<%@ include file="include/footer.jsp" %>
