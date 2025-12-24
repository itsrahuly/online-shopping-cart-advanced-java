<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String payment = request.getParameter("payment");
    String fullname = request.getParameter("fullname");
    String phone = request.getParameter("phone");
    String address = request.getParameter("address");
    String total = request.getParameter("total");
    String productId = request.getParameter("productId");
    String qty      = request.getParameter("qty");

%>

<%-- COD Payment (POST required) --%>
<%
    if ("COD".equals(payment)) {
%>
<form id="codForm" action="../place-order" method="post">
    <input type="hidden" name="fullname" value="<%= fullname %>">
    <input type="hidden" name="phone" value="<%= phone %>">
    <input type="hidden" name="address" value="<%= address %>">
    <input type="hidden" name="payment" value="Cash on Delivery">
    <input type="hidden" name="total" value="<%= total %>">
    <input type="hidden" name="productId" value="<%=productId%>">
<input type="hidden" name="qty" value="<%=qty%>">
    
</form>

<script>
    document.getElementById("codForm").submit();
</script>

<%
    return;
}
%>

<%-- CARD Payment --%>
<%
    if ("CARD".equals(payment)) {
        response.sendRedirect("card-payment.jsp?fullname=" + fullname +
                "&phone=" + phone +
                "&address=" + address +
                "&total=" + total);
        return;
    }
%>

<%-- UPI Payment --%>
<%
    if ("UPI".equals(payment)) {
        response.sendRedirect("upi-payment.jsp?fullname=" + fullname +
                "&phone=" + phone +
                "&address=" + address +
                "&total=" + total);
        return;
    }
%>
